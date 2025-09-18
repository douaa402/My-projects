package com.iset.gestion_stage.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iset.gestion_stage.entities.DemandeStage;
import com.iset.gestion_stage.entities.DemandeStageEntreprise;
import com.iset.gestion_stage.entities.Etudiant;
import com.iset.gestion_stage.entities.OffreStage;
import com.iset.gestion_stage.entities.RapportFinal;
import com.iset.gestion_stage.entities.StatusCandidature;
import com.iset.gestion_stage.entities.Utilisateur;
import com.iset.gestion_stage.repositories.DemandeStageEntrepriseRepository;
import com.iset.gestion_stage.repositories.DemandeStageRepository;
import com.iset.gestion_stage.repositories.EtudiantRepository;
import com.iset.gestion_stage.repositories.OffreStageRepository;
import com.iset.gestion_stage.repositories.RapportFinalRepository;
import com.iset.gestion_stage.repositories.UtilisateurRepository;
import com.iset.gestion_stage.services.OffreStageService;

@Controller
public class EtudiantController {
	@Autowired
	private DemandeStageEntrepriseRepository demandeStageEntrepriseRepository;

    @Autowired private EtudiantRepository etudiantRepository;
    @Autowired private OffreStageService offreStageService;
    @Autowired private OffreStageRepository offreStageRepository;
    @Autowired private DemandeStageRepository demandeStageRepository;
    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private RapportFinalRepository rapportFinalRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/etudiant/offres")
    public String afficherOffres(Model model) {
        model.addAttribute("offres", offreStageService.getAllOffres());
        return "etudiant/offres";
    }

    


    @GetMapping("/etudiant/etat_demande")
    public String etatDemande(Principal principal, Model model) {
        try {
            String email = principal.getName();
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            Etudiant etudiant = etudiantRepository.findByUtilisateurId(utilisateur.getId())
                    .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

            List<DemandeStage> demandes = demandeStageRepository.findByEtudiant_Id(etudiant.getId());
            model.addAttribute("demandes", demandes);
            return "etudiant/etat_demande";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue. Veuillez réessayer.");
            return "etudiant/etat_demande";
        }
    }



    @GetMapping("/etudiant/profile")
    public String afficherProfil(Model model, Principal principal) {
        String email = principal.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        model.addAttribute("utilisateur", utilisateur);
        return "etudiant/profile";
    }

    @PostMapping("/etudiant/profile")
    public String modifierProfil(@RequestParam String nom,
                                 @RequestParam String prenom,
                                 @RequestParam String email,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        String oldEmail = principal.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(oldEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);

        utilisateurRepository.save(utilisateur);
        redirectAttributes.addFlashAttribute("successMessage", "Profil mis à jour !");
        return "redirect:/etudiant/profile";
    }

    @PostMapping("/etudiant/change-password")
    public String changerMotDePasse(@RequestParam String oldPassword,
                                    @RequestParam String newPassword,
                                    Principal principal,
                                    RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(oldPassword, utilisateur.getMotDePasse())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ancien mot de passe incorrect !");
            return "redirect:/etudiant/profile";
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(utilisateur);
        redirectAttributes.addFlashAttribute("successMessage", "Mot de passe changé avec succès !");
        return "redirect:/etudiant/profile";
    }

    @GetMapping("/etudiant/postuler/{id}")
    public String afficherFormulairePostuler(@PathVariable Long id, Model model) {
        OffreStage offre = offreStageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
        model.addAttribute("offre", offre);
        return "etudiant/postuler";
    }

