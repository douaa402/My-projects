package com.iset.gestion_stage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iset.gestion_stage.entities.Entreprise;
import com.iset.gestion_stage.repositories.EntrepriseRepository;

@Service
public class EntrepriseService {
    @Autowired
    private EntrepriseRepository entrepriseRepository;

    public Entreprise findByEmail(String email) {
        return entrepriseRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Entreprise non trouvée"));
    }
}
