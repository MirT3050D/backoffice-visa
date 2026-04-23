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

    @Column(nullable = true, length = 100)
    private String nom_jeune_fille;

    @Column(unique = true, nullable = true, length = 150)
    private String email;

    @Column(unique = true, nullable = false, length = 50)
    private String numero_telephone;

    @Column(unique = false, nullable = false, length = 150)
    private LocalDate date_naissance;   

    @Column(unique = false, nullable = false, length = 150)
    private String lieu_naissance;

    @Column(unique = false, nullable = false, length = 150)
    private String adresse_mada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nationalite", nullable = false)
    private Nationalite nationalite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_situation_familiale", nullable = false)
    private SitutationFamiliale situation_familiale;

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom_jeune_fille() {
        return nom_jeune_fille;
    }

    public String getEmail() {
        return email;
    }

    public String getNumero_telephone() {
        return numero_telephone;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public String getLieu_naissance() {
        return lieu_naissance;
    }

    public String getAdresse_mada() {
        return adresse_mada;
    }

    public Nationalite getNationalite() {
        return nationalite;
    }

    public SitutationFamiliale getSituation_familiale() {
        return situation_familiale;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom_jeune_fille(String nom_jeune_fille) {
        this.nom_jeune_fille = nom_jeune_fille;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumero_telephone(String numero_telephone) {
        this.numero_telephone = numero_telephone;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public void setAdresse_mada(String adresse_mada) {
        this.adresse_mada = adresse_mada;
    }

    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    public void setSituation_familiale(SitutationFamiliale situation_familiale) {
        this.situation_familiale = situation_familiale;
    }


}
