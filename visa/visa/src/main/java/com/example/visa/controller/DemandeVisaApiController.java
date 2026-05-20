package com.example.visa.controller;

import com.example.visa.service.DemandeVisaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemandeVisaApiController {
    private final DemandeVisaService demandeVisaService;

    public DemandeVisaApiController(DemandeVisaService demandeVisaService) {
        this.demandeVisaService = demandeVisaService;
    }

    @GetMapping("/api/demandes/{id}/pieces-jointes/fusion")
    public ResponseEntity<byte[]> piecesJointesFusion(@PathVariable("id") Long id) {
        byte[] pdf = demandeVisaService.fusionnerPiecesJointes(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"justificatifs.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
