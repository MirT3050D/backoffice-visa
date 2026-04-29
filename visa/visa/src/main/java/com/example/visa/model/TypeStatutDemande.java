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
    private int rang;

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public int getRang() {
        return rang;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
    
}
