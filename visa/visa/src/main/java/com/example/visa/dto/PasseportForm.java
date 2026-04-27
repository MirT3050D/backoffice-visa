package com.example.visa.dto;

import java.time.LocalDate;

/**
 * DTO pour la création d'un Passeport avec les informations associées d'EtatCivil
 * Aligné avec les modèles: Passeport et EtatCivil
 */
public class PasseportForm {
    // Champs EtatCivil
    private String nom;
    private String prenom;
    private String nom_jeune_fille;
    private String email;
    private String numero_telephone;
    private LocalDate date_naissance;
    private String lieu_naissance;
    private String adresse_mada;
    private Long nationaliteId;
    private Long situationFamiliale;
    
    // Champs Passeport
    private String numero_passport;
    private LocalDate date_expiration;
    private LocalDate date_delivrance;
    
    // Champs métier
    private Long typeDemandeId;

    // === Getters et Setters EtatCivil ===
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom_jeune_fille() {
        return nom_jeune_fille;
    }

    public void setNom_jeune_fille(String nom_jeune_fille) {
        this.nom_jeune_fille = nom_jeune_fille;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero_telephone() {
        return numero_telephone;
    }

    public void setNumero_telephone(String numero_telephone) {
        this.numero_telephone = numero_telephone;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getLieu_naissance() {
        return lieu_naissance;
    }

    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public String getAdresse_mada() {
        return adresse_mada;
    }

    public void setAdresse_mada(String adresse_mada) {
        this.adresse_mada = adresse_mada;
    }

    public Long getNationaliteId() {
        return nationaliteId;
    }

    public void setNationaliteId(Long nationaliteId) {
        this.nationaliteId = nationaliteId;
    }

    public Long getSituationFamiliale() {
        return situationFamiliale;
    }

    public void setSituationFamiliale(Long situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    // === Getters et Setters Passeport ===
    
    public String getNumero_passport() {
        return numero_passport;
    }

    public void setNumero_passport(String numero_passport) {
        this.numero_passport = numero_passport;
    }

    public LocalDate getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(LocalDate date_expiration) {
        this.date_expiration = date_expiration;
    }

    public LocalDate getDate_delivrance() {
        return date_delivrance;
    }

    public void setDate_delivrance(LocalDate date_delivrance) {
        this.date_delivrance = date_delivrance;
    }

    // === Getters et Setters métier ===
    
    public Long getTypeDemandeId() {
        return typeDemandeId;
    }

    public void setTypeDemandeId(Long typeDemandeId) {
        this.typeDemandeId = typeDemandeId;
    }
}
