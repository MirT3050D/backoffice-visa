package com.example.visa.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "statut_visa")
public class StatutVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_statut", nullable = false)
    private LocalDateTime dateStatut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_statut_visa", nullable = false)
    private TypeStatutVisa typeStatutVisa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visa", nullable = false)
    private Visa visa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateStatut() { return dateStatut; }
    public void setDateStatut(LocalDateTime dateStatut) { this.dateStatut = dateStatut; }

    public TypeStatutVisa getTypeStatutVisa() { return typeStatutVisa; }
    public void setTypeStatutVisa(TypeStatutVisa typeStatutVisa) { this.typeStatutVisa = typeStatutVisa; }

    public Visa getVisa() { return visa; }
    public void setVisa(Visa visa) { this.visa = visa; }
}