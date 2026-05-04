package com.example.visa.repository;

import com.example.visa.model.DemandeVisa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

public interface DemandeVisaRepository extends JpaRepository<DemandeVisa, Long> {
    @NativeQuery("SELECT * FROM  demande_visa WHERE demande_visa.id LIKE :ref_demande")
    List<DemandeVisa> findByDemandeId(String ref__demande);
    @NativeQuery("SELECT * FROM  demande_visa JOIN passeport ON demande_visa.id_passeport = passeport.id WHERE passeport.id LIKE :ref_passeport")
    List<DemandeVisa> findByPasseportId(String ref_passeport);

}
