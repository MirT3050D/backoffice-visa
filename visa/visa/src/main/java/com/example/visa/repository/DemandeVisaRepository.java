package com.example.visa.repository;

import com.example.visa.model.DemandeVisa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeVisaRepository extends JpaRepository<DemandeVisa, Long> {
}
