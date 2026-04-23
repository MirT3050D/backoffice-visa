package com.example.visa.model;


import jakarta.persistence.*;
@Entity
@Table(name = "type_statut_demande")
public class TypeStatutDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;

    @Column(nullable = false, unique = true)
    private int ordre_statut;
    
}
