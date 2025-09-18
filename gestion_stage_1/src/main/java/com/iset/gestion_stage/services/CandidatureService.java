package com.iset.gestion_stage.services;

import com.iset.gestion_stage.entities.DemandeStageEntreprise;
import com.iset.gestion_stage.entities.StatusCandidature;
import com.iset.gestion_stage.repositories.DemandeStageEntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatureService {

    private final DemandeStageEntrepriseRepository repository;

    @Autowired
    public CandidatureService(DemandeStageEntrepriseRepository repository) {
        this.repository = repository;
    }

    /**
     * Récupère toutes les candidatures avec le statut ACCEPTÉE.
     */
    public List<DemandeStageEntreprise> getAcceptedCandidatures() {
        return repository.findByStatus(StatusCandidature.ACCEPTEE);
    }

    /**
     * Récupère une candidature par son ID.
     */
    public DemandeStageEntreprise getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable pour l'ID: " + id));
    }

    /**
     * Sauvegarde ou met à jour une candidature.
     */
    public void save(DemandeStageEntreprise demande) {
        repository.save(demande);
    }
}


