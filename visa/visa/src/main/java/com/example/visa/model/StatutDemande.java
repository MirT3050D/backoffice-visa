package com.example.visa.model;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name = "statut_demande")
public class StatutDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false, name = "date_statut")
    private LocalDate dateStatut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande_visa", nullable = false)
    private DemandeVisa demandeVisa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_statut_demande", nullable = false)
    private TypeStatutDemande typeStatutDemande;
    
    public Long getId() {
        return id;
    }

    public LocalDate getDateStatut() {
        return dateStatut;
    }

    public DemandeVisa getDemandeVisa() {
        return demandeVisa;
    }

    public TypeStatutDemande getTypeStatutDemande() {
        return typeStatutDemande;
    }

    public void setDateStatut(LocalDate dateStatut) {
        this.dateStatut = dateStatut;
    }

    public void setDemandeVisa(DemandeVisa demandeVisa) {
        this.demandeVisa = demandeVisa;
    }

    public void setTypeStatutDemande(TypeStatutDemande typeStatutDemande) {
        this.typeStatutDemande = typeStatutDemande;
    }
}
