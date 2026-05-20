package com.example.visa.controller;

import com.example.visa.service.DemandeVisaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.google.zxing.WriterException;
import java.io.IOException;

@RestController
public class DemandeVisaApiController {
    private final DemandeVisaService demandeVisaService;

    private static final String QR_URL_BASE = "http://localhost:5173?type=demande&ref=";

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

    @GetMapping("/api/demandes/{id}/lettre-reception")
    public ResponseEntity<byte[]> lettreReception(@PathVariable("id") Long id) throws IOException, WriterException {
        byte[] pdf = demandeVisaService.generateLettreReceptionPdf(id, QR_URL_BASE);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"lettre-reception.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
