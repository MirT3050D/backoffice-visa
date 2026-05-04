package com.example.visa.repository;

import com.example.visa.model.DemandeVisa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface DemandeVisaRepository extends JpaRepository<DemandeVisa, Long> {
    @NativeQuery("SELECT * FROM demande_visa WHERE demande_visa.id = :ref_demande")
    List<DemandeVisa> findByDemandeId(Long ref_demande);

    @NativeQuery("SELECT * FROM demande_visa JOIN passeport ON demande_visa.id_passeport = passeport.id WHERE passeport.id = :ref_passeport")
    List<DemandeVisa> findByPasseportId(Long ref_passeport);

    @NativeQuery("SELECT dv.* FROM demande_visa dv JOIN passeport p ON dv.id_passeport = p.id WHERE p.num_passeport = :num_passeport")
    List<DemandeVisa> findByPasseportNumero(String num_passeport);

    @NativeQuery("SELECT dv.* FROM demande_visa dv JOIN passeport p ON dv.id_passeport = p.id "
            + "WHERE CAST(dv.id AS text) ILIKE CONCAT('%', :reference, '%') "
            + "OR p.num_passeport ILIKE CONCAT('%', :reference, '%')")
    List<DemandeVisa> findByReferenceLike(String reference);

}
