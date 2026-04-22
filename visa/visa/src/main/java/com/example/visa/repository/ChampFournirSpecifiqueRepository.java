package com.example.visa.repository;

import com.example.visa.model.ChampFournirSpecifique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChampFournirSpecifiqueRepository extends JpaRepository<ChampFournirSpecifique, Long> {
	List<ChampFournirSpecifique> findByTypeVisaId(Long typeVisaId);
}
