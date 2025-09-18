package com.iset.gestion_stage.services;

import com.iset.gestion_stage.entities.*;
import com.iset.gestion_stage.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeStageEntrepriseService {

	 @Autowired
	    private DemandeStageEntrepriseRepository demandeStageEntrepriseRepository;

	    // Méthode pour récupérer toutes les demandes de stage
	    public List<DemandeStageEntreprise> getAllDemandes() {
	        return demandeStageEntrepriseRepository.findAll();
	    }

	    // Méthode pour mettre à jour le statut d'une demande
	    public void updateStatus(Long id, StatusCandidature status) {
	        DemandeStageEntreprise demande = demandeStageEntrepriseRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));
	        demande.setStatus(status);
	        demandeStageEntrepriseRepository.save(demande);
	    }
}
