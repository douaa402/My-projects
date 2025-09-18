package com.iset.gestion_stage.repositories;

import com.iset.gestion_stage.entities.Entreprise;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    // Méthode pour trouver une entreprise par son email
    Optional<Entreprise> findByEmail(String email);

}
