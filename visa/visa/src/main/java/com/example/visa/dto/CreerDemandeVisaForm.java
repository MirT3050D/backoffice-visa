package com.example.visa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CreerDemandeVisaForm {
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateDemande;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    private String nomJeuneFille;

    private String email;

    @NotBlank
    private String numeroTelephone;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateNaissance;

    @NotBlank
    private String lieuNaissance;

    @NotBlank
    private String adresseMada;

    @NotNull
    private Long nationaliteId;

    @NotNull
    private Long situationFamilialeId;

    @NotBlank
    private String numeroPasseport;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateDelivrancePasseport;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateExpirationPasseport;

    @NotNull
    private Long typeDemandeId;

    @NotNull
    private Long typeVisaId;

    @NotBlank
    private String visaTranNumPasseport;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate visaTranDateDelivrance;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate visaTranDateExpiration;

    private java.util.List<Long> champsSpecifiquesCoches;

    public String getVisaTranNumPasseport() {
        return visaTranNumPasseport;
    }

    public void setVisaTranNumPasseport(String visaTranNumPasseport) {
        this.visaTranNumPasseport = visaTranNumPasseport;
    }

    public LocalDate getVisaTranDateDelivrance() {
        return visaTranDateDelivrance;
    }

    public void setVisaTranDateDelivrance(LocalDate visaTranDateDelivrance) {
        this.visaTranDateDelivrance = visaTranDateDelivrance;
    }

    public LocalDate getVisaTranDateExpiration() {
        return visaTranDateExpiration;
    }

    public void setVisaTranDateExpiration(LocalDate visaTranDateExpiration) {
        this.visaTranDateExpiration = visaTranDateExpiration;
    }

    public java.util.List<Long> getChampsSpecifiquesCoches() {
        return champsSpecifiquesCoches;
    }

    public void setChampsSpecifiquesCoches(java.util.List<Long> champsSpecifiquesCoches) {
        this.champsSpecifiquesCoches = champsSpecifiquesCoches;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

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

    public String getNomJeuneFille() {
        return nomJeuneFille;
    }

    public void setNomJeuneFille(String nomJeuneFille) {
        this.nomJeuneFille = nomJeuneFille;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getAdresseMada() {
        return adresseMada;
    }

    public void setAdresseMada(String adresseMada) {
        this.adresseMada = adresseMada;
    }

    public Long getNationaliteId() {
        return nationaliteId;
    }

    public void setNationaliteId(Long nationaliteId) {
        this.nationaliteId = nationaliteId;
    }

    public Long getSituationFamilialeId() {
        return situationFamilialeId;
    }

    public void setSituationFamilialeId(Long situationFamilialeId) {
        this.situationFamilialeId = situationFamilialeId;
    }

    public String getNumeroPasseport() {
        return numeroPasseport;
    }

    public void setNumeroPasseport(String numeroPasseport) {
        this.numeroPasseport = numeroPasseport;
    }

    public LocalDate getDateDelivrancePasseport() {
        return dateDelivrancePasseport;
    }

    public void setDateDelivrancePasseport(LocalDate dateDelivrancePasseport) {
        this.dateDelivrancePasseport = dateDelivrancePasseport;
    }

    public LocalDate getDateExpirationPasseport() {
        return dateExpirationPasseport;
    }

    public void setDateExpirationPasseport(LocalDate dateExpirationPasseport) {
        this.dateExpirationPasseport = dateExpirationPasseport;
    }

    public Long getTypeDemandeId() {
        return typeDemandeId;
    }

    public void setTypeDemandeId(Long typeDemandeId) {
        this.typeDemandeId = typeDemandeId;
    }

    public Long getTypeVisaId() {
        return typeVisaId;
    }

    public void setTypeVisaId(Long typeVisaId) {
        this.typeVisaId = typeVisaId;
    }
}
