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
    private String num_passeport;

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

    public String getNum_passeport() {
        return num_passeport;
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

    public void setNum_passeport(String num_passeport) {
        this.num_passeport = num_passeport;
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
