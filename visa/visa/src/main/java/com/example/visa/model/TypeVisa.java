package com.example.visa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type_visa")
public class TypeVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String label;

}
