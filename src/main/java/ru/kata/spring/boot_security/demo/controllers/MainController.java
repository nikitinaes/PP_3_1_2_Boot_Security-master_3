package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {
    @GetMapping("/hello")
    public String sayHello() {

        return "hello";
    }

    @GetMapping("/user")
    public String showUsers(Principal principal) {
        return "user" + principal.getName();
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
