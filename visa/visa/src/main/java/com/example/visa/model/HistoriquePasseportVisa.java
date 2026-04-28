package com.example.visa.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique_passeport_visa")
public class HistoriquePasseportVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_historique", nullable = false)
    private LocalDateTime dateHistorique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_passeport", nullable = false)
    private Passeport passeport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visa", nullable = false)
    private Visa visa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateHistorique() { return dateHistorique; }
    public void setDateHistorique(LocalDateTime dateHistorique) { this.dateHistorique = dateHistorique; }

    public Passeport getPasseport() { return passeport; }
    public void setPasseport(Passeport passeport) { this.passeport = passeport; }

    public Visa getVisa() { return visa; }
    public void setVisa(Visa visa) { this.visa = visa; }
}