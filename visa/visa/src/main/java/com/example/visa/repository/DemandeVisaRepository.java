package com.example.visa.repository;

import com.example.visa.model.DemandeVisa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface DemandeVisaRepository extends JpaRepository<DemandeVisa, Long> {
    // @NativeQuery("SELECT * FROM demande_visa WHERE demande_visa.id = :ref_demande")
    // List<DemandeVisa> findByDemandeId(Long ref_demande);

        // @NativeQuery("SELECT dv.* FROM demande_visa dv JOIN passeport p ON dv.id_passeport = p.id WHERE p.num_passeport = :num_passeport")
        // List<DemandeVisa> findByPasseportNumero(String num_passeport);
    List<DemandeVisa> findByPasseport_NumPasseportContaining(String fragment);
    List<DemandeVisa> findByPasseport_NumPasseport(String numPasseport);
    List<DemandeVisa> findByPasseport_Id(Long idPasseport);
    List<DemandeVisa> findByPasseport_EtatCivil_IdOrderByDateDemandeAsc(Long idEtatCivil);

}
