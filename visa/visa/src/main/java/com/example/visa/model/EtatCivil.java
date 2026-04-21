package com.example.visa.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "etat_civil")
public class EtatCivil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 200)
    private String nom;

    @Column(nullable = true, length = 100)
    private String prenom;

    @Column(nullable = true, length = 100)
    private String nom_jeune_fille;

    @Column(unique = true, nullable = true, length = 150)
    private String email;

    @Column(unique = true, nullable = false, length = 50)
    private String numero_telephone;

    @Column(unique = true, nullable = false, length = 150)
    private LocalDate date_naissance;   

    @Column(unique = true, nullable = false, length = 150)
    private String lieu_naissance;

    @Column(unique = true, nullable = false, length = 150)
    private String adresse_mada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nationalite", nullable = false)
    private Nationalite nationalite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_situation_familiale", nullable = false)
    private SitutationFamiliale situation_familiale;



}
