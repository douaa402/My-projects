package com.iset.gestion_stage.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class DemandeStageEntreprise {
	@OneToOne
	@JoinColumn(name = "demande_stage_id")
	private DemandeStage demandeStage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String attestation;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant; // Référence à l'étudiant

    @ManyToOne
    @JoinColumn(name = "offre_stage_id", nullable = false)
    private OffreStage offreStage; // Référence à l'offre de stage

    private LocalDate dateDemande; // Date de la candidature

    @Enumerated(EnumType.STRING)
    private StatusCandidature status; // Statut de la candidature (ex: EN_ATTENTE, ACCEPTÉE, REJETÉE)

    // Ajout des champs pour le nom et prénom de l'étudiant
    private String nomEtudiant;
    private String prenomEtudiant;
    private String formattedDate;
    private String message;

    // ✅ Nouveau champ : chemin ou nom du fichier CV
    private String cv;
    
  

    public String getAttestation() {
        return attestation;
    }

    public void setAttestation(String attestation) {
        this.attestation = attestation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
	// Getters et setters pour les nouveaux champs
    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant = prenomEtudiant;
    }

    // Getters et setters existants
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public OffreStage getOffreStage() {
        return offreStage;
    }

    public void setOffreStage(OffreStage offreStage) {
        this.offreStage = offreStage;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public StatusCandidature getStatus() {
        return status;
    }

    public void setStatus(StatusCandidature enAttente) {
        this.status = enAttente;
    }
    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
    
}
