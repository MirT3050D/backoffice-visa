package com.example.visa.model;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name = "statut_demande")
public class StatutDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private LocalDate date_statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande_visa", nullable = false)
    private DemandeVisa demande_visa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_statut_demande", nullable = false)
    private TypeStatutDemande type_statut_demande;
    
    public Long getId() {
        return id;
    }

    public LocalDate getDate_statut() {
        return date_statut;
    }

    public DemandeVisa getDemande_visa() {
        return demande_visa;
    }

    public TypeStatutDemande getType_statut_demande() {
        return type_statut_demande;
    }

    public void setDate_statut(LocalDate date_statut) {
        this.date_statut = date_statut;
    }

    public void setDemande_visa(DemandeVisa demande_visa) {
        this.demande_visa = demande_visa;
    }

    public void setType_statut_demande(TypeStatutDemande type_statut_demande) {
        this.type_statut_demande = type_statut_demande;
    }
}
