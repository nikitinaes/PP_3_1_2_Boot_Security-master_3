package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @GetMapping("admin/new")
    public String pageCreateUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.findByIdRoles());
        return "create";
    }

    @PostMapping("admin/new")
    public String pageCreate(@ModelAttribute("user")
                             @Valid User user, BindingResult bindingResult,
                             @RequestParam("listRoles") List<Integer> roleIds) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        if (userService.findByUsername(user.getUsername()) != null) {
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

    @DeleteMapping("admin/delete/{id}")
    public String pageDelete(@PathVariable("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
    @GetMapping("admin/edit/{id}")
    public String pageEditUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user",userService.getUserById(id));
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "edit";
    }
    @PutMapping("admin/edit")
    public String pageEdit(@Valid User user, BindingResult bindingResult,
                           @RequestParam("listRoles") List<Integer>roleIds) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        Set<Role> roles = new HashSet<>(roleService.getRolesById(roleIds));
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

}

