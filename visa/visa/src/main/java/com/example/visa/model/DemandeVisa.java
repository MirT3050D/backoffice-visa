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

}
