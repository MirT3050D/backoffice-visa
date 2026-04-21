package com.example.visa.model;

import jakarta.persistence.*;
@Entity
@Table(name = "dossier")
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private boolean est_coche;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande_visa", nullable = false)
    private DemandeVisa emande_visa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_champ_fournir_specifique", nullable = false)
    private TypeDemandeVisa type_demande_visa;
}
