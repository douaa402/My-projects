package com.iset.gestion_stage.repositories;

import com.iset.gestion_stage.entities.Etudiant;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

	Optional<Etudiant> findByUtilisateurId(Long utilisateurId);


	
	

}