    @PostMapping("/etudiant/postuler")
    public String postulerStage(@RequestParam("offreId") Long offreId,
                                @RequestParam("cvFileName") MultipartFile cvFileName,
                                @RequestParam("message") String message,
                                Principal principal,
                                Model model) {
        try {
            // Récupérer l'utilisateur actuellement connecté
            String email = principal.getName();
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Récupérer l'étudiant à partir de l'utilisateur
            Etudiant etudiant = etudiantRepository.findByUtilisateurId(utilisateur.getId())
                    .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

            // Récupérer l'offre de stage
            OffreStage offre = offreStageRepository.findById(offreId)
                    .orElseThrow(() -> new RuntimeException("Offre de stage non trouvée"));

            // Créer une nouvelle demande de stage
            DemandeStage demande = new DemandeStage();
            demande.setEtudiant(etudiant);
            demande.setOffre(offre);
            demande.setStatut("En attente");
            demande.setMessage(message);
            demande.setDateDemande(LocalDateTime.now());

            // Gestion du fichier CV
            if (!cvFileName.isEmpty()) {
                String nomOriginal = cvFileName.getOriginalFilename();
                String nomUnique = UUID.randomUUID() + "_" + nomOriginal;
                Path dossierCvs = Paths.get("src/main/resources/static/uploads/cvs");

                if (Files.notExists(dossierCvs)) {
                    Files.createDirectories(dossierCvs);
                }

                Path destination = dossierCvs.resolve(nomUnique);
                Files.copy(cvFileName.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
                demande.setCvFileName(nomUnique);
            }

            // Enregistrer la demande dans la table demande_stage
            demandeStageRepository.save(demande);

            // Créer la demande pour l’entreprise
            DemandeStageEntreprise demandeStageEntreprise = new DemandeStageEntreprise();
            demandeStageEntreprise.setEtudiant(etudiant);
            demandeStageEntreprise.setOffreStage(offre);
            demandeStageEntreprise.setStatus(StatusCandidature.EN_ATTENTE);
            demandeStageEntreprise.setDateDemande(LocalDate.now());

            // Ajouter nom et prénom
            Utilisateur utilisateurEtudiant = utilisateurRepository.findById(etudiant.getUtilisateur().getId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur de l'étudiant introuvable"));

            demandeStageEntreprise.setNomEtudiant(utilisateurEtudiant.getNom());
            demandeStageEntreprise.setPrenomEtudiant(utilisateurEtudiant.getPrenom());

            // ✅ Copier le CV et le message vers la table entreprise
            demandeStageEntreprise.setCv(demande.getCvFileName());
            demandeStageEntreprise.setMessage(message);

            // Enregistrer dans la table demande_stage_entreprise
            demandeStageEntrepriseRepository.save(demandeStageEntreprise);

            // Redirection
            return "redirect:/etudiant/etat_demande";

        } catch (Exception e) {
            e.printStackTrace();
            OffreStage offre = offreStageRepository.findById(offreId).orElse(null);
            model.addAttribute("offre", offre);
            model.addAttribute("errorMessage", "Une erreur est survenue lors de l'envoi de la candidature.");
            return "etudiant/postuler";
        }
    }






    @PostMapping("/etudiant/documents")
    public String uploadRapport(@RequestParam("fichier") MultipartFile fichier, Principal principal) throws IOException {
        String email = principal.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Etudiant etudiant = etudiantRepository.findByUtilisateurId(utilisateur.getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        // Nom de fichier unique
        String originalFileName = fichier.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

        Path uploadPath = Paths.get("C:/uploads/rapports");
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(uniqueFileName);
        fichier.transferTo(filePath.toFile());

        // Supprimer l'ancien rapport s’il existe
        rapportFinalRepository.findByEtudiant(etudiant).ifPresent(rapportFinalRepository::delete);

        // Enregistrer le nouveau
        RapportFinal nouveauRapport = new RapportFinal(uniqueFileName, etudiant);
        rapportFinalRepository.save(nouveauRapport);

        return "redirect:/etudiant/documents?success=true";
    }


    @GetMapping("/etudiant/documents")
    public String afficherFormulaireRapport(Model model, Principal principal,
                                             @RequestParam(value = "success", required = false) String success) {
        String email = principal.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Etudiant etudiant = etudiantRepository.findByUtilisateurId(utilisateur.getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        // Ajouter le rapport si déjà déposé
        RapportFinal rapport = rapportFinalRepository.findByEtudiant(etudiant).orElse(null);
        model.addAttribute("rapport", rapport);

        // Afficher le message de succès s'il est présent
        if ("true".equals(success)) {
            model.addAttribute("successMessage", "Le rapport a été soumis avec succès.");
        }

        return "etudiant/documents";
    }
}