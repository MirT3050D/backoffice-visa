package com.example.visa.repository;

import com.example.visa.model.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StatutDemandeRepository extends JpaRepository<StatutDemande, Long> {
	@Query("select s from StatutDemande s where s.demande_visa.id = :demandeVisaId order by s.date_statut desc, s.id desc")
	List<StatutDemande> findLatestByDemandeVisaId(@Param("demandeVisaId") Long demandeVisaId, Pageable pageable);

	@Query("select s from StatutDemande s where s.demande_visa.id = :demandeVisaId order by s.date_statut desc, s.id desc")
	List<StatutDemande> findByDemandeVisaIdOrderByDateStatutDesc(@Param("demandeVisaId") Long demandeVisaId);

	@Modifying
	@Query("delete from StatutDemande s where s.demande_visa.id = :demandeVisaId")
	void deleteByDemandeVisaId(@Param("demandeVisaId") Long demandeVisaId);
}
