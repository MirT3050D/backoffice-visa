package com.example.visa.repository;

import com.example.visa.model.TypeStatutVisa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TypeStatutVisaRepository extends JpaRepository<TypeStatutVisa, Long> {
    Optional<TypeStatutVisa> findByRang(double rang);
}
