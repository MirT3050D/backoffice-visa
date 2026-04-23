package com.example.visa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dossier")
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "est_coche")
    private boolean estCoche;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande_visa", nullable = false)
    private DemandeVisa demandeVisa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_champ_fournir_specifique", nullable = true)
    private ChampFournirSpecifique champFournirSpecifique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_champ_fournir_commune", nullable = true)
    private ChampFournirCommune champFournirCommune;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEstCoche() {
        return estCoche;
    }

    public void setEstCoche(boolean estCoche) {
        this.estCoche = estCoche;
    }

    public DemandeVisa getDemandeVisa() {
        return demandeVisa;
    }

    public void setDemandeVisa(DemandeVisa demandeVisa) {
        this.demandeVisa = demandeVisa;
    }

    public ChampFournirSpecifique getChampFournirSpecifique() {
        return champFournirSpecifique;
    }

    public void setChampFournirSpecifique(ChampFournirSpecifique champFournirSpecifique) {
        this.champFournirSpecifique = champFournirSpecifique;
    }

    public ChampFournirCommune getChampFournirCommune() {
        return champFournirCommune;
    }

    public void setChampFournirCommune(ChampFournirCommune champFournirCommune) {
        this.champFournirCommune = champFournirCommune;
    }
}
