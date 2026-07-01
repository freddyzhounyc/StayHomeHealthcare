package com.stayhome.healthcare.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: Remove after testing
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping(path = "/test")
    public String hello() {
        return "Hello";
    }

}
