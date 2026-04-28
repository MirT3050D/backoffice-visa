package com.example.visa.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visa")
public class Visa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_visa", nullable = false, unique = true)
    private String numVisa;

    @Column(name = "data_entre", nullable = false)
    private LocalDate dataEntre;

    @Column(name = "date_expiration", nullable = false)
    private LocalDate dateExpiration;

    @Column(name = "date_delivrance", nullable = false)
    private LocalDate dateDelivrance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande_visa", nullable = false)
    private DemandeVisa demandeVisa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ville", nullable = false)
    private Ville ville;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etat_civil", nullable = false)
    private EtatCivil etatCivil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_visa", nullable = false)
    private TypeVisa typeVisa;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumVisa() { return numVisa; }
    public void setNumVisa(String numVisa) { this.numVisa = numVisa; }
    
    public LocalDate getDataEntre() { return dataEntre; }
    public void setDataEntre(LocalDate dataEntre) { this.dataEntre = dataEntre; }
    
    public LocalDate getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDate dateExpiration) { this.dateExpiration = dateExpiration; }
    
    public LocalDate getDateDelivrance() { return dateDelivrance; }
    public void setDateDelivrance(LocalDate dateDelivrance) { this.dateDelivrance = dateDelivrance; }
    
    public DemandeVisa getDemandeVisa() { return demandeVisa; }
    public void setDemandeVisa(DemandeVisa demandeVisa) { this.demandeVisa = demandeVisa; }
    
    public Ville getVille() { return ville; }
    public void setVille(Ville ville) { this.ville = ville; }
    
    public EtatCivil getEtatCivil() { return etatCivil; }
    public void setEtatCivil(EtatCivil etatCivil) { this.etatCivil = etatCivil; }
    
    public TypeVisa getTypeVisa() { return typeVisa; }
    public void setTypeVisa(TypeVisa typeVisa) { this.typeVisa = typeVisa; }
}