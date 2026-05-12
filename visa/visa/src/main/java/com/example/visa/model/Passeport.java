package com.example.visa.model;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name = "passeport")
public class Passeport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "num_passeport")
    private String numPasseport;

    @Column(nullable = false, unique = false, name = "date_expiration")
    private LocalDate dateExpiration;

    @Column(nullable = false, unique = false, name = "date_delivrance")
    private LocalDate dateDelivrance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etat_civil", nullable = false)
    private EtatCivil etatCivil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pays", nullable = true)
    private Pays pays;

    public Long getId() {
        return id;
    }

    public String getNumPasseport() {
        return numPasseport;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public LocalDate getDateDelivrance() {
        return dateDelivrance;
    }

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public void setNumPasseport(String numPasseport) {
        this.numPasseport = numPasseport;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public void setDateDelivrance(LocalDate dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }
    
}
