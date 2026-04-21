package com.example.visa.model;


import jakarta.persistence.*;
@Entity
@Table(name = "type_demande_visa")
public class TypeDemandeVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;





    
}
