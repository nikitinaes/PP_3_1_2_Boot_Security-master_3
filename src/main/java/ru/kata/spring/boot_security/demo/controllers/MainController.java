package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.services.PersonDetailsService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
import java.security.Principal;

@Controller
public class MainController {

    private final UserServiceImpl userService;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public MainController(UserServiceImpl userService, PersonDetailsService personDetailsService) {
        this.userService = userService;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/user")
    public String pageForUser (Model model, Principal principal) {
       model.addAttribute("user",personDetailsService.findByUsername(principal.getName()));
    return "user";
}
}
