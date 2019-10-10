package com.unmeshc.security.controllers;

import com.unmeshc.security.domain.User;
import com.unmeshc.security.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Created by uc on 10/10/2019
 */
@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", User.builder().build());
        return "registration";
    }

    @PostMapping("/registration")
    public String createNewUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }

        User foundUser = userService.findUserByEmail(user.getEmail());
        if (foundUser == null) {
            result.rejectValue("email", "user.error", "Email is already in used");
        }

        userService.saveUser(user);

        model.addAttribute("successMessage", "User has been registered successfully");
        model.addAttribute("user", User.builder().build());

        return "registration";
    }

    @GetMapping("/admin/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        model.addAttribute("welcomeMessage", "Welcome, " + user.getFirstName() + " " +
                user.getLastName());
        model.addAttribute("adminMessage",
                "Content is only available for users with ADMIN role");

        return "admin/home";
    }
}
