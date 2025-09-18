package com.iset.gestion_stage.repositories;

import com.iset.gestion_stage.entities.DemandeStage;
import com.iset.gestion_stage.entities.DemandeStageEntreprise;
import com.iset.gestion_stage.entities.StatusCandidature;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeStageEntrepriseRepository extends JpaRepository<DemandeStageEntreprise, Long> {

	List<DemandeStageEntreprise> findByOffreStage_Entreprise_IdAndStatus(Long id, StatusCandidature acceptee);

	void save(DemandeStage demande);

	List<DemandeStageEntreprise> findByOffreStage_Entreprise_Id(Long id);
	List<DemandeStageEntreprise> findAll();
	List<DemandeStageEntreprise> findByStatus(StatusCandidature status);
		

}
