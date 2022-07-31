package com.example.webapplication.controller;

import com.example.webapplication.exception.WrongAuthorizationException;
import com.example.webapplication.model.dto.UserSignInDto;
import com.example.webapplication.model.dto.UserSignUpDto;
import com.example.webapplication.model.entity.Bank;
import com.example.webapplication.model.entity.UserRole;
import com.example.webapplication.model.entity.type.UserRoleType;
import com.example.webapplication.model.repository.RoleRepository;
import com.example.webapplication.model.service.CurrencyService;
import com.example.webapplication.model.service.UserService;
import com.example.webapplication.model.service.impl.BankDistributedCacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthorizationController {
    private final UserService userService;
    private final CurrencyService currencyService;
    private KafkaTemplate<String, String> kafkaTemplate;
    private RoleRepository roleRepository;

    @Autowired
    public AuthorizationController(RoleRepository roleRepository, UserService userService, CurrencyService currencyService, KafkaTemplate<String, String> kafkaTemplate) {
        this.userService = userService;
        this.currencyService = currencyService;
        this.kafkaTemplate = kafkaTemplate;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userDto", new UserSignInDto());
        return "login";
    }

    @GetMapping("/sign_up")
    public String registrationPage(Model model) {
        model.addAttribute("userDto", new UserSignUpDto());
        return "sign_up";
    }

    @PostMapping("/sign_up")
    public String registration(@ModelAttribute("userDto") @Valid UserSignUpDto userDto, Errors errors, Model model) {
        try {
            if (!userService.saveUser(userDto, errors)) {
                return "sign_up";
            }

            kafkaTemplate.send("Registration", userDto.getUsername(), "Congratulation!");
        } catch (WrongAuthorizationException wrongAuthorizationException) {
            model.addAttribute(wrongAuthorizationException.getErrorField(), wrongAuthorizationException.getMessage());
        }

        return "redirect:/";
    }

}
