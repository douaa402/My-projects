package com.iset.gestion_stage.controller;

import com.iset.gestion_stage.entities.*;
import com.iset.gestion_stage.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String role,
            @RequestParam(required = false) String cin,
            @RequestParam(required = false) String niveau,
            @RequestParam(required = false) String specialite,
            @RequestParam(required = false) String departement,
            @RequestParam(required = false) String nomEntreprise,
            @RequestParam(required = false) String adresse,
            @RequestParam(required = false) String telephone
    ) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/register?error=passwordsDoNotMatch";
        }

        try {
            Role userRole = Role.valueOf(role.toUpperCase());

            Utilisateur utilisateur;

            switch (userRole) {
                case ETUDIANT:
                    Etudiant etudiant = new Etudiant();
                    etudiant.setCin(cin);
                    etudiant.setNiveau(niveau);
                    etudiant.setSpecialite(specialite);
                    utilisateur = etudiant;
                    break;
                case ENCADRANT:
                    Encadrant encadrant = new Encadrant();
                    encadrant.setDepartement(departement);
                    utilisateur = encadrant;
                    break;
                case ENTREPRISE:
                    Entreprise entreprise = new Entreprise();
                    entreprise.setNomEntreprise(nomEntreprise);
                    entreprise.setAdresse(adresse);
                    entreprise.setTelephone(telephone);
                    utilisateur = entreprise;
                    break;
                default:
                    return "redirect:/register?error=invalidRole";
            }

            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setMotDePasse(password);
            utilisateur.setRole(userRole);

            authService.register(utilisateur);
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            return "redirect:/register?error=invalidRole";
        } catch (RuntimeException e) {
            return "redirect:/register?error=" + e.getMessage();
        }
    }
}
