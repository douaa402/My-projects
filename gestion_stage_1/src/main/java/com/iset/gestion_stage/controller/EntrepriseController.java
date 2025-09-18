package com.iset.gestion_stage.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.UUID;
import com.iset.gestion_stage.entities.*;
import com.iset.gestion_stage.repositories.DemandeStageEntrepriseRepository;
import com.iset.gestion_stage.repositories.DemandeStageRepository;
import com.iset.gestion_stage.services.*;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EntrepriseController {

    private static final Logger log = LoggerFactory.getLogger(EntrepriseController.class);

    @Autowired
    private OffreStageService offreStageService;
    
    @Autowired
    private DemandeStageRepository demandeStageRepository;

    
    @Autowired
    private DemandeStageEntrepriseRepository demandeStageEntrepriseRepository;

    @Autowired
    private EntrepriseService entrepriseService;

    @Autowired
    private CandidatureService candidatureService;

    @Autowired
    private UtilisateurService utilisateurService;

	private OffreStageService candidatureRepository;

    // ✅ Dashboard
    @GetMapping("/entreprise/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) return "redirect:/login";
        Entreprise entreprise = entrepriseService.findByEmail(userDetails.getUsername());
        model.addAttribute("entreprise", entreprise);
        return "entreprise/dashboard";
    }

    // ✅ Create new offer form
    @GetMapping("/entreprise/offres/new")
    public String afficherFormulaireCreation(Model model) {
        model.addAttribute("offre", new OffreStage());
        return "entreprise/creer_offre";
    }

    // ✅ Save offer
    @PostMapping("/entreprise/offres/save")
    public String saveOffre(@ModelAttribute OffreStage offre, Principal principal) {
        Utilisateur utilisateur = utilisateurService.findByEmail(principal.getName());
        if (utilisateur instanceof Entreprise entreprise) {
            offre.setEntreprise(entreprise);
            offreStageService.save(offre);
            log.info("Offre enregistrée pour {}", entreprise.getNom());
            return "redirect:/entreprise/offres";
        }
        log.error("Utilisateur non authentifié comme entreprise.");
        return "redirect:/login";
    }

 // ✅ List offers
    @GetMapping("/entreprise/offres")
    public String afficherOffres(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return "redirect:/login"; // Si l'utilisateur n'est pas connecté, on redirige vers la page de login
        
        // Récupérer l'entreprise associée à l'utilisateur
        Entreprise entreprise = entrepriseService.findByEmail(userDetails.getUsername());
        
        // Récupérer la liste des offres de stage de cette entreprise
        List<OffreStage> offres = offreStageService.findByEntreprise(entreprise);
        
        // Ajouter les offres au modèle pour les afficher dans la vue
        model.addAttribute("offres", offres);
        
        return "entreprise/liste-offres"; // Nom de la vue Thymeleaf pour afficher les offres
    }


    // ✅ Edit offer form
    @GetMapping("/entreprise/offres/{id}/edit")
    public String editOffer(@PathVariable Long id, Model model) {
        OffreStage offre = offreStageService.findById(id).orElseThrow(() -> new RuntimeException("Offre introuvable"));
        model.addAttribute("offre", offre);
        return "entreprise/editOffre";
    }

    // ✅ Update offer
    @PostMapping("/entreprise/offres/{id}/edit")
    public String updateOffer(@PathVariable Long id,
                              @RequestParam String titre,
                              @RequestParam String domaine,
                              @RequestParam String description,
                              @RequestParam Integer duree,
                              @RequestParam String lieu,
                              @RequestParam String dateDebut) {
        OffreStage offre = offreStageService.findById(id).orElseThrow(() -> new RuntimeException("Offre introuvable"));
        offre.setTitre(titre);
        offre.setDomaine(domaine);
        offre.setDescription(description);
        offre.setDuree(duree);
        offre.setLieu(lieu);
        offre.setDateDebut(LocalDate.parse(dateDebut));
        offreStageService.save(offre);
        return "redirect:/entreprise/offres";
    }

    // ✅ Delete offer
    @PostMapping("/entreprise/offres/{id}/delete")
    public String deleteOffer(@PathVariable Long id) {
        OffreStage offre = offreStageService.findById(id).orElseThrow(() -> new RuntimeException("Offre introuvable"));
        offreStageService.delete(offre);
        return "redirect:/entreprise/offres";
    }

   

    @Autowired
    private DemandeStageEntrepriseService demandeStageEntrepriseService;

    @GetMapping("/entreprise/demande_stage")
    public String afficherDemandesStage(Model model) {
        List<DemandeStageEntreprise> demandes = demandeStageEntrepriseService.getAllDemandes();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (DemandeStageEntreprise demande : demandes) {
            if (demande.getDateDemande() != null) {
                // Formatage de la date
                String formattedDate = demande.getDateDemande().format(dateFormatter);
                demande.setFormattedDate(formattedDate);
                
            }
        }

        model.addAttribute("demandes", demandes);
        return "entreprise/demande_stage";
    }
    
    @PostMapping("/entreprise/demande_stage")
    public String uploadCvAndDemande(@RequestParam("cv") MultipartFile cv, 
                                      @RequestParam("demandeId") Long demandeId) throws IOException {
        // Logique pour sauvegarder le CV et mettre à jour la demande
        if (!cv.isEmpty()) {
            String nomOriginal = cv.getOriginalFilename();
            String nomUnique = UUID.randomUUID() + "_" + nomOriginal;
            Path dossierCvs = Paths.get("src/main/resources/static/uploads/cvs");

            if (Files.notExists(dossierCvs)) {
                Files.createDirectories(dossierCvs);
            }

            Path destination = dossierCvs.resolve(nomUnique);
            try {
                Files.copy(cv.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
                // Mise à jour de la demande avec le fichier CV
                DemandeStageEntreprise demande = demandeStageEntrepriseRepository.findById(demandeId)
                    .orElseThrow(() -> new RuntimeException("Demande de stage non trouvée"));
                demande.setCv(nomUnique);
                demandeStageEntrepriseRepository.save(demande);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/entreprise/demande_stage"; // Rediriger après l'upload
    }

    
    
    
    
    
    
    
    
    
    // Action pour accepter une demande
    @PostMapping("/entreprise/demande_stage/{id}/accepter")
    public String accepterDemande(@PathVariable("id") Long id) {
        demandeStageEntrepriseService.updateStatus(id, StatusCandidature.ACCEPTEE);
        return "redirect:/entreprise/demande_stage";
    }

    // Action pour refuser une demande
    @PostMapping("/entreprise/demande_stage/{id}/refuser")
    public String refuserDemande(@PathVariable("id") Long id) {
        demandeStageEntrepriseService.updateStatus(id, StatusCandidature.REFUSEE);
        return "redirect:/entreprise/demande_stage";
    }

   
    // Affiche les candidatures acceptées dans la page entreprise/candidatures.html
    @GetMapping("/candidatures")
    public String afficherCandidaturesAcceptees(Model model) {
        List<DemandeStageEntreprise> candidatures = candidatureService.getAcceptedCandidatures();
        model.addAttribute("candidatures", candidatures);
        return "entreprise/candidatures";
    }

    // Permet à l'entreprise de déposer une attestation pour une candidature donnée
    @PostMapping("/candidatures/{id}/attestation")
    public String deposerAttestation(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        DemandeStageEntreprise demande = candidatureService.getById(id);

        if (!file.isEmpty()) {
            String uploadDir = "uploads/attestations/";
            Files.createDirectories(Paths.get(uploadDir));
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + filename);
            Files.write(filePath, file.getBytes());

            demande.setAttestation(filename);
            candidatureService.save(demande);
        }

        return "redirect:/entreprise/candidatures";
    }







  
    // ✅ Profile page
    @GetMapping("/entreprise/profile")
    public String afficherProfil(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return "redirect:/login";
        Entreprise entreprise = entrepriseService.findByEmail(userDetails.getUsername());
        model.addAttribute("entreprise", entreprise);
        return "entreprise/profile";
    }

    // ✅ Logout redirection
    @GetMapping("/entreprise/logout")
    public String logout() {
        return "redirect:/logout";
    }
    
    
  


    
    
    
    
    
    
    
    
    
}
