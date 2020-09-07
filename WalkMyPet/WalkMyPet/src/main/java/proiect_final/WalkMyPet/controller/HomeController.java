package proiect_final.WalkMyPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiect_final.WalkMyPet.domain.Profile;
import proiect_final.WalkMyPet.repository.UserRepository;
import proiect_final.WalkMyPet.service.impl.UserService;
import proiect_final.WalkMyPet.service.impl.UserServiceImpl;

import javax.validation.Valid;


@Controller
public class HomeController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/")
    public String welcome(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/pet_owner")
    public String petOwner() {
        return "pet_owner";
    }


    @GetMapping("/provider")
    public String provider() {
        return "provider";
    }


    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }



}


