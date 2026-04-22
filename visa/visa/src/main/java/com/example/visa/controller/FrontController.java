package com.example.visa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.visa.repository.TypeDemandeVisaRepository;
import com.example.visa.model.TypeDemandeVisa;
import java.util.List;

@Controller
public class FrontController {

    @Autowired
    private TypeDemandeVisaRepository typeDemandeVisaRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<TypeDemandeVisa> typeDemandes = typeDemandeVisaRepository.findAll();
        model.addAttribute("typeDemandes", typeDemandes);
        return "index";
    }

    @GetMapping("/visa-demande")
    public String formdemande() {
        return "visa-demande";
    }

}
