package proiect_final.WalkMyPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import proiect_final.WalkMyPet.domain.Profile;
import proiect_final.WalkMyPet.service.helper.Helper;
import proiect_final.WalkMyPet.service.impl.ProfileAService;
import proiect_final.WalkMyPet.service.mail.EmailService;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
public class ProfileAController {

    @Autowired
    private ProfileAService profileAService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Helper helper;

    @RequestMapping(value = "/homepage", method = GET)
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("homepage");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = GET)
    public ModelAndView redirectToAddProfile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profile", new Profile());
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView addProfile(@Valid @ModelAttribute(value = "profile") Profile profile,
                                   BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if(result.hasErrors()){
            modelAndView.setViewName("register");
        } else {
            profileAService.addProfile(profile);
            modelAndView.addObject("profile", profileAService.findById(profile.getId()).get());
            modelAndView.setViewName("registered");

            String name = profile.getFirstName();
            String email = profile.getEmail();

            emailService.sendMailOnRegister("Registration Successful",email,name);
        }
        return modelAndView;

    }


    @RequestMapping(value = "/profile/{id}", method = GET)
    public ModelAndView redirectToProfilePage(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Profile> profile = profileAService.findById(id);
        modelAndView.addObject("profile", profile);
        modelAndView.setViewName("profile");
        return modelAndView;
    }

//    @RequestMapping(value = "/profile/login", method = GET)
//    public ModelAndView login(@RequestParam int id) {
//        ModelAndView modelAndView = new ModelAndView();
//        Optional<Profile> profiles = testProfileService.findById(id);
//        modelAndView.addObject("profiles", profiles);
//        modelAndView.setViewName("profile");
//        return modelAndView;
//    }

//    @RequestMapping(value = "/profile/edit/{id}", method = GET)
//    public ModelAndView redirectToEditProfile(@PathVariable("id") int id) {
//        ModelAndView modelAndView = new ModelAndView();
//        Profile profile = testProfileService.findById(id).get();
//        modelAndView.addObject("profile", profile);
//        modelAndView.setViewName("editProfile");
//        return modelAndView;
//    }

//    @RequestMapping(value = "/profile/editProfile/{id}", method = RequestMethod.POST)
//    public ModelAndView editProfile(Profile profile,
//                                    @RequestParam("address.streetNumber") String streetNumber,
//                                    @RequestParam("address.street") String street,
//                                    @RequestParam("address.zone") String zone,
//                                    @RequestParam("address.city") String city,
//                                    @RequestParam("address.postcode") String postcode,
//                                    @RequestParam("email") String email,
//                                    @RequestParam("password") String password,
//                                    @RequestParam("id") @PathVariable("id") int id) {
//        ModelAndView modelAndView = new ModelAndView();
//        profile.setEmail(email);
//        profile.setPassword(password);
//        profile.setAddress(new Address(streetNumber, street, zone, city, postcode));
//        profile.setId(id);
//        List<Profile> profiles = testProfileService.getAllProfiles();
//        modelAndView.addObject("profiles", profiles);
//        testProfileService.editProfile(profile, id);
//        modelAndView.setViewName("editConfirmed");
//        return modelAndView;
//    }

//    @RequestMapping(value = "/profile/delete/{id}", method = RequestMethod.POST)
//    public ModelAndView deleteProfile(@PathVariable("id") int id, Profile profile) {
//        ModelAndView modelAndView = new ModelAndView();
//        testProfileService.deleteProfile(profile);
//        modelAndView.setViewName("deleted");
//        return modelAndView;
//    }

//    @RequestMapping(value = "/profile", method = RequestMethod.GET)
//    public ModelAndView viewProfiles(Profile profile) {
//        ModelAndView modelAndView = new ModelAndView();
//        testProfileService.getAllProfiles();
//        modelAndView.addObject("profiles", testProfileService.getAllProfiles());
//        modelAndView.setViewName("profiles");
//        return modelAndView;
//    }

//    @RequestMapping(value = "/profile/manage", method = GET)
//    public ModelAndView manageProfiles() {
//        ModelAndView modelAndView = new ModelAndView();
//        List<Profile> profiles = new ArrayList<>();
//        profiles = testProfileService.getAllProfiles();
//        modelAndView.addObject("profiles", profiles);
//        modelAndView.setViewName("profileList");
//        return modelAndView;
//    }
}
