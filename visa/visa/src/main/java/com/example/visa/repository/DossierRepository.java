package com.example.visa.repository;

import com.example.visa.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
}
