package com.iset.gestion_stage.repositories;

import com.iset.gestion_stage.entities.DemandeStage;
import com.iset.gestion_stage.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeStageRepository extends JpaRepository<DemandeStage, Long> {

	List<DemandeStage> findByEtudiant(Etudiant etudiant);
	List<DemandeStage> findByEtudiant_Id(Long etudiantId);
	List<DemandeStage> findByOffre_Id(Long offreId);
	List<DemandeStage> findByOffre_Entreprise_Id(Long entrepriseId); // <-- à ajouter



}
