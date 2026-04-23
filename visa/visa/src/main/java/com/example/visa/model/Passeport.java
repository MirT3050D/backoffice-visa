package com.example.visa.model;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name = "passeport")
public class Passeport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero_passport;

    @Column(nullable = false, unique = false)
    private LocalDate date_expiration;

    @Column(nullable = false, unique = false)
    private LocalDate date_delivrance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etat_civil", nullable = false)
    private EtatCivil etatCivil;

    public Long getId() {
        return id;
    }

    public String getNumero_passport() {
        return numero_passport;
    }

    public LocalDate getDate_expiration() {
        return date_expiration;
    }

    public LocalDate getDate_delivrance() {
        return date_delivrance;
    }

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public void setNumero_passport(String numero_passport) {
        this.numero_passport = numero_passport;
    }

    public void setDate_expiration(LocalDate date_expiration) {
        this.date_expiration = date_expiration;
    }

    public void setDate_delivrance(LocalDate date_delivrance) {
        this.date_delivrance = date_delivrance;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }


    
}
