package com.example.visa.dto;

import java.util.List;
import java.util.Map;

import com.example.visa.model.DemandeVisa;

public class DemandeVisaResponseDto {
    private DemandeVisa demande;
    private String currentStatut;
    private List<Map<String, String>> historique;

    public DemandeVisaResponseDto() {
    }

    public DemandeVisaResponseDto(DemandeVisa demande, String currentStatut, List<Map<String, String>> historique) {
        this.demande = demande;
        this.currentStatut = currentStatut;
        this.historique = historique;
    }

    public DemandeVisa getDemande() {
        return demande;
    }

    public void setDemande(DemandeVisa demande) {
        this.demande = demande;
    }

    public String getCurrentStatut() {
        return currentStatut;
    }

    public void setCurrentStatut(String currentStatut) {
        this.currentStatut = currentStatut;
    }

    public List<Map<String, String>> getHistorique() {
        return historique;
    }

    public void setHistorique(List<Map<String, String>> historique) {
        this.historique = historique;
    }
}
