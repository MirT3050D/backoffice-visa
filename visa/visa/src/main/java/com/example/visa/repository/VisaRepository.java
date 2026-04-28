package com.example.visa.repository;

import com.example.visa.model.Visa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisaRepository extends JpaRepository<Visa, Long> {}