package com.iset.gestion_stage.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Entreprise extends Utilisateur {
    
	
	

	
	private String telephone;
    private String nomEntreprise;
    private String adresse;
    private String secteur;

    @OneToMany(mappedBy = "entreprise")
    private List<OffreStage> offres;

    //getters and setters
    public String getNomEntreprise() {
        return nomEntreprise;
    }
    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getTelephone() {
	    return telephone;
	}

	public void setTelephone(String telephone) {
	    this.telephone = telephone;
	}
    public String getSecteur() {
        return secteur;
    }
    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }
    public List<OffreStage> getOffres() {
        return offres;
    }
    public void setOffres(List<OffreStage> offres) {
        this.offres = offres;
    }
    // Constructor
    public Entreprise(String nomEntreprise, String adresse, String secteur, List<OffreStage> offres) {
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.secteur = secteur;
        this.offres = offres;
    }
    public Entreprise() {
    }
    public Entreprise(String nomEntreprise, String adresse, String secteur) {
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.secteur = secteur;
    }
	public Entreprise(String telephone, String nomEntreprise, String adresse, String secteur, List<OffreStage> offres) {
		this.telephone = telephone;
		this.nomEntreprise = nomEntreprise;
		this.adresse = adresse;
		this.secteur = secteur;
		this.offres = offres;
	}
}