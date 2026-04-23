package com.example.visa.repository;

import com.example.visa.model.VisaTransformable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Long> {
	Optional<VisaTransformable> findFirstByEtatCivilId(Long etatCivilId);
}
