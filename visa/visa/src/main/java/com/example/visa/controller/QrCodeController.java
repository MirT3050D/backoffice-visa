package com.example.visa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.visa.service.QrCodeService;
import com.google.zxing.WriterException;

@Controller
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @Value("${app.server.ip}")
    private String serverIp;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/qr/{id}")
    public ResponseEntity<byte[]> getQrCode(@PathVariable("id") Long id) {
        try {
            String content = "http://" + serverIp + ":5173?type=demande&ref=" + id;
            byte[] qrCode = qrCodeService.generateQrCode(content, 200, 200);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCode);
        } catch (WriterException | IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
