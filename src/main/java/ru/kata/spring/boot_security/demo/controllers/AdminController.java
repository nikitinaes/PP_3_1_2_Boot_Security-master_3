package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.PersonDetailsService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;

    private final PersonDetailsService personDetailsService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService, PasswordEncoder passwordEncoder,
                           PersonDetailsService personDetailsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/admin")
    public String showAllUsers (Principal principal, Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("user", personDetailsService.findByUsername(principal.getName()));
        model.addAttribute("roles", roleService.findByIdRoles());
        return "users";
    }

@GetMapping("/add")
public String pageCreateUser (Model model, Principal principal) {
    model.addAttribute("user", personDetailsService.findByUsername(principal.getName()));
    model.addAttribute("listRoles", roleService.findByIdRoles());
    return "create";
}

    @PostMapping("/new")
    public String pageCreate(@ModelAttribute("user")
                             @Valid User user, BindingResult bindingResult,
                             @RequestParam("listRoles") List<Integer> roleIds) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        if (personDetailsService.findByUsername(user.getUsername()) != null) {
            bindingResult.addError(new FieldError("username", "username",
                    String.format("User with name \"%s\" already exists!", user.getUsername())));
            return "create";
        }
        Set<Role> roles = new HashSet<>(roleService.getRolesById(roleIds));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String pageDelete(@PathVariable("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/update")
    public String pageEditUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user",userService.getUserById(id));
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "users";
    }

    @PutMapping("/{id}/update")
    public String pageEdit(@Valid User user, BindingResult bindingResult,
                           @RequestParam("listRoles") List<Integer>roleIds) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        Set<Role> roles = new HashSet<>(roleService.getRolesById(roleIds));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}

