package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
import java.security.Principal;

@Controller
public class MainController {

    private final UserServiceImpl userService;

    @Autowired
    public MainController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String pageForUser (Model model, Principal principal) {
       model.addAttribute("user",userService.findByUsername(principal.getName()));
    return "user";
}
}
