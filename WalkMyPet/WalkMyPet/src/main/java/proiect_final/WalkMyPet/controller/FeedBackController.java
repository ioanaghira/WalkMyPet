package proiect_final.WalkMyPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import proiect_final.WalkMyPet.domain.Feedback;
import proiect_final.WalkMyPet.domain.OrderStatus;
import proiect_final.WalkMyPet.domain.Profile;
import proiect_final.WalkMyPet.domain.WalkingOrder;
import proiect_final.WalkMyPet.repository.FeedBackRepository;
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
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private WalkingOrderCreateService walkingOrderCreateService;
    @Autowired
    private ProfileAService profileAService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private Helper helper;
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/profile/{profileId}/walkingOrder/{orderId}/feedbacks", method = RequestMethod.GET)
    public ModelAndView viewFeedBacks(@PathVariable("profileId") int profileId,
                                      @PathVariable("orderId") int orderId,
                                      Feedback feedback) {
        WalkingOrder walkO = walkingOrderCreateService.findById(orderId).get();
        Profile profile = profileAService.findById(profileId).get();
        List<WalkingOrder> ownerOrders = walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId);
        List<WalkingOrder> providerOrders = walkingOrderCreateService.viewProviderWalkingOrders(profileId);
        if (!walkO.getOrderStatus().equals(OrderStatus.FINISHED) &&
                !walkO.getOrderStatus().equals(OrderStatus.CANCELLED)) {
            ModelAndView modelAndView = new ModelAndView();
            if (!ownerOrders.isEmpty() && profile.getId() == ownerOrders.get(0).getPetOwner().getId()) {
                modelAndView.addObject("successMsg", "You cannot Add Reviews to Order " + walkO.getId() + " at this Status!");
                modelAndView.addObject("walkingOrders",
                        walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId));
                modelAndView.setViewName("walkingOrdersPetOwner");
            } else if (!providerOrders.isEmpty() && profile.getId() == providerOrders.get(0).getProvider().getId()) {
                modelAndView.addObject("walkingOrders",
                        walkingOrderCreateService.viewProviderWalkingOrders(profileId));
                modelAndView.addObject("successMsg", "You cannot View the Reviews on Order" + walkO.getId() + " at this Status!");
                modelAndView.setViewName("walkingOrdersProvider");
            }
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("profile", profile);
            modelAndView.addObject("feedbacks", feedBackService.getAllOrderFeedbacks(orderId));
            modelAndView.addObject("reply", replyService.getAllReplies(feedback.getId()));

            if (profile.getProfileType().toString() == "PROVIDER") {
                modelAndView.setViewName("feedbacksProvider");
            } else {
                modelAndView.setViewName("feedbacksPetOwner");
            }
            return modelAndView;
        }
    }

    @RequestMapping(value = "/profile/{profileId}/walkingOrder/{orderId}/addFeedBack", method = GET)
    public ModelAndView redirectToAddFeedBack(@PathVariable("profileId") int profileId,
                                              @PathVariable("orderId") int orderId, Feedback feedback) {
        WalkingOrder walkO = walkingOrderCreateService.findById(orderId).get();
        Profile profile = profileAService.findById(profileId).get();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("feedback", new Feedback());
        modelAndView.addObject("walkingOrders", walkO);
        modelAndView.addObject("profile", profile);
        modelAndView.setViewName("addFeedBack");
        return modelAndView;
    }

    @RequestMapping(value = "/profile/{profileId}/walkingOrder/{orderId}/saveFeedBack", method = RequestMethod.POST)
    public ModelAndView saveFeedBack(@PathVariable("profileId") int profileId, @PathVariable("orderId") int orderId,
                                     @Valid Feedback feedback, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        Profile profile = profileAService.findById(profileId).get();
        if (result.hasErrors()) {
//            Optional<Profile> profile = profileAService.findById(profileId);
            modelAndView.addObject("profile", profile);
            modelAndView.setViewName("addFeedBack");
        } else {
            WalkingOrder walkingOrder = walkingOrderCreateService.findById(orderId).get();
            feedback.setProfile(profile);
            feedback.setWalkingOrder(walkingOrder);
            feedBackService.saveFeedBack(feedback);
            modelAndView.addObject("profile", profile);
            modelAndView.addObject("feedbacks", feedBackService.getAllOrderFeedbacks(orderId));
            if (profile.getProfileType().toString() == "PROVIDER") {
                modelAndView.setViewName("feedbacksProvider");
            } else {
                modelAndView.setViewName("feedbacksPetOwner");
            }


            Feedback fb = feedBackRepository.findById(feedback.getId()).get();
            String titleName = helper.setFbEmailReceiverName(walkingOrder, fb);
            String fbName = fb.getProfile().getFirstName() + " " + fb.getProfile().getLastName();
            String content = fb.getContent();
            String email = helper.getFbReceiverEmailAddress(walkingOrder, fb);
            String dateTime = fb.getDateTime();

            if (walkingOrder.getProvider() != null) {
                emailService.sendMailOnFeedBack("Feedback Received", email, titleName, fbName, orderId, content, dateTime);
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/feedbacks", method = RequestMethod.GET)
    public ModelAndView viewAllFeedBacks() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("feedbacks", feedBackService.getAllFeedbacks());
        modelAndView.setViewName("viewAllFeedbacks");
        return modelAndView;
    }
}
