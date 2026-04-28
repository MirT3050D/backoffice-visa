package com.example.visa.repository;

import com.example.visa.model.TypeStatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TypeStatutDemandeRepository extends JpaRepository<TypeStatutDemande, Long> {
    Optional<TypeStatutDemande> findByRang(int rang);
}
