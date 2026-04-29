package com.example.visa.repository;

import com.example.visa.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
	List<Dossier> findByDemandeVisaIdOrderByIdAsc(Long demandeVisaId);

	Optional<Dossier> findByIdAndDemandeVisaId(Long id, Long demandeVisaId);

	Optional<Dossier> findByDemandeVisaIdAndChampFournirCommuneId(Long demandeVisaId, Long champFournirCommuneId);

	Optional<Dossier> findByDemandeVisaIdAndChampFournirSpecifiqueId(Long demandeVisaId, Long champFournirSpecifiqueId);

	void deleteByDemandeVisaId(Long demandeVisaId);
}
