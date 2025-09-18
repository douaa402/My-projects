package com.iset.gestion_stage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iset.gestion_stage.entities.Etudiant;
import com.iset.gestion_stage.entities.RapportFinal;

public interface RapportFinalRepository extends JpaRepository<RapportFinal, Long> {
    Optional<RapportFinal> findByEtudiant(Etudiant etudiant);
}

