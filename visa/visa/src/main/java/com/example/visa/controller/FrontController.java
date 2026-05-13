package com.example.visa.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.visa.dto.DemandeVisaEditForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.visa.model.DemandeVisa;
import com.example.visa.model.Dossier;
import com.example.visa.model.StatutDemande;
import com.example.visa.model.TypeDemandeVisa;
import com.example.visa.repository.DemandeVisaRepository;
import com.example.visa.repository.DossierRepository;
import com.example.visa.repository.StatutDemandeRepository;
import com.example.visa.repository.TypeDemandeVisaRepository;
import com.example.visa.service.DemandeVisaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;

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

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<DemandeVisa> demandes = demandeVisaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(DemandeVisa::getId, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
        Map<Long, String> statutLabels = new HashMap<>();
        Map<Long, List<Map<String, String>>> statutHistory = new HashMap<>();
        for (DemandeVisa demande : demandes) {
            String label = statutDemandeRepository
                    .findLatestByDemandeVisaId(demande.getId(), PageRequest.of(0, 1))
                    .stream()
                    .findFirst()
                    .map(StatutDemande::getTypeStatutDemande)
                    .map(type -> type.getLabel())
                    .orElse("Creer");
            statutLabels.put(demande.getId(), label);
            List<Map<String, String>> historyItems = statutDemandeRepository
                .findByDemandeVisaIdOrderByDateStatutDesc(demande.getId())
                .stream()
                .map(statut -> {
                Map<String, String> item = new HashMap<>();
                String dateValue = statut.getDateStatut() == null
                    ? ""
                    : statut.getDateStatut().format(dateFormatter);
                item.put("label", statut.getTypeStatutDemande().getLabel());
                item.put("date", dateValue);
                return item;
                })
                .collect(Collectors.toList());
            statutHistory.put(demande.getId(), historyItems);
        }

        model.addAttribute("demandes", demandes);
        model.addAttribute("statutLabels", statutLabels);
        model.addAttribute("statutHistory", statutHistory);
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

    @GetMapping("/demande/{id}/scan")
    public String scan(@PathVariable Long id, Model model) {
        demandeVisaService.verifierAutorisationScan(id);
        DemandeVisa demande = demandeVisaRepository.findById(id).orElse(null);
        if (demande == null) {
            return "redirect:/list";
        }

        List<Dossier> dossiers = dossierRepository.findByDemandeVisaIdOrderByIdAsc(id);
        List<Dossier> dossiersRequis = dossiers.stream()
                .filter(Dossier::isEstCoche)
                .toList();
        boolean isComplet = dossiersRequis.stream()
                .allMatch(dossier -> dossier.getPathFichier() != null && !dossier.getPathFichier().isBlank());

        model.addAttribute("demande", demande);
        model.addAttribute("dossiers", dossiersRequis);
        model.addAttribute("isComplet", isComplet);
        return "scan-demande";
    }

    @GetMapping("/demande/{id}/signature")
    public String signaturePage(@PathVariable Long id, Model model) {
        DemandeVisa demande = demandeVisaRepository.findById(id).orElse(null);
        if (demande == null) {
            return "redirect:/list";
        }
        model.addAttribute("demande", demande);
        return "signature-demande";
    }

    @PostMapping("/demande/{idDemande}/upload-signature")
    public ResponseEntity<?> uploadSignature(@PathVariable Long idDemande, @RequestParam("signature") MultipartFile signatureFile) {
        try {
            DemandeVisa demande = demandeVisaRepository.findById(idDemande).orElse(null);
            if (demande == null) {
                return ResponseEntity.notFound().build();
            }

            if (!signatureFile.isEmpty()) {
                String fileName = "signature_" + idDemande + "_" + java.util.UUID.randomUUID().toString() + ".png";
                Path uploadPath = Paths.get("uploads/signature");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(signatureFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                demande.setCheminSignature("uploads/signature/" + fileName);
                demandeVisaRepository.save(demande);
                
                // Changer le statut de la demande en "Signature créée" (rang 4)
                // demandeVisaService.changerStatutDemande(idDemande, 3);
                // ✅ IMPORTANT : mise à jour statut global
                demandeVisaService.mettreAJourStatutMedia(idDemande);
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/demande/{idDemande}/upload")
    public String uploadPiece(
            @PathVariable Long idDemande,
            @RequestParam("dossierId") Long dossierId,
            @RequestParam("fichier") Part fichier,
            RedirectAttributes redirectAttributes) {
        try {
            demandeVisaService.uploadPiece(idDemande, dossierId, fichier);
            redirectAttributes.addFlashAttribute("successMessage", "Fichier charge avec succes.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'upload: " + e.getMessage());
        }
        return "redirect:/demande/" + idDemande + "/scan";
    }

    @PostMapping("/demande/{idDemande}/upload-multi")
    public String uploadPieces(
            @PathVariable Long idDemande,
            HttpServletRequest request,
            @RequestParam(value = "singleDossierId", required = false) Long singleDossierId,
            RedirectAttributes redirectAttributes) {
        int uploaded = 0;
        int skipped = 0;
        try {
            if (singleDossierId != null) {
                Part part = request.getPart("fichier_" + singleDossierId);
                if (part == null || part.getSize() == 0) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Aucun fichier selectionne.");
                    return "redirect:/demande/" + idDemande + "/scan";
                }
                demandeVisaService.uploadPiece(idDemande, singleDossierId, part);
                redirectAttributes.addFlashAttribute("successMessage", "Fichier charge avec succes.");
                return "redirect:/demande/" + idDemande + "/scan";
            }

            for (Part part : request.getParts()) {
                String name = part.getName();
                if (!name.startsWith("fichier_")) {
                    continue;
                }
                if (part.getSize() == 0) {
                    skipped++;
                    continue;
                }

                String idPart = name.substring("fichier_".length());
                Long dossierId = Long.parseLong(idPart);
                demandeVisaService.uploadPiece(idDemande, dossierId, part);
                uploaded++;
            }
            if (uploaded > 0) {
                redirectAttributes.addFlashAttribute("successMessage", "Fichiers charges: " + uploaded + ".");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Aucun fichier selectionne.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'upload: " + e.getMessage());
        }

        return "redirect:/demande/" + idDemande + "/scan";
    }

    @PostMapping("/demande/{idDemande}/verrouiller")
    public String verrouiller(@PathVariable Long idDemande, RedirectAttributes redirectAttributes) {
        try {
            demandeVisaService.verrouillerDemande(idDemande);
            redirectAttributes.addFlashAttribute("successMessage", "Demande verrouillee avec succes.");
            return "redirect:/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors du verrouillage: " + e.getMessage());
            return "redirect:/demande/" + idDemande + "/scan";
        }
    }

    @GetMapping("/demande/{idDemande}/files/{idChamp}")
    public ResponseEntity<Resource> downloadPiece(
            @PathVariable Long idDemande,
            @PathVariable Long idChamp) {
        Dossier dossier = dossierRepository
                .findByIdAndDemandeVisaId(idChamp, idDemande)
                .orElse(null);
        if (dossier == null || dossier.getPathFichier() == null || dossier.getPathFichier().isBlank()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path path = Paths.get(dossier.getPathFichier());
            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(path.toUri());
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
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
        form.setDateDemande(demande.getDateDemande());
        form.setTypeDemandeId(demande.getTypeDemandeVisa().getId());
        form.setTypeVisaId(demande.getTypeVisa().getId());
        if (selectedTypeVisaId != null) {
            form.setTypeVisaId(selectedTypeVisaId);
        }
        form.setNom(demande.getPasseport().getEtatCivil().getNom());
        form.setPrenom(demande.getPasseport().getEtatCivil().getPrenom());
        form.setNomJeuneFille(demande.getPasseport().getEtatCivil().getNomJeuneFille());
        form.setEmail(demande.getPasseport().getEtatCivil().getEmail());
        form.setNumeroTelephone(demande.getPasseport().getEtatCivil().getNumTel());
        form.setDateNaissance(demande.getPasseport().getEtatCivil().getDateNaissance());
        form.setLieuNaissance(demande.getPasseport().getEtatCivil().getLieuNaissance());
        form.setAdresseMada(demande.getPasseport().getEtatCivil().getAdresseMada());
        form.setNationaliteId(demande.getPasseport().getEtatCivil().getNationalite().getId());
        form.setSituationFamilialeId(demande.getPasseport().getEtatCivil().getSituationFamiliale().getId());
        form.setNumeroPasseport(demande.getPasseport().getNumPasseport());
        form.setDateDelivrancePasseport(demande.getPasseport().getDateDelivrance());
        form.setDateExpirationPasseport(demande.getPasseport().getDateExpiration());
        demandeVisaService.getVisaTransformableByEtatCivilId(demande.getPasseport().getEtatCivil().getId())
                .ifPresent(visaTransformable -> {
                    form.setVisaTranNumPasseport(visaTransformable.getNumeroPassport());
                    form.setVisaTranDateDelivrance(visaTransformable.getDateDelivrance());
                    form.setVisaTranDateExpiration(visaTransformable.getDateExpiration());
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

    @GetMapping("/demande/{id}/photo")
    public String openCamera(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "photo"; // photo.jsp
    }

    @PostMapping("/demande/{id}/photo")
    @ResponseBody
    public String savePhoto(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        demandeVisaService.enregistrerPhotoDemande(
                id,
                body.get("image")
        );
        return "OK";
    }

    @GetMapping("/demande/{id}/photo/view")
    @ResponseBody
    public ResponseEntity<Resource> afficherPhoto(
            @PathVariable Long id) throws IOException {

        Path photoPath = Paths.get(
                "uploads",
                "demande-" + id,
                "photo-" + id + ".png"
        );

        if (!Files.exists(photoPath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(photoPath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    @GetMapping("/demande/{id}/signature/view")
    @ResponseBody
    public ResponseEntity<Resource> afficherSignature(
            @PathVariable Long id) throws IOException {

        DemandeVisa demande = demandeVisaRepository.findById(id)
                .orElse(null);

        if (demande == null || demande.getCheminSignature() == null) {
            return ResponseEntity.notFound().build();
        }

        Path signaturePath = Paths.get(demande.getCheminSignature());

        if (!Files.exists(signaturePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(signaturePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
