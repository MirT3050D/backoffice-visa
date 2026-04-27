package com.example.visa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.visa.dto.CreerDemandeVisaForm;
import com.example.visa.service.DemandeVisaService;


@Controller
@RequestMapping("/demande-visa")
public class DemandeVisaController {
    private final DemandeVisaService demandeVisaService;

    public DemandeVisaController(DemandeVisaService demandeVisaService) {
        this.demandeVisaService = demandeVisaService;
    }

    @GetMapping("/visa-type")
    public String typeVisa(@RequestParam(value = "type_demande_id", required = false) Long typeDemandeId, Model model) {
        model.addAttribute("typesVisa", demandeVisaService.getAllTypesVisa());
        model.addAttribute("typeDemandeId", typeDemandeId);
        return "visa-type";
    }

    @GetMapping("/visa-form")
    public String visaForm(@RequestParam("typeVisaId") Long typeVisaId, @RequestParam(value = "type_demande_id", required = false) Long typeDemandeId, Model model) {
        model.addAttribute("typeVisaId", typeVisaId);
        model.addAttribute("typeDemandeId", typeDemandeId);
        model.addAttribute("champsCommuns", demandeVisaService.getChampsCommuns());
        model.addAttribute("champsSpecifiques", demandeVisaService.getChampsSpecifiques(typeVisaId));
        model.addAttribute("nationalites", demandeVisaService.getAllNationalites());
        model.addAttribute("situationsFamiliales", demandeVisaService.getAllSituationsFamiliales());    
        return "visa-form-a-remplir";
    }

    @GetMapping("/visa-recap")
    public String confirmationVisa() {
        return "visa-recap";
    }

    @GetMapping("/creer")
    public String creerDemandeVisa(Model model) {
        model.addAttribute("form", new CreerDemandeVisaForm());
        return "creer_demande_visa";
    }

    @PostMapping("/creer")
    public String enregistrerDemandeVisa(
            @ModelAttribute("form") CreerDemandeVisaForm form,
            RedirectAttributes redirectAttributes) {

        // 2. Définir explicitement la date de demande à aujourd'hui
        if (form.getDateDemande() == null) {
            form.setDateDemande(java.time.LocalDate.now());
        }

        System.out.println("--- DEBUT POST /demande-visa/creer ---");
        System.out.println("Type Visa ID: " + form.getTypeVisaId());
        System.out.println("Type Demande Visa ID: " + form.getTypeDemandeId());
        System.out.println("Nom: " + form.getNom() + " | Prenom: " + form.getPrenom());
        
        try {
            System.out.println("-> Appel de demandeVisaService.creerDemandeVisa()");
            demandeVisaService.creerDemandeVisa(form);
            System.out.println("-> Succes : Demande de visa creee en base de donnees.");
            redirectAttributes.addFlashAttribute("successMessage", "Les informations ont bien ete stockees.");
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            System.out.println("-> ERREUR (IllegalArgumentException) : " + e.getMessage());
            return "creer_demande_visa";
        } catch (Exception e) {
            System.out.println("-> ERREUR INATTENDUE : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/list")
    public String listDemandes() {
        return "list-demande-visa";
    }
}
