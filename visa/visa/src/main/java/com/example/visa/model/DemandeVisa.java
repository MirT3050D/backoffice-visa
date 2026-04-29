package com.example.visa.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "demande_visa")
public class DemandeVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private LocalDate date_demande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_demande_visa", nullable = false)
    private TypeDemandeVisa type_demande_visa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_visa", nullable = false)
    private TypeVisa type_visa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_passeport", nullable = false)
    private Passeport passeport;

    @Column(name = "est_verrouille", nullable = false)
    private boolean estVerrouille;

    public Long getId() {
        return id;
    }

    public LocalDate getDate_demande() {
        return date_demande;
    }

    public TypeDemandeVisa getType_demande_visa() {
        return type_demande_visa;
    }

    public TypeVisa getType_visa() {
        return type_visa;
    }

    public Passeport getPasseport() {
        return passeport;
    }

    public boolean isEstVerrouille() {
        return estVerrouille;
    }

    public void setDate_demande(LocalDate date_demande) {
        this.date_demande = date_demande;
    }

    public void setType_demande_visa(TypeDemandeVisa type_demande_visa) {
        this.type_demande_visa = type_demande_visa;
    }

    public void setType_visa(TypeVisa type_visa) {
        this.type_visa = type_visa;
    }

    public void setPasseport(Passeport passeport) {
        this.passeport = passeport;
    }

    public void setEstVerrouille(boolean estVerrouille) {
        this.estVerrouille = estVerrouille;
    }
}
