package com.example.visa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @GetMapping("/front")
    public String front() {
        return "index";
    }

    @GetMapping("/demande-visa")
    public String formdemande() {
        return "demande-visa";
    }
}
