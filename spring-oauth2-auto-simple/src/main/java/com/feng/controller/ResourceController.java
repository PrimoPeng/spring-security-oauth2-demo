package com.feng.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author primo
 * @date 2021/9/27
 */
@RestController
public class ResourceController {

    @GetMapping("/resource")
    public HttpEntity resource(Principal principal) {
        return ResponseEntity.ok(principal);
    }

}
