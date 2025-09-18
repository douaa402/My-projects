package com.iset.gestion_stage.repositories;

import com.iset.gestion_stage.entities.Candidature;
import com.iset.gestion_stage.entities.DemandeStage;
import com.iset.gestion_stage.entities.Entreprise;
import com.iset.gestion_stage.entities.OffreStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffreStageRepository extends JpaRepository<OffreStage, Long> {
    List<OffreStage> findByEntreprise_Id(Long entrepriseId);
    List<OffreStage> findByEntreprise(Entreprise entreprise);
	OffreStage save(Candidature candidature);
	OffreStage save(DemandeStage demande);

}
