package com.example.visa.repository;

import com.example.visa.model.HistoriquePasseportVisa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoriquePasseportVisaRepository extends JpaRepository<HistoriquePasseportVisa, Long> {
	@Query("select h from HistoriquePasseportVisa h where h.passeport.num_passeport = :numPasseport order by h.dateHistorique desc")
	List<HistoriquePasseportVisa> findLatestByPasseportNumero(@Param("numPasseport") String numPasseport, Pageable pageable);
}