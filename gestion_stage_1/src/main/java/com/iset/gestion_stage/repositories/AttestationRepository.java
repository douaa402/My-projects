package com.iset.gestion_stage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iset.gestion_stage.entities.Attestation;
import com.iset.gestion_stage.entities.Etudiant;

import java.util.List;

@Repository
public interface AttestationRepository extends JpaRepository<Attestation, Long> {
    List<Attestation> findByEtudiant(Etudiant etudiant);
}
