package com.iset.gestion_stage.services;

import com.iset.gestion_stage.entities.Utilisateur;
import com.iset.gestion_stage.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Méthode pour enregistrer un utilisateur
    public Utilisateur register(Utilisateur utilisateur) {
        // Vérifier si l'email est déjà utilisé
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        // Encoder le mot de passe avant de sauvegarder
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        // Enregistrer l'utilisateur
        return utilisateurRepository.save(utilisateur);
    }

    // Méthode pour authentifier un utilisateur
    public Utilisateur login(String email, String password) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();

            // Vérifier que le mot de passe correspond
            if (passwordEncoder.matches(password, utilisateur.getMotDePasse())) {
                return utilisateur;
            } else {
                throw new RuntimeException("Mot de passe incorrect !");
            }
        } else {
            throw new RuntimeException("Utilisateur non trouvé !");
        }
    }
}

