package com.cerberus.demotradingweb.controller;

import com.cerberus.demotradingweb.client.UserClient;
import com.cerberus.demotradingweb.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final UserClient userClient;

    @GetMapping("/registration")
    public String createUserPage(Model model){
        model.addAttribute("user", new UserDto());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") UserDto userDto){
        this.userClient.register(userDto);
        return "auth/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
}
