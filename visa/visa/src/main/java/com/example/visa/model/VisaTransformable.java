package com.example.visa.model;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name = "visa_transformable")
public class VisaTransformable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero_passport;

    @Column(nullable = false, unique = false)
    private LocalDate date_expiration;

    @Column(nullable = false, unique = false)
    private LocalDate date_delivrance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etat_civil", nullable = false)
    private EtatCivil etatCivil;


    
}
