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

    @Column(nullable = false, unique = false, name = "type_donnee")
    private String typeDonnee;

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTypeDonnee() {
        return typeDonnee;
    }

    public void setTypeDonnee(String typeDonnee) {
        this.typeDonnee = typeDonnee;
    }
}
