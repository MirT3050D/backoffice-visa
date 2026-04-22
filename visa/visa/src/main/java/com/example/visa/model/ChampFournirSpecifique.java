package com.example.visa.model;

import jakarta.persistence.*;
@Entity
@Table(name = "champ_fournir_specifique")
public class ChampFournirSpecifique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;

    @Column(nullable = false, unique = false)
    private String type_donnee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_visa", nullable = false)
    private TypeVisa typeVisa;

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
        return type_donnee;
    }

    public void setTypeDonnee(String type_donnee) {
        this.type_donnee = type_donnee;
    }

    public TypeVisa getTypeVisa() {
        return typeVisa;
    }

    public void setTypeVisa(TypeVisa typeVisa) {
        this.typeVisa = typeVisa;
    }
}
