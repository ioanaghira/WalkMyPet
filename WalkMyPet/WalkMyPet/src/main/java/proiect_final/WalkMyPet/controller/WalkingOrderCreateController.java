package proiect_final.WalkMyPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import proiect_final.WalkMyPet.domain.OrderStatus;
import proiect_final.WalkMyPet.domain.Profile;
import proiect_final.WalkMyPet.domain.WalkingOrder;
import proiect_final.WalkMyPet.repository.ProfileARepository;
import proiect_final.WalkMyPet.repository.WalkingOrderCreateRepository;
import proiect_final.WalkMyPet.service.impl.ProfileAService;
import proiect_final.WalkMyPet.service.impl.WalkingOrderCreateService;
import proiect_final.WalkMyPet.service.mail.EmailService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class WalkingOrderCreateController {

    @Autowired
    private WalkingOrderCreateService walkingOrderCreateService;
    @Autowired
    private WalkingOrderCreateRepository walkingOrderCreateRepository;
    @Autowired
    private ProfileAService profileAService;

    @Autowired
    private ProfileARepository profileARepository;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/profile/{profileId}/addWalkingOrder", method = GET)
    public ModelAndView redirectToAddWalkingOrder(@PathVariable("profileId") int profileId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("walkingOrder", new WalkingOrder());
        Optional<Profile> profiles = profileAService.findById(profileId);
        modelAndView.addObject("profiles", profiles);
        modelAndView.setViewName("addWalkingOrder");
        return modelAndView;
    }

    @RequestMapping(value = "/profile/{profileId}/addWalkingOrder", method = RequestMethod.POST)
    public ModelAndView addWalkingOrder(@PathVariable("profileId") int profileId,
                                        @Valid @ModelAttribute("walkingOrder") WalkingOrder walkingOrder,
                                        BindingResult result) {

        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            Optional<Profile> profiles = profileAService.findById(profileId);
            modelAndView.addObject("profiles", profiles);
            modelAndView.setViewName("addWalkingOrder");
        } else {
            Profile profile = new Profile(profileId);
            walkingOrder.setPetOwner(profile);
            walkingOrderCreateService.addWalkingOrder(walkingOrder);

            Optional<Profile> profiles = profileAService.findById(profileId);
            modelAndView.addObject("profiles", profiles);
            modelAndView.setViewName("walkingOrderOk");

            Optional<Profile> profile1 = profileARepository.findById(profileId);
            String email = profile1.get().getEmail();
            String name = profile1.get().getFirstName();
            WalkingOrder wo = walkingOrderCreateRepository.findById(walkingOrder.getId()).get();
            int id = walkingOrder.getId();
            String date = wo.getDate().toString();
            String start = wo.getStartTime().toString();
            String end = wo.getEndTime().toString();
            String status = wo.getOrderStatus().toString();
            double cost = wo.getPayment().getPaymentAmount();

            emailService.sendMailCreateOrder("Order Created", email, name,
                    id, date, start, end, status, cost);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/profile/{profileId}/yourWalkingOrders", method = RequestMethod.GET)
    public ModelAndView viewYourWalkingOrders(@PathVariable("profileId") int profileId) {
        ModelAndView modelAndView = new ModelAndView();
        Profile profile = new Profile(profileId);
        List<WalkingOrder> ownerOrders = walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId);
        List<WalkingOrder> providerOrders = walkingOrderCreateService.viewProviderWalkingOrders(profileId);

        if (!ownerOrders.isEmpty() && profile.getId() == ownerOrders.get(0).getPetOwner().getId()) {
            modelAndView.addObject("walkingOrders",
                    walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId));
            modelAndView.setViewName("walkingOrdersPetOwner");
        } else if (!providerOrders.isEmpty() && profile.getId() == providerOrders.get(0).getProvider().getId()) {
            modelAndView.addObject("walkingOrders",
                    walkingOrderCreateService.viewProviderWalkingOrders(profileId));
            modelAndView.setViewName("walkingOrdersProvider");
        } else {
            Profile profile1 = profileAService.findById(profileId).get();
            if(profile1.getProfileType().toString() == "PROVIDER"){
                modelAndView.addObject("walkingOrders",
                        providerOrders);
                modelAndView.setViewName("walkingOrdersProvider");
            } else {
                modelAndView.addObject("walkingOrders",
                        ownerOrders);
                modelAndView.setViewName("walkingOrdersPetOwner");
            }
        }
        return modelAndView;
    }


    @RequestMapping(value = "/profile/{profileId}/editWalkingOrder/{id}", method = GET)
    public ModelAndView redirectToEditWalkingOrder(@PathVariable("profileId") int profileId, @PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<WalkingOrder> walkingOrders = walkingOrderCreateService.findById(id);
        Optional<Profile> profile = profileAService.findById(profileId);
        modelAndView.addObject("walkingOrders", walkingOrders);
        modelAndView.addObject("profile", profile);
        modelAndView.setViewName("editWalkingOrder");
        return modelAndView;
    }

    @RequestMapping(value = "/profile/{profileId}/saveWalkingOrder/{id}", method = RequestMethod.POST)
    public ModelAndView saveWalkingOrder(WalkingOrder walkingOrder,
                                         @PathVariable("profileId") int profileId,
                                         @RequestParam("id") @PathVariable int id,
                                         @RequestParam("date") String date,
                                         @RequestParam("startTime") String startTime,
                                         @RequestParam("endTime") String endTime,
                                         @RequestParam("observations") String observations) {
        ModelAndView modelAndView = new ModelAndView();
        walkingOrder.setId(id);
        walkingOrder.setObservations(observations);
        walkingOrder.setDate(LocalDate.parse(date));
        walkingOrder.setStartTime(LocalTime.parse(startTime));
        walkingOrder.setEndTime(LocalTime.parse(endTime));
        walkingOrder.setPetOwner(new Profile(profileId));
        List<WalkingOrder> walkingOrders = walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId);
        modelAndView.addObject("walkingOrders", walkingOrders);
        walkingOrderCreateService.saveWalkingOrder(walkingOrder, id);
        modelAndView.setViewName("walkingOrderOk");
        return modelAndView;
    }


    @RequestMapping(value = "/profile/{profileId}/viewOpenOrders", method = RequestMethod.GET)
    public ModelAndView viewOpenWalkingOrders(@PathVariable("profileId") int profileId) {
        ModelAndView modelAndView = new ModelAndView();
        String status = "OPEN";
        modelAndView.addObject("walkingOrders",
                walkingOrderCreateService.viewOpenOrders(OrderStatus.valueOf(status)));
        modelAndView.setViewName("viewOpenOrdersProvider");
        return modelAndView;
    }

    @RequestMapping(value = "/profile/{profileId}/cancelWalkingOrder/{id}", method = RequestMethod.POST)
    public ModelAndView cancelWalkingOrder(WalkingOrder walkingOrder, @PathVariable("profileId") int profileId,
                                           @PathVariable int id) {
        walkingOrderCreateService.cancelWalkingOrder(walkingOrder, OrderStatus.CANCELLED);
        ModelAndView modelAndView = new ModelAndView();
        Profile profile = new Profile(profileId);
        List<WalkingOrder> ownerOrders = walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId);
        List<WalkingOrder> providerOrders = walkingOrderCreateService.viewProviderWalkingOrders(profileId);

        WalkingOrder wo = walkingOrderCreateService.findById(id).get();
        String email1 = wo.getPetOwner().getEmail();
        String email2;
        if (wo.getProvider() != null) {
            email2 = wo.getProvider().getEmail();
        } else {
            email2 = "walkmypet2020@gmail.com";
        }
        emailService.sendMailCancelledOrder("Order Cancelled", email1, id);
        emailService.sendMailCancelledOrder("Order Cancelled", email2, id);

        if (!ownerOrders.isEmpty() && profile.getId() == ownerOrders.get(0).getPetOwner().getId()) {
            modelAndView.addObject("walkingOrders",
                    walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId));
            modelAndView.setViewName("walkingOrdersPetOwner");
        }
        else if (!providerOrders.isEmpty() && profile.getId() == providerOrders.get(0).getProvider().getId()) {
            modelAndView.addObject("walkingOrders",
                    walkingOrderCreateService.viewProviderWalkingOrders(profileId));
            modelAndView.setViewName("walkingOrdersProvider");
        } else {
            modelAndView.setViewName("noWalkingOrder");
        }
        return modelAndView;

    }

    @RequestMapping(value = "/profile/{profileId}/confirmWalkingOrder/{id}", method = RequestMethod.POST)
    public ModelAndView confirmWalkingOrder(WalkingOrder walkingOrder, @PathVariable("profileId") int profileId,
                                            @PathVariable int id){
        Profile profile = new Profile(profileId);
        walkingOrderCreateService.confirmWalkingOrder(walkingOrder, OrderStatus.BOOKED, profile);
        ModelAndView modelAndView = new ModelAndView();
        walkingOrder.setProvider(profile);
        List<WalkingOrder> ownerOrders = walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId);
        List<WalkingOrder> providerOrders = walkingOrderCreateService.viewProviderWalkingOrders(profileId);

        WalkingOrder wo = walkingOrderCreateService.findById(id).get();
        String email1 = wo.getPetOwner().getEmail();
        String email2;
        if (wo.getProvider() != null) {
            email2 = wo.getProvider().getEmail();
        } else {
            email2 = "walkmypet2020@gmail.com";
        }
        emailService.sendMailConfirmOrder("Order Confirmed", email1, id);
        emailService.sendMailConfirmOrder("Order Confirmed", email2, id);


        if (!ownerOrders.isEmpty() && profile.getId() == ownerOrders.get(0).getPetOwner().getId()) {
            modelAndView.addObject("walkingOrders",
                    walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId));
            modelAndView.setViewName("walkingOrdersPetOwner");
        }
        else if (!providerOrders.isEmpty() && profile.getId() == providerOrders.get(0).getProvider().getId()) {
            modelAndView.addObject("walkingOrders",
                    walkingOrderCreateService.viewProviderWalkingOrders(profileId));
            modelAndView.setViewName("walkingOrdersProvider");
        } else {
            modelAndView.setViewName("noWalkingOrder");
        }
        return modelAndView;
    }


    @RequestMapping(value = "/profile/{profileId}/completeWalkingOrder/{id}", method = RequestMethod.POST)
    public ModelAndView completeWalkingOrder(WalkingOrder walkingOrder, @PathVariable("profileId") int profileId,
                                             @PathVariable int id) {
        walkingOrderCreateService.completeWalkingOrder(walkingOrder, OrderStatus.FINISHED);
        ModelAndView modelAndView = new ModelAndView();
        Profile profile = new Profile(profileId);
        walkingOrder.setProvider(profile);
        List<WalkingOrder> ownerOrders = walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId);
        List<WalkingOrder> providerOrders = walkingOrderCreateService.viewProviderWalkingOrders(profileId);

        WalkingOrder wo = walkingOrderCreateService.findById(id).get();
        String email1 = wo.getPetOwner().getEmail();
        String email2;
        if (wo.getProvider() != null) {
            email2 = wo.getProvider().getEmail();
        } else {
            email2 = "walkmypet2020@gmail.com";
        }

        Optional<Profile> profile1 = profileARepository.findById(profileId);
        String email = profile1.get().getEmail();
        String name = profile1.get().getFirstName();
        WalkingOrder wo1 = walkingOrderCreateRepository.findById(walkingOrder.getId()).get();
        int id1 = walkingOrder.getId();
        String date = wo.getDate().toString();
        String start = wo.getStartTime().toString();
        String end = wo.getEndTime().toString();
        String status = wo.getOrderStatus().toString();
        double cost = wo.getPayment().getPaymentAmount();
        emailService.sendMailCompleteOrder("Order Created", email, name,
                id, date, start, end, status, cost);
        emailService.sendMailCompleteOrder("Order Created", email, name,
                id, date, start, end, status, cost);

        if (!ownerOrders.isEmpty() && profile.getId() == ownerOrders.get(0).getPetOwner().getId()) {
            modelAndView.addObject("walkingOrders",
                    walkingOrderCreateService.viewPetOwnerWalkingOrders(profileId));
            modelAndView.setViewName("walkingOrdersPetOwner");
        } else if (!providerOrders.isEmpty() && profile.getId() == providerOrders.get(0).getProvider().getId()) {
            modelAndView.addObject("walkingOrders",
                    walkingOrderCreateService.viewProviderWalkingOrders(profileId));
            modelAndView.setViewName("walkingOrdersProvider");
        } else {
            modelAndView.setViewName("noWalkingOrder");
        }
        return modelAndView;
    }
}
