package com.stayhome.healthcare.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: Remove after testing
@RestController
public class TestController {

    @GetMapping(path = "/test")
    public String hello() {
        return "Hello";
    }

}
