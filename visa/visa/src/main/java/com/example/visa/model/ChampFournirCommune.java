package com.example.visa.model;

import jakarta.persistence.*;
@Entity
@Table(name = "champ_fournir_commune")
public class ChampFournirCommune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;

    @Column(nullable = false, unique = false)
    private String type_donne;
    
}
