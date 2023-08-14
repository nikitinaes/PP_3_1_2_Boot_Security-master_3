package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class MainController {
    private UserService userService;
   @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/user")
//    public String showUserInfo(Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//       return "Information about the user: name: " + user.getUsername() +
//               ", email: " + user.getEmail()+".";
//
//    }
  @GetMapping("/user")
  public String pageForUser (Model model, Principal principal) {
       model.addAttribute("user",userService.findByUsername(principal.getName()));
    return "user";
}
}
