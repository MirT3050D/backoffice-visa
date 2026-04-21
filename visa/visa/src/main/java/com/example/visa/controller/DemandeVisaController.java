package com.example.visa.controller;

import com.example.visa.service.DemandeVisaService;
import com.example.visa.service.DemandeVisaService.CreerDemandeVisaForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/demande-visa")
public class DemandeVisaController {
    private final DemandeVisaService demandeVisaService;

    public DemandeVisaController(DemandeVisaService demandeVisaService) {
        this.demandeVisaService = demandeVisaService;
    }

    @GetMapping("/creer")
    public String creerDemandeVisa(Model model) {
        model.addAttribute("form", new CreerDemandeVisaForm());
        return "creer_demande_visa";
    }

    @PostMapping("/creer")
    public String enregistrerDemandeVisa(
            @Valid @ModelAttribute("form") CreerDemandeVisaForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "creer_demande_visa";
        }

        try {
            demandeVisaService.creerDemandeVisa(form);
            redirectAttributes.addFlashAttribute("successMessage", "Demande de visa creee avec succes");
            return "redirect:/demande-visa/creer";
        } catch (IllegalArgumentException e) {
            bindingResult.reject("creation.error", e.getMessage());
            return "creer_demande_visa";
        }
    }
    
}
