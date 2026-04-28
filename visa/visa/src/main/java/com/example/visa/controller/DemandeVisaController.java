package com.example.visa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.visa.dto.CreerDemandeVisaForm;
import com.example.visa.dto.PasseportForm;
import com.example.visa.service.DemandeVisaService;


@Controller
@RequestMapping("/demande-visa")
@SessionAttributes("passeportData")
public class DemandeVisaController {
    private final DemandeVisaService demandeVisaService;

    public DemandeVisaController(DemandeVisaService demandeVisaService) {
        this.demandeVisaService = demandeVisaService;
    }

    @GetMapping("/visa-type")
    public String typeVisa(@RequestParam(value = "type_demande_id", required = false) Long typeDemandeId, Model model) {
        PasseportForm passeportForm = new PasseportForm();
        passeportForm.setTypeDemandeId(typeDemandeId);
        model.addAttribute("passeportForm", passeportForm);
        model.addAttribute("typeDemandeId", typeDemandeId);
        model.addAttribute("nationalites", demandeVisaService.getAllNationalites());
        model.addAttribute("situationsFamiliales", demandeVisaService.getAllSituationsFamiliales());
        return "passport-form";
    }

    @PostMapping("/visa-type")
    public String creerPasseport(@ModelAttribute("passeportForm") PasseportForm passeportForm, 
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            // Stocker les données du passeport en session
            model.addAttribute("passeportData", passeportForm);
            redirectAttributes.addFlashAttribute("passeportData", passeportForm);
            return "redirect:/demande-visa/select-visa?type_demande_id=" + passeportForm.getTypeDemandeId();
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création du passeport: " + e.getMessage());
            return "passport-form";
        }
    }

    @GetMapping("/select-visa")
    public String selectVisa(@RequestParam(value = "type_demande_id", required = false) Long typeDemandeId, 
            Model model) {
        model.addAttribute("typesVisa", demandeVisaService.getAllTypesVisa());
        model.addAttribute("typeDemandeId", typeDemandeId);
        return "select-visa";
    }

    @PostMapping("/attach-visa")
    public String attachVisa(
            @RequestParam(value = "selectedVisa", required = false) String selectedVisa,
            @RequestParam(value = "typeDemandeId", required = false) Long typeDemandeId,
            @RequestParam(value = "passeportNom", required = false) String passeportNom,
            @RequestParam(value = "passeportPrenom", required = false) String passeportPrenom,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        if (selectedVisa == null || selectedVisa.isEmpty()) {
            model.addAttribute("error", "Veuillez selectionner un type de visa");
            model.addAttribute("typesVisa", demandeVisaService.getAllTypesVisa());
            model.addAttribute("typeDemandeId", typeDemandeId);
            return "select-visa";
        }

        try {
            Long typeVisaId = Long.parseLong(selectedVisa);
            model.addAttribute("typeVisaId", typeVisaId);
            model.addAttribute("typeDemandeId", typeDemandeId);
            model.addAttribute("champsCommuns", demandeVisaService.getChampsCommuns());
            model.addAttribute("champsSpecifiques", demandeVisaService.getChampsSpecifiques(typeVisaId));
            
            return "saisie-visa-dossiers";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la preparation de la saisie: " + e.getMessage());
            model.addAttribute("typesVisa", demandeVisaService.getAllTypesVisa());
            model.addAttribute("typeDemandeId", typeDemandeId);
            return "select-visa";
        }
    }

    @GetMapping("/visa-type-old")
    public String typeVisaOld(@RequestParam(value = "type_demande_id", required = false) Long typeDemandeId, Model model) {
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
        return "visa-form-a-remplir";
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
            int statutInitial = 1; // 1 = Créer
            if (form.getTypeDemandeId() != 1L) {
                statutInitial = 5; // Les demandes différentes de "Nouveau Titre" commencent avec "Approuvé"
            }
            demandeVisaService.creerDemandeVisa(form, statutInitial);
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
    public String listDemandes(Model model) {
        model.addAttribute("demandes", demandeVisaService.getAllDemandes());
        return "list-demande-visa";
    }
}
