package com.iset.gestion_stage.services;

import com.iset.gestion_stage.entities.DemandeStage;
import com.iset.gestion_stage.repositories.DemandeStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeStageService {

	@Autowired
	private DemandeStageRepository demandeStageRepository;


    public List<DemandeStage> findByEntrepriseId(Long entrepriseId) {
        return demandeStageRepository.findByOffre_Entreprise_Id(entrepriseId);
    }

    // Ajoute si nécessaire :
    public DemandeStage getById(Long id) {
        return demandeStageRepository.findById(id).orElse(null);
    }
}

