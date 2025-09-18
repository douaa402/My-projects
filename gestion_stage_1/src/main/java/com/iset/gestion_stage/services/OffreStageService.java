package com.iset.gestion_stage.services;

import com.iset.gestion_stage.entities.Candidature;
import com.iset.gestion_stage.entities.Entreprise;
import com.iset.gestion_stage.entities.OffreStage;
import com.iset.gestion_stage.repositories.OffreStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffreStageService {

    @Autowired
    private OffreStageRepository offreStageRepository;

    // ✅ Method to get all offers
    public List<OffreStage> getAllOffres() {
        return offreStageRepository.findAll();
    }

    // ✅ Method to get offers by entreprise
    public List<OffreStage> findByEntreprise(Entreprise entreprise) {
        return offreStageRepository.findByEntreprise(entreprise);
    }

    // ✅ Method to get an offer by ID
    public Optional<OffreStage> findById(Long id) {
        return offreStageRepository.findById(id);
    }

    // ✅ Method to save or update an offer
    public OffreStage save(OffreStage offre) {
        return offreStageRepository.save(offre);
    }

    // ✅ Method to delete an offer
    public void delete(OffreStage offreStage) {
        offreStageRepository.delete(offreStage);
    }

	public void save(Candidature candidature) {
		// TODO Auto-generated method stub
		
	}

	
}
