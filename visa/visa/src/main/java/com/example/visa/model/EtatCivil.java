package com.example.visa.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "etat_civil")
public class EtatCivil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 200)
    private String nom;

    @Column(nullable = true, length = 100)
    private String prenom;

    @Column(nullable = true, length = 100, name = "nom_jeune_fille")
    private String nomJeuneFille;

    @Column(unique = true, nullable = true, length = 150)
    private String email;

    @Column(unique = true, nullable = false, length = 50, name = "num_tel")
    private String numTel;

    @Column(unique = false, nullable = false, length = 150, name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(unique = false, nullable = false, length = 150, name = "lieu_naissance")
    private String lieuNaissance;

    @Column(unique = false, nullable = false, length = 150, name = "adresse_mada")
    private String adresseMada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nationalite", nullable = false)
    private Nationalite nationalite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_situation_familiale", nullable = false)
    private SitutationFamiliale situationFamiliale;

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNomJeuneFille() {
        return nomJeuneFille;
    }

    public String getEmail() {
        return email;
    }

    public String getNumTel() {
        return numTel;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public String getAdresseMada() {
        return adresseMada;
    }

    public Nationalite getNationalite() {
        return nationalite;
    }

    public SitutationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNomJeuneFille(String nomJeuneFille) {
        this.nomJeuneFille = nomJeuneFille;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public void setAdresseMada(String adresseMada) {
        this.adresseMada = adresseMada;
    }

    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    public void setSituationFamiliale(SitutationFamiliale situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }


}
