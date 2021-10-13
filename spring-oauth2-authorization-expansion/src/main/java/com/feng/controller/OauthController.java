package com.feng.controller;

import com.feng.config.SecurityProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {

    private final @NonNull SecurityProperties securityProperties;

    @GetMapping("login")
    public String loginView(Model model) {
        model.addAttribute("action", securityProperties.getLoginProcessingUrl());
        return "form-login";
    }

}