package proiect_final.WalkMyPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import proiect_final.WalkMyPet.domain.Address;
import proiect_final.WalkMyPet.domain.Profile;
import proiect_final.WalkMyPet.service.impl.ProfileAService;
import proiect_final.WalkMyPet.service.impl.UserServiceImpl;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class HomeController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    ProfileAService profileAService;

    @GetMapping("/")
    public String welcome(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping(value = "/pet_owner", method = GET)
    public ModelAndView petOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        ModelAndView modelAndView = new ModelAndView();
        Profile profile = userService.findByEmail(currentPrincipalName);
        modelAndView.addObject("profile", profile);
        modelAndView.setViewName("pet_owner");
        return modelAndView;
    }

    @RequestMapping(value = "/provider", method = GET)
    public ModelAndView provider() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName2 = authentication.getName();
        ModelAndView modelAndView = new ModelAndView();
        Profile profile = userService.findByEmail(currentPrincipalName2);
        modelAndView.addObject("profile", profile);
        modelAndView.setViewName("provider");
        return modelAndView;
    }

    @RequestMapping(value = "/pet_owner/edit/{id}", method = GET)
    public ModelAndView editPetOwner(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Profile profile = userService.findById(id).get();
        modelAndView.addObject("profile", profile);
        modelAndView.setViewName("edit_petOwner");
        return modelAndView;
    }


    @RequestMapping(value = "/pet_owner/edit/{id}", method = POST)
    public ModelAndView editPetOwner(Profile profile, Address address,
                                     @RequestParam("address.streetNumber") String streetNumber,
                                     @RequestParam("address.street") String street,
                                     @RequestParam("address.zone") String zone,
                                     @RequestParam("address.city") String city,
                                     @RequestParam("address.id") int addressId,
                                     @RequestParam("address.postcode") String postcode,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("phoneNumber") String phoneNumber,
                                     @RequestParam("id") @PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        profile.setEmail(email);
        profile.setPassword(password);
        profile.setPhoneNumber(phoneNumber);
        address.setCity(city);
        address.setPostcode(postcode);
        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setZone(zone);
        address.setId(addressId);
        profile.setAddress(address);
        profile.setId(id);

        userService.editProfile(profile);
        modelAndView.setViewName("pet_owner");
        return modelAndView;
    }

    @RequestMapping(value = "/provider/edit/{id}", method = GET)
    public ModelAndView editProvider(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Profile profile = userService.findById(id).get();
        modelAndView.addObject("profile", profile);
        modelAndView.setViewName("edit_provider");
        return modelAndView;
    }


    @RequestMapping(value = "/provider/edit/{id}", method = POST)
    public ModelAndView editProfile(Profile profile, Address address,
                                    @RequestParam("address.streetNumber") String streetNumber,
                                    @RequestParam("address.street") String street,
                                    @RequestParam("address.zone") String zone,
                                    @RequestParam("address.city") String city,
                                    @RequestParam("address.id") int addressId,
                                    @RequestParam("address.postcode") String postcode,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password,
                                    @RequestParam("phoneNumber") String phoneNumber,
                                    @RequestParam("id") @PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        profile.setEmail(email);
        profile.setPassword(password);
        profile.setPhoneNumber(phoneNumber);
        address.setCity(city);
        address.setPostcode(postcode);
        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setZone(zone);
        address.setId(addressId);
        profile.setAddress(address);
        profile.setId(id);

        userService.editProfile(profile);
        modelAndView.setViewName("provider");
        return modelAndView;
    }


    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

}


