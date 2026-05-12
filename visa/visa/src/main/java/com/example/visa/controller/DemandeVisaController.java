package com.example.visa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.visa.dto.CreerDemandeVisaForm;
import com.example.visa.dto.DemandeVisaResponseDto;
import com.example.visa.dto.FinaliserSansDonneesForm;
import com.example.visa.dto.FinaliserTransfertSansDonneesForm;
import com.example.visa.dto.PasseportForm;
import com.example.visa.dto.TransfertResult;
import com.example.visa.model.CarteResident;
import com.example.visa.model.DemandeVisa;
import com.example.visa.model.Passeport;
import com.example.visa.model.StatutDemande;
import com.example.visa.repository.DemandeVisaRepository;
import com.example.visa.repository.StatutDemandeRepository;
import com.example.visa.service.DemandeVisaService;

@Controller
@RequestMapping("/demande-visa")
@SessionAttributes({ "passeportData", "transfertData" })
public class DemandeVisaController {
    private static final Logger logger = LoggerFactory.getLogger(DemandeVisaController.class);
    private final DemandeVisaService demandeVisaService;
    private final StatutDemandeRepository statutDemandeRepository;
    private final DemandeVisaRepository demandeVisaRepository;

    public DemandeVisaController(DemandeVisaService demandeVisaService,
            StatutDemandeRepository statutDemandeRepository, DemandeVisaRepository demandeVisaRepository) {
        this.demandeVisaService = demandeVisaService;
        this.statutDemandeRepository = statutDemandeRepository;
        this.demandeVisaRepository = demandeVisaRepository;
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
            model.addAttribute("villesParPays", demandeVisaService.getVillesParPays());

            return "saisie-visa-dossiers";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la preparation de la saisie: " + e.getMessage());
            model.addAttribute("typesVisa", demandeVisaService.getAllTypesVisa());
            model.addAttribute("typeDemandeId", typeDemandeId);
            return "select-visa";
        }
    }

    @GetMapping("/visa-type-old")
    public String typeVisaOld(@RequestParam(value = "type_demande_id", required = false) Long typeDemandeId,
            Model model) {
        model.addAttribute("typesVisa", demandeVisaService.getAllTypesVisa());
        model.addAttribute("typeDemandeId", typeDemandeId);
        return "visa-type";
    }

    @GetMapping("/visa-form")
    public String visaForm(@RequestParam("typeVisaId") Long typeVisaId,
            @RequestParam(value = "type_demande_id", required = false) Long typeDemandeId, Model model) {
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

    @GetMapping("/nouveau-passeport")
    public String nouveauPasseport(
            @RequestParam(value = "type_demande_id", required = false) Long typeDemandeId,
            @RequestParam(value = "type_visa_id", required = false) Long typeVisaId,
            @RequestParam(value = "visa_id", required = false) Long visaId,
            Model model) {
        model.addAttribute("typeDemandeId", typeDemandeId);
        model.addAttribute("typeVisaId", typeVisaId);
        model.addAttribute("visaId", visaId);
        return "nouveau-passeport";
    }

    @PostMapping("/prepare-transfert")
    public String preparerTransfert(
            @ModelAttribute("form") FinaliserSansDonneesForm form,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("transfertData", form);
            return "redirect:/demande-visa/nouveau-passeport?type_demande_id=" + form.getTypeDemandeId()
                    + "&type_visa_id=" + form.getTypeVisaId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la preparation du transfert: " + e.getMessage());
            return "redirect:/demande-visa/select-visa?type_demande_id=" + form.getTypeDemandeId();
        }
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
            demandeVisaService.creerDemandeVisa(form, form.getTypeDemandeId(), statutInitial);
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

    @PostMapping("/finaliser-duplicata")
    public String finaliserDuplicata(
            @ModelAttribute("form") FinaliserSansDonneesForm form,
            @ModelAttribute("passeportData") PasseportForm passeportForm,
            RedirectAttributes redirectAttributes) {

        if (form.getDateDemande() == null) {
            form.setDateDemande(java.time.LocalDate.now());
        }

        if (passeportForm != null) {
            form.setNom(passeportForm.getNom());
            form.setPrenom(passeportForm.getPrenom());
            form.setNomJeuneFille(passeportForm.getNom_jeune_fille());
            form.setEmail(passeportForm.getEmail());
            form.setNumeroTelephone(passeportForm.getNumero_telephone());
            form.setDateNaissance(passeportForm.getDate_naissance());
            form.setLieuNaissance(passeportForm.getLieu_naissance());
            form.setAdresseMada(passeportForm.getAdresse_mada());
            form.setNationaliteId(passeportForm.getNationaliteId());
            form.setSituationFamilialeId(passeportForm.getSituationFamiliale());
            form.setNumeroPasseport(passeportForm.getNumero_passport());
            form.setDateExpirationPasseport(passeportForm.getDate_expiration());
            form.setDateDelivrancePasseport(passeportForm.getDate_delivrance());
            form.setVisaTranNumPasseport(passeportForm.getVisaTranNumPasseport());
            form.setVisaTranDateDelivrance(passeportForm.getVisaTranDateDelivrance());
            form.setVisaTranDateExpiration(passeportForm.getVisaTranDateExpiration());
        }

        try {
            System.out.println("-> Appel de demandeVisaService.creerDemandeDuplicatatSansDonnees()");
            CarteResident carte = demandeVisaService.creerDemandeDuplicatatSansDonnees(form);
            System.out.println("-> Succes : Demande de duplicata creee.");
            redirectAttributes.addFlashAttribute("successMessage", "La demande de duplicata a bien ete enregistree.");
            return "redirect:/demande-visa/duplicata-result?carte_id=" + carte.getId();
        } catch (Exception e) {
            System.out.println("-> ERREUR : " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la creation du duplicata: " + e.getMessage());
            return "redirect:/demande-visa/select-visa?type_demande_id=" + form.getTypeDemandeId();
        }
    }

    @PostMapping("/finaliser-transfert")
    public String finaliserTransfert(
            @ModelAttribute("form") FinaliserTransfertSansDonneesForm form,
            @ModelAttribute("passeportData") PasseportForm passeportForm,
            @ModelAttribute("transfertData") FinaliserSansDonneesForm transfertData,
            RedirectAttributes redirectAttributes) {

        if (form.getDateDemande() == null) {
            form.setDateDemande(java.time.LocalDate.now());
        }

        if (passeportForm != null) {
            form.setNom(passeportForm.getNom());
            form.setPrenom(passeportForm.getPrenom());
            form.setNomJeuneFille(passeportForm.getNom_jeune_fille());
            form.setEmail(passeportForm.getEmail());
            form.setNumeroTelephone(passeportForm.getNumero_telephone());
            form.setDateNaissance(passeportForm.getDate_naissance());
            form.setLieuNaissance(passeportForm.getLieu_naissance());
            form.setAdresseMada(passeportForm.getAdresse_mada());
            form.setNationaliteId(passeportForm.getNationaliteId());
            form.setSituationFamilialeId(passeportForm.getSituationFamiliale());
            form.setNumeroPasseport(passeportForm.getNumero_passport());
            form.setDateExpirationPasseport(passeportForm.getDate_expiration());
            form.setDateDelivrancePasseport(passeportForm.getDate_delivrance());
            form.setVisaTranNumPasseport(passeportForm.getVisaTranNumPasseport());
            form.setVisaTranDateDelivrance(passeportForm.getVisaTranDateDelivrance());
            form.setVisaTranDateExpiration(passeportForm.getVisaTranDateExpiration());
        }

        if (transfertData != null) {
            form.setAncienNumeroVisa(transfertData.getAncienNumeroVisa());
            form.setAncienDateDelivrance(transfertData.getAncienDateDelivrance());
            form.setAncienDateExpiration(transfertData.getAncienDateExpiration());
            form.setAncienVilleId(transfertData.getAncienVilleId());
            form.setAncienNumeroCarteResident(transfertData.getAncienNumeroCarteResident());
            form.setTypeVisaId(transfertData.getTypeVisaId());
            form.setTypeDemandeId(transfertData.getTypeDemandeId());
            form.setChampsCommunsCoches(transfertData.getChampsCommunsCoches());
            form.setChampsSpecifiquesCoches(transfertData.getChampsSpecifiquesCoches());
        }

        try {
            System.out.println("-> Appel de demandeVisaService.creerDemandeTransfertSansDonnees()");
            TransfertResult result = demandeVisaService.creerDemandeTransfertSansDonnees(form);
            System.out.println("-> Succes : Demande de transfert creee.");
            redirectAttributes.addFlashAttribute("successMessage", "La demande de transfert a bien ete enregistree.");
            return "redirect:/demande-visa/transfert-result?visa_id=" + result.getVisaId()
                    + "&passeport_id=" + result.getPasseportId();
        } catch (Exception e) {
            System.out.println("-> ERREUR : " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la creation du transfert: " + e.getMessage());
            return "redirect:/demande-visa/nouveau-passeport?type_demande_id=" + form.getTypeDemandeId()
                    + "&type_visa_id=" + form.getTypeVisaId();
        }
    }

    @PostMapping("/transfert-avec-donnees")
    public String transfertAvecDonnees(
            @RequestParam("visa_id") Long visaId,
            @RequestParam("nouveauNumeroPasseport") String nouveauNumeroPasseport,
            @RequestParam("nouveauDateDelivrance") java.time.LocalDate nouveauDateDelivrance,
            @RequestParam("nouveauDateExpiration") java.time.LocalDate nouveauDateExpiration,
            RedirectAttributes redirectAttributes) {
        try {
            Passeport nouveauPasseport = demandeVisaService.creerTransfertAvecDonnees(
                    visaId,
                    nouveauNumeroPasseport,
                    nouveauDateDelivrance,
                    nouveauDateExpiration);
            redirectAttributes.addFlashAttribute("successMessage", "Transfert cree avec succes.");
            return "redirect:/demande-visa/transfert-result?visa_id=" + visaId
                    + "&passeport_id=" + nouveauPasseport.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors du transfert: " + e.getMessage());
            return "redirect:/demande-visa/nouveau-passeport?type_demande_id=3&visa_id=" + visaId;
        }
    }

    @GetMapping("/list")
    public String listDemandes(Model model,
            @RequestParam(value = "type_demande_id", required = false) Long typeDemandeId) {
        List<DemandeVisa> demandes = demandeVisaService.getAllDemandes();
        Map<Long, String> statutLabels = new HashMap<>();
        for (DemandeVisa demande : demandes) {
            String label = statutDemandeRepository
                    .findLatestByDemandeVisaId(demande.getId(), PageRequest.of(0, 1))
                    .stream()
                    .findFirst()
                    .map(StatutDemande::getTypeStatutDemande)
                    .map(type -> type.getLabel())
                    .orElse("Creer");
            statutLabels.put(demande.getId(), label);
        }

        model.addAttribute("demandes", demandes);
        model.addAttribute("statutLabels", statutLabels);
        model.addAttribute("typeDemandeId", typeDemandeId);

        return "list-demande-visa";
    }

    @GetMapping("/recherche-duplicata")
    public String rechercheDuplicata(
            @RequestParam("rechercheType") String rechercheType,
            @RequestParam("rechercheValeur") String rechercheValeur,
            @RequestParam(value = "type_demande_id", required = false) Long typeDemandeId,
            Model model) {
        model.addAttribute("typeDemandeId", typeDemandeId);
        model.addAttribute("searchType", rechercheType);
        model.addAttribute("searchValue", rechercheValeur);

        return demandeVisaService.rechercherVisaPourDuplicata(rechercheType, rechercheValeur)
                .map(visa -> {
                    model.addAttribute("visaResult", visa);
                    return "list-demande-visa";
                })
                .orElseGet(() -> {
                    model.addAttribute("errorMessage", "Aucun visa correspondant trouve.");
                    return "list-demande-visa";
                });
    }

    @PostMapping("/dupliquer-visa")
    public String dupliquerVisa(
            @RequestParam("visa_id") Long visaId,
            RedirectAttributes redirectAttributes) {
        try {
            CarteResident carte = demandeVisaService.creerDuplicataAvecDonnees(visaId);
            redirectAttributes.addFlashAttribute("successMessage", "Carte resident dupliquee avec succes.");
            return "redirect:/demande-visa/duplicata-result?carte_id=" + carte.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la duplication: " + e.getMessage());
            return "redirect:/demande-visa/list";
        }
    }

    @GetMapping("/duplicata-result")
    public String afficherDuplicataResultat(
            @RequestParam("carte_id") Long carteId,
            Model model) {
        return demandeVisaService.getCarteResidentById(carteId)
                .map(carte -> {
                    model.addAttribute("carteResident", carte);
                    model.addAttribute("visaResult", carte.getVisa());
                    return "duplicata-result";
                })
                .orElseGet(() -> {
                    model.addAttribute("errorMessage", "Carte resident introuvable.");
                    return "duplicata-result";
                });
    }

    @GetMapping("/transfert-result")
    public String afficherTransfertResultat(
            @RequestParam("visa_id") Long visaId,
            @RequestParam("passeport_id") Long passeportId,
            Model model) {
        model.addAttribute("visaResult", demandeVisaService.getVisaById(visaId).orElse(null));
        model.addAttribute("nouveauPasseport", demandeVisaService.getPasseportById(passeportId).orElse(null));
        if (model.getAttribute("visaResult") == null || model.getAttribute("nouveauPasseport") == null) {
            model.addAttribute("errorMessage", "Resultat introuvable pour le transfert.");
        }
        return "transfert-result";
    }

    @GetMapping("/reference/{reference}")
    @ResponseBody
    public ResponseEntity<List<DemandeVisaResponseDto>> getDemandesByReference(
            @PathVariable String reference) {
        List<DemandeVisaResponseDto> data = new ArrayList<>();
        Set<Long> seenIds = new LinkedHashSet<>();
        java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        try {
            Set<Long> etatCivilIds = new java.util.HashSet<>();
            
            // Try numeric reference first
            try {
                Long reference_long = Long.valueOf(reference);
                DemandeVisa demande_id = demandeVisaRepository.findById(reference_long).orElse(null);
                if (demande_id != null && demande_id.getPasseport() != null && demande_id.getPasseport().getEtatCivil() != null) {
                    etatCivilIds.add(demande_id.getPasseport().getEtatCivil().getId());
                }
                List<DemandeVisa> demande_passeport_id = demandeVisaRepository.findByPasseport_Id(reference_long);
                if (demande_passeport_id != null) {
                    for (DemandeVisa demande : demande_passeport_id) {
                        if (demande.getPasseport() != null && demande.getPasseport().getEtatCivil() != null) {
                            etatCivilIds.add(demande.getPasseport().getEtatCivil().getId());
                        }
                    }
                }
            } catch (NumberFormatException e) {
                // Not a numeric reference, continue with text search
            }
            
            // Search by exact passport number
            List<DemandeVisa> demandes_passeport_ref = demandeVisaRepository.findByPasseport_NumPasseport(reference);
            if (demandes_passeport_ref != null) {
                for (DemandeVisa demande : demandes_passeport_ref) {
                    if (demande.getPasseport() != null && demande.getPasseport().getEtatCivil() != null) {
                        etatCivilIds.add(demande.getPasseport().getEtatCivil().getId());
                    }
                }
            }
            
            // Now retrieve all demandes for these etatCivilIds, sorted chronologically
            for (Long etatCivilId : etatCivilIds) {
                List<DemandeVisa> allDemandesForDemandeur = demandeVisaRepository.findByPasseport_EtatCivil_IdOrderByDateDemandeAsc(etatCivilId);
                if (allDemandesForDemandeur != null) {
                    for (DemandeVisa demande : allDemandesForDemandeur) {
                        if (!seenIds.contains(demande.getId())) {
                            seenIds.add(demande.getId());
                            data.add(buildResponseDto(demande, dateFormatter));
                        }
                    }
                }
            }
            
            return ResponseEntity.ok(data);

        } catch (Exception e) {
            logger.error("Error retrieving demandes for reference: {}", reference, e);
            throw e;
        }
    }

    private DemandeVisaResponseDto buildResponseDto(DemandeVisa demande, java.time.format.DateTimeFormatter dateFormatter) {
        // Get current status
        String currentStatut = statutDemandeRepository
                .findLatestByDemandeVisaId(demande.getId(), PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .map(StatutDemande::getTypeStatutDemande)
                .map(type -> type.getLabel())
                .orElse("Creer");

        // Get history
        List<java.util.Map<String, String>> historique = statutDemandeRepository
                .findByDemandeVisaIdOrderByDateStatutDesc(demande.getId())
                .stream()
                .map(statut -> {
                    java.util.Map<String, String> item = new java.util.HashMap<>();
                    String dateValue = statut.getDateStatut() == null
                            ? ""
                            : statut.getDateStatut().format(dateFormatter);
                    item.put("label", statut.getTypeStatutDemande().getLabel());
                    item.put("date", dateValue);
                    return item;
                })
                .collect(java.util.stream.Collectors.toList());

        return new DemandeVisaResponseDto(demande, currentStatut, historique);
    }

    // @GetMapping("/reference/exact/{reference}")
    // @ResponseBody
    // public ResponseEntity<List<DemandeVisa>> getDemandesByReferenceExact(
    //         @PathVariable String reference) {
    //     try {
    //         if (reference == null || reference.trim().isEmpty()) {
    //             return ResponseEntity.badRequest().build();
    //         }

    //         String trimmed = reference.trim();
    //         Set<DemandeVisa> results = new LinkedHashSet<>();

    //         try {
    //             Long refId = Long.parseLong(trimmed);
    //             results.addAll(this.demandeVisaRepository.findByDemandeId(refId));
    //             results.addAll(this.demandeVisaRepository.findByPasseportId(refId));
    //         } catch (NumberFormatException e) {
    //             logger.debug("Reference non numerique pour recherche exacte: {}", trimmed);
    //         }

    //         results.addAll(this.demandeVisaRepository.findByPasseportNumero(trimmed));

    //         return ResponseEntity.ok(new ArrayList<>(results));
    //     } catch (Exception e) {
    //         logger.error("Erreur lors de la recuperation des demandes pour la reference exacte: {}", reference, e);
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }

}
