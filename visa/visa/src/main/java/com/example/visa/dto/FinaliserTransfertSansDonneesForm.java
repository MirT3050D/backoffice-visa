package com.example.visa.dto;

import java.time.LocalDate;

public class FinaliserTransfertSansDonneesForm extends FinaliserSansDonneesForm {
    private String nouveauNumeroPasseport;
    private LocalDate nouveauDateDelivrance;
    private LocalDate nouveauDateExpiration;

    public String getNouveauNumeroPasseport() {
        return nouveauNumeroPasseport;
    }

    public void setNouveauNumeroPasseport(String nouveauNumeroPasseport) {
        this.nouveauNumeroPasseport = nouveauNumeroPasseport;
    }

    public LocalDate getNouveauDateDelivrance() {
        return nouveauDateDelivrance;
    }

    public void setNouveauDateDelivrance(LocalDate nouveauDateDelivrance) {
        this.nouveauDateDelivrance = nouveauDateDelivrance;
    }

    public LocalDate getNouveauDateExpiration() {
        return nouveauDateExpiration;
    }

    public void setNouveauDateExpiration(LocalDate nouveauDateExpiration) {
        this.nouveauDateExpiration = nouveauDateExpiration;
    }
}
