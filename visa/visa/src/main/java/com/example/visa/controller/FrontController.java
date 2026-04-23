package com.example.visa.controller;

import com.example.visa.dto.DemandeVisaEditForm;
import com.example.visa.service.DemandeVisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.visa.model.DemandeVisa;
import com.example.visa.model.Dossier;
import com.example.visa.repository.TypeDemandeVisaRepository;
import com.example.visa.repository.DemandeVisaRepository;
import com.example.visa.repository.DossierRepository;
import com.example.visa.model.TypeDemandeVisa;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class FrontController {

    @Autowired
    private TypeDemandeVisaRepository typeDemandeVisaRepository;

    @Autowired
    private DemandeVisaRepository demandeVisaRepository;

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private DemandeVisaService demandeVisaService;

    @GetMapping("/")
    public String index(Model model) {
        List<TypeDemandeVisa> typeDemandes = typeDemandeVisaRepository.findAll();
        model.addAttribute("typeDemandes", typeDemandes);
        return "index";
    }

    @GetMapping("/creation")
    public String creation() {
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<DemandeVisa> demandes = demandeVisaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(DemandeVisa::getId, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
        model.addAttribute("demandes", demandes);
        return "list";
    }

    @GetMapping("/list/{id}")
    public String detail(@PathVariable Long id, Model model) {
        DemandeVisa demande = demandeVisaRepository.findById(id).orElse(null);
        if (demande == null) {
            return "redirect:/list";
        }

        List<Dossier> dossiers = dossierRepository.findByDemandeVisaIdOrderByIdAsc(id);
        List<Dossier> dossiersCommuns = dossiers.stream()
                .filter(dossier -> dossier.getChampFournirCommune() != null)
                .toList();
        List<Dossier> dossiersSpecifiques = dossiers.stream()
                .filter(dossier -> dossier.getChampFournirSpecifique() != null)
                .toList();

        model.addAttribute("demande", demande);
        model.addAttribute("dossiersCommuns", dossiersCommuns);
        model.addAttribute("dossiersSpecifiques", dossiersSpecifiques);
        return "detail";
    }

    @GetMapping("/list/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam(value = "typeVisaId", required = false) Long selectedTypeVisaId,
                       Model model) {
        DemandeVisa demande = demandeVisaService.getDemandeById(id).orElse(null);
        if (demande == null) {
            return "redirect:/list";
        }

        List<Dossier> dossiers = dossierRepository.findByDemandeVisaIdOrderByIdAsc(id);
        Set<Long> champsCommunsCoches = dossiers.stream()
            .filter(d -> d.getChampFournirCommune() != null && d.isEstCoche())
            .map(d -> d.getChampFournirCommune().getId())
            .collect(Collectors.toSet());
        Set<Long> champsSpecifiquesCoches = dossiers.stream()
            .filter(d -> d.getChampFournirSpecifique() != null && d.isEstCoche())
            .map(d -> d.getChampFournirSpecifique().getId())
            .collect(Collectors.toSet());

        DemandeVisaEditForm form = new DemandeVisaEditForm();
        form.setDateDemande(demande.getDate_demande());
        form.setTypeDemandeId(demande.getType_demande_visa().getId());
        form.setTypeVisaId(demande.getType_visa().getId());
        if (selectedTypeVisaId != null) {
            form.setTypeVisaId(selectedTypeVisaId);
        }
        form.setNom(demande.getPasseport().getEtatCivil().getNom());
        form.setPrenom(demande.getPasseport().getEtatCivil().getPrenom());
        form.setNomJeuneFille(demande.getPasseport().getEtatCivil().getNom_jeune_fille());
        form.setEmail(demande.getPasseport().getEtatCivil().getEmail());
        form.setNumeroTelephone(demande.getPasseport().getEtatCivil().getNumero_telephone());
        form.setDateNaissance(demande.getPasseport().getEtatCivil().getDate_naissance());
        form.setLieuNaissance(demande.getPasseport().getEtatCivil().getLieu_naissance());
        form.setAdresseMada(demande.getPasseport().getEtatCivil().getAdresse_mada());
        form.setNationaliteId(demande.getPasseport().getEtatCivil().getNationalite().getId());
        form.setSituationFamilialeId(demande.getPasseport().getEtatCivil().getSituation_familiale().getId());
        form.setNumeroPasseport(demande.getPasseport().getNumero_passport());
        form.setDateDelivrancePasseport(demande.getPasseport().getDate_delivrance());
        form.setDateExpirationPasseport(demande.getPasseport().getDate_expiration());
        demandeVisaService.getVisaTransformableByEtatCivilId(demande.getPasseport().getEtatCivil().getId())
                .ifPresent(visaTransformable -> {
                    form.setVisaTranNumPasseport(visaTransformable.getNumero_passport());
                    form.setVisaTranDateDelivrance(visaTransformable.getDate_delivrance());
                    form.setVisaTranDateExpiration(visaTransformable.getDate_expiration());
                });
        form.setChampsCommunsCoches(champsCommunsCoches.stream().toList());
        form.setChampsSpecifiquesCoches(champsSpecifiquesCoches.stream().toList());

        model.addAttribute("demande", demande);
        model.addAttribute("form", form);
        model.addAttribute("typesDemande", demandeVisaService.getAllTypesDemandeVisa());
        model.addAttribute("typesVisa", demandeVisaService.getAllTypesVisa());
        model.addAttribute("nationalites", demandeVisaService.getAllNationalites());
        model.addAttribute("situationsFamiliales", demandeVisaService.getAllSituationsFamiliales());
        model.addAttribute("champsCommuns", demandeVisaService.getChampsCommuns());
        model.addAttribute("champsSpecifiques", demandeVisaService.getChampsSpecifiques(form.getTypeVisaId()));
        model.addAttribute("champsCommunsCoches", champsCommunsCoches);
        model.addAttribute("champsSpecifiquesCoches", champsSpecifiquesCoches);
        return "edit";
    }

    @PostMapping("/list/{id}/edit")
    public String editSubmit(
            @PathVariable Long id,
            @ModelAttribute("form") DemandeVisaEditForm form,
            RedirectAttributes redirectAttributes) {
        try {
            demandeVisaService.updateDemandeVisa(id, form);
            redirectAttributes.addFlashAttribute("successMessage", "Demande modifiee avec succes.");
            return "redirect:/list/" + id;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/list";
        }
    }

    @PostMapping("/list/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            demandeVisaService.deleteDemandeVisa(id);
            redirectAttributes.addFlashAttribute("successMessage", "Demande supprimee avec succes.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/list";
    }

    @GetMapping("/visa-demande")
    public String formdemande() {
        return "visa-demande";
    }

}
