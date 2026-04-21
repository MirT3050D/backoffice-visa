package com.example.visa.model;

import jakarta.persistence.*;
@Entity
@Table(name = "situation_familiale")
public class SitutationFamiliale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;
    
}
