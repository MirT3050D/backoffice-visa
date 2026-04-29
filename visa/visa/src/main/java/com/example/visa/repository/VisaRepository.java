package com.example.visa.repository;

import com.example.visa.model.Visa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisaRepository extends JpaRepository<Visa, Long> {
	Optional<Visa> findFirstByDemandeVisaId(Long demandeVisaId);
}