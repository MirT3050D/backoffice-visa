package com.example.visa.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "demande_visa")
public class DemandeVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200, name = "date_demande")
    private LocalDate dateDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_demande_visa", nullable = false)
    private TypeDemandeVisa typeDemandeVisa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_visa", nullable = false)
    private TypeVisa typeVisa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_passeport", nullable = false)
    private Passeport passeport;

    @Column(name = "est_verrouille", nullable = false)
    private boolean estVerrouille;

    public Long getId() {
        return id;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public TypeDemandeVisa getTypeDemandeVisa() {
        return typeDemandeVisa;
    }

    public TypeVisa getTypeVisa() {
        return typeVisa;
    }

    public Passeport getPasseport() {
        return passeport;
    }

    public boolean isEstVerrouille() {
        return estVerrouille;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public void setTypeDemandeVisa(TypeDemandeVisa typeDemandeVisa) {
        this.typeDemandeVisa = typeDemandeVisa;
    }

    public void setTypeVisa(TypeVisa typeVisa) {
        this.typeVisa = typeVisa;
    }

    public void setPasseport(Passeport passeport) {
        this.passeport = passeport;
    }

    public void setEstVerrouille(boolean estVerrouille) {
        this.estVerrouille = estVerrouille;
    }
}
