package com.example.visa.repository;

import com.example.visa.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
	List<Dossier> findByDemandeVisaIdOrderByIdAsc(Long demandeVisaId);

	void deleteByDemandeVisaId(Long demandeVisaId);
}
