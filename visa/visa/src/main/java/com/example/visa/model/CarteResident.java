package com.example.visa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "carte_resident")
public class CarteResident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visa", nullable = false)
    private Visa visa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNum() { return num; }
    public void setNum(String num) { this.num = num; }

    public Visa getVisa() { return visa; }
    public void setVisa(Visa visa) { this.visa = visa; }
}