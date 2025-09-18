package com.iset.gestion_stage.entities;

import jakarta.persistence.*;
import java.util.List;
@Entity
public class Etudiant extends Utilisateur {
	 @OneToOne
	    @JoinColumn(name = "utilisateur_id", nullable = false)
	    private Utilisateur utilisateur;

    private String cin;
    private String niveau;
    private String specialite;

    @OneToMany(mappedBy = "etudiant")
    private List<DemandeStage> demandes;

    @ManyToOne
@JoinColumn(name = "encadrant_id")
private Encadrant encadrant;
    //getters and setters
    public String getCin() {
        return cin;
    }
    public void setCin(String cin) {
        this.cin = cin;
    }
    public String getNiveau() {
        return niveau;
    }
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    public String getSpecialite() {
        return specialite;
    }
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    public List<DemandeStage> getDemandes() {
        return demandes;
    }
    public void setDemandes(List<DemandeStage> demandes) {
        this.demandes = demandes;
    }
    @Override
    public String toString() {
        return "Etudiant{" +
                "cin='" + cin + '\'' +
                ", niveau='" + niveau + '\'' +
                ", specialite='" + specialite + '\'' +
                ", demandes=" + demandes +
                '}';
    }
    // Constructors
    public Etudiant(String cin, String niveau, String specialite, List<DemandeStage> demandes) {
        this.cin = cin;
        this.niveau = niveau;
        this.specialite = specialite;
        this.demandes = demandes;
    }
    public Etudiant() {
    }
    public Etudiant(String cin, String niveau, String specialite) {
        this.cin = cin;
        this.niveau = niveau;
        this.specialite = specialite;
    }
    public Encadrant getEncadrant() {
        return encadrant;
    }
    public void setEncadrant(Encadrant encadrant) {
        this.encadrant = encadrant;
    }
    public Etudiant(String cin, String niveau, String specialite, List<DemandeStage> demandes, Encadrant encadrant) {
        this.cin = cin;
        this.niveau = niveau;
        this.specialite = specialite;
        this.demandes = demandes;
        this.encadrant = encadrant;
    }
    public Etudiant(String cin, String niveau, String specialite, Encadrant encadrant) {
        this.cin = cin;
        this.niveau = niveau;
        this.specialite = specialite;
        this.encadrant = encadrant;
    }
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
}
