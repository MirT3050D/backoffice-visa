package com.example.visa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/visa-demande")
    public String formdemande() {
        return "visa-demande";
    }
    
    @GetMapping("/visa-type")
    public String typeVisa() {
        return "visa-type";
    }

    @GetMapping("/visa-form")
    public String visaForm() {
        return "visa-form";
    }

    @GetMapping("/visa-recap")
    public String confirmationVisa() {
        return "visa-recap";
    }

}
