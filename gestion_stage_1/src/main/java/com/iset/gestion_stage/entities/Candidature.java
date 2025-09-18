package com.iset.gestion_stage.entities;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OffreStage offreStage;

    @ManyToOne
    private Utilisateur etudiant;

    private String cv;  // Lien ou chemin vers le CV
    private String message;  // Message du candidat

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCandidature;
    
    


	// Getters et setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public OffreStage getOffreStage() {
        return offreStage;
    }
    public void setOffreStage(OffreStage offreStage) {
        this.offreStage = offreStage;
    }
    public Utilisateur getEtudiant() {
        return etudiant;
    }
    public void setEtudiant(Utilisateur etudiant) {
        this.etudiant = etudiant;
    }
    public String getCv() {
        return cv;
    }
    public void setCv(String cv) {
        this.cv = cv;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getDateCandidature() {
        return dateCandidature;
    }
    public void setDateCandidature(Date localDateTime) {
        this.dateCandidature = localDateTime;
    }
    // Constructeur par défaut
    public Candidature() {
    }
    // Constructeur avec paramètres
    public Candidature(OffreStage offreStage, Utilisateur etudiant, String cv, String message, Date dateCandidature) {
        this.offreStage = offreStage;
        this.etudiant = etudiant;
        this.cv = cv;
        this.message = message;
        this.dateCandidature = dateCandidature;
    }
    // Méthode toString
    @Override
    public String toString() {
        return "Candidature{" +
                "id=" + id +
                ", offreStage=" + offreStage +
                ", etudiant=" + etudiant +
                ", cv='" + cv + '\'' +
                ", message='" + message + '\'' +
                ", dateCandidature=" + dateCandidature +
                '}';
    }
	
}