package com.example.webapplication.controller;

import com.example.webapplication.exception.WrongAuthorizationException;
import com.example.webapplication.model.dto.UserSignInDto;
import com.example.webapplication.model.dto.UserSignUpDto;
import com.example.webapplication.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AuthorizationController {
    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign_in")
    public String loginPage(Model model) {
        model.addAttribute("userDto", new UserSignInDto());
        return "sign_in";
    }

    @GetMapping("/sign_up")
    public String registrationPage(Model model) {
        model.addAttribute("userDto", new UserSignUpDto());
        return "sign_up";
    }

    @PostMapping("/sign_up")
    public String registration(@ModelAttribute("userDto") @Valid UserSignUpDto userDto, Model model, Errors errors) {
        if(errors.hasErrors()) {
            return "sign_up";
        }
        try {
            if (!userService.saveUser(userDto, errors)) {
                return "sign_up";
            }
        } catch (WrongAuthorizationException wrongAuthorizationException) {
            model.addAttribute(wrongAuthorizationException.getErrorField(), wrongAuthorizationException.getMessage());
        }

        return "redirect:/";
    }
}
