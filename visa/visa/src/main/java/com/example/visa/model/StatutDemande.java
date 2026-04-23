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
    private LocalDate date_changement_statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande_visa", nullable = false)
    private DemandeVisa demande_visa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_statut_demande", nullable = false)
    private TypeStatutDemande type_statut_demande;


    
}
