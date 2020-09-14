package proiect_final.WalkMyPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import proiect_final.WalkMyPet.domain.Feedback;
import proiect_final.WalkMyPet.domain.Profile;
import proiect_final.WalkMyPet.domain.Reply;
import proiect_final.WalkMyPet.domain.WalkingOrder;
import proiect_final.WalkMyPet.repository.ReplyRepository;
import proiect_final.WalkMyPet.service.helper.Helper;
import proiect_final.WalkMyPet.service.impl.FeedBackService;
import proiect_final.WalkMyPet.service.impl.ProfileAService;
import proiect_final.WalkMyPet.service.impl.ReplyService;
import proiect_final.WalkMyPet.service.impl.WalkingOrderCreateService;
import proiect_final.WalkMyPet.service.mail.EmailService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private WalkingOrderCreateService walkingOrderCreateService;
    @Autowired
    private ProfileAService profileAService;
    @Autowired
    private Helper helper;
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/profile/{profileId}/walkingOrder/{orderId}/feedBack/{fbId}/addReply", method = GET)
    public ModelAndView redirectToAddReply(@PathVariable("profileId") int profileId,
                                              @PathVariable("orderId") int orderId,
                                              @PathVariable("fbId") int fbId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reply", new Reply());
        Feedback feedback = feedBackService.findById(fbId).get();
        modelAndView.addObject("feedback", feedback);
        Optional<WalkingOrder> walkingOrders = walkingOrderCreateService.findById(orderId);
        modelAndView.addObject("walkingOrders", walkingOrders);
        Profile profile = profileAService.findById(profileId).get();
        modelAndView.addObject("feedbacks", feedBackService.getAllOrderFeedbacks(orderId));
        modelAndView.addObject("profile", profile);
        modelAndView.setViewName("addReply");
        return modelAndView;
    }

    @RequestMapping(value = "/profile/{profileId}/walkingOrder/{orderId}/feedBack/{fbId}/reply",
            method = RequestMethod.POST)
    public ModelAndView saveReply(@PathVariable("profileId") int profileId,
                                     @PathVariable("orderId") int orderId,
                                     @PathVariable("fbId") int fbId,
                                     @Valid Reply reply, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            List<Feedback> feedbacks = feedBackService.getAllOrderFeedbacks(orderId);
            modelAndView.addObject("feedbacks", feedbacks);
            Optional<Profile> profile = profileAService.findById(profileId);
            modelAndView.addObject("profile", profile);
            modelAndView.setViewName("addReply");
        } else {
            reply.setProfile(new Profile(profileId));
            reply.setFeedback(new Feedback(fbId));
            replyService.saveReply(reply);

            Optional<Profile> profile = profileAService.findById(profileId);
            if( profile.isPresent() ) {
                modelAndView.addObject("profile", profile.get());
            }

            List<Feedback> feedbacks = feedBackService.getAllOrderFeedbacks(orderId);
            if( !feedbacks.isEmpty()) {
                modelAndView.addObject("feedbacks", feedbacks);
            }
            WalkingOrder walkingOrder = walkingOrderCreateService.findById(orderId).get();
            modelAndView.addObject("replies", replyService.getAllReplies(fbId));
            if(profile.get().getProfileType().toString() == "PROVIDER") {
                modelAndView.setViewName("feedbacksProvider");
            } else {
                modelAndView.setViewName("feedbacksPetOwner");
            }

            Feedback feedback = feedBackService.findById(fbId).get();
            Reply reply1 = replyRepository.findById(reply.getId()).get();
            String titleName = helper.setRpEmailReceiverName(walkingOrder,reply1);
            String fbName = feedback.getProfile().getFirstName() + " " + feedback.getProfile().getLastName();
            String rpName = reply1.getProfile().getFirstName() + " " + reply1.getProfile().getLastName();
            String content = feedback.getContent();
            String rpContent = reply1.getReplyContent();
            String email = helper.getRpReceiverEmailAddress(walkingOrder,reply1);
            String rpDateTime = reply1.getDateTime();
            String dateTime = feedback.getDateTime();

            emailService.sendMailOnReply("Reply Received", email, titleName, fbName, rpName,
                    orderId, content, rpContent, rpDateTime, dateTime);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/feedback/{fbId}/replies",
            method = RequestMethod.GET)
    public ModelAndView viewReplies(@PathVariable("fbId") int fbId){
        ModelAndView modelAndView = new ModelAndView();
        Feedback feedback = feedBackService.findById(fbId).get();
        modelAndView.addObject("feedback", feedback);
        modelAndView.addObject("replies", replyService.getAllReplies(fbId));
        modelAndView.setViewName("feedbacks+replies");
        return modelAndView;
    }
    @RequestMapping(value = "/profile/{profileId}/walkingOrder/{orderId}/feedback/{fbId}/repliesInside",
            method = RequestMethod.GET)
    public ModelAndView viewReplies2(@PathVariable("fbId") int fbId, @PathVariable("profileId") int profileId,
                                     @PathVariable("orderId") int orderId){
        ModelAndView modelAndView = new ModelAndView();
        Profile profile = profileAService.findById(profileId).get();
        WalkingOrder walkingOrder = walkingOrderCreateService.findById(orderId).get();
        Feedback feedback = feedBackService.findById(fbId).get();
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("walkingOrder", walkingOrder);
        modelAndView.addObject("feedback", feedback);
        modelAndView.addObject("replies", replyService.getAllReplies(fbId));
        modelAndView.setViewName("replies");
        return modelAndView;
    }
}