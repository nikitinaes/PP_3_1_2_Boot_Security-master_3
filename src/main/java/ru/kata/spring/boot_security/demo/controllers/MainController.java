package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@RestController
public class MainController {
    private UserService userService;
   @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String sayHello() {

        return "hello";
    }

    @GetMapping("/user")
    public String showUsers(Principal principal) {
        User user = userService.findByUsername(principal.getName());
       return "user:" + user.getEmail();
    }

    @GetMapping("/admin")
    public String showAdminPage() {
        return "admins only";
    }
    @GetMapping ("/read_profile")
    public String pageForReadingProfile() {
        return "read profile page";
    }
}
