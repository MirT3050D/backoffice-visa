package com.example.visa.dto;

import java.time.LocalDate;
import java.util.List;

public class FinaliserSansDonneesForm extends CreerDemandeVisaForm {
    
    // Champs pour l'ancien titre (Visa existant)
    private String ancienNumeroVisa;
    private LocalDate ancienDateDelivrance;
    private LocalDate ancienDateExpiration;
    private Long ancienVilleId; // Pour la delivrance du visa existant

    // Champs pour la carte resident existante (optionnel)
    private String ancienNumeroCarteResident;
    
    public String getAncienNumeroVisa() { return ancienNumeroVisa; }
    public void setAncienNumeroVisa(String ancienNumeroVisa) { this.ancienNumeroVisa = ancienNumeroVisa; }
    
    public LocalDate getAncienDateDelivrance() { return ancienDateDelivrance; }
    public void setAncienDateDelivrance(LocalDate ancienDateDelivrance) { this.ancienDateDelivrance = ancienDateDelivrance; }

    public LocalDate getAncienDateExpiration() { return ancienDateExpiration; }
    public void setAncienDateExpiration(LocalDate ancienDateExpiration) { this.ancienDateExpiration = ancienDateExpiration; }

    public Long getAncienVilleId() { return ancienVilleId; }
    public void setAncienVilleId(Long ancienVilleId) { this.ancienVilleId = ancienVilleId; }

    public String getAncienNumeroCarteResident() { return ancienNumeroCarteResident; }
    public void setAncienNumeroCarteResident(String ancienNumeroCarteResident) { this.ancienNumeroCarteResident = ancienNumeroCarteResident; }
}