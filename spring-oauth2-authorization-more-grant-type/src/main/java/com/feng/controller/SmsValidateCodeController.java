package com.feng.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SmsValidateCodeController {

    @PostMapping("/sms")
    public HttpEntity<?> sms() {
        return ResponseEntity.ok("ok");
    }
}