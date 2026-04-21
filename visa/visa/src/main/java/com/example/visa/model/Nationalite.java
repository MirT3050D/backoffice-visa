package com.example.visa.model;

import jakarta.persistence.*;
@Entity
@Table(name = "nationalite")
public class Nationalite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;
    
}
