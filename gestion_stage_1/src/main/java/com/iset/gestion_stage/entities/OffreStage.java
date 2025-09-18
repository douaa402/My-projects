package com.iset.gestion_stage.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class OffreStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    
    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;
    
    private String titre;
    private String description;
    private String domaine;
    private boolean active;
    private int duree;
    private String lieu;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;

    public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate localDate) {
		this.dateDebut = localDate;
	}

	public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }


   

    public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}




	@OneToMany(mappedBy = "offre")
    private List<DemandeStage> demandes;

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDomaine() {
        return domaine;
    }
    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
   
    public List<DemandeStage> getDemandes() {
        return demandes;
    }
    public void setDemandes(List<DemandeStage> demandes) {
        this.demandes = demandes;
    }
    // Constructors
    public OffreStage() {
    }
    public OffreStage(String titre, String description, String domaine, boolean active, Entreprise entreprise) {
        this.titre = titre;
        this.description = description;
        this.domaine = domaine;
        this.active = active;
       
    }
    // toString method
    @Override
    public String toString() {
        return "OffreStage{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", domaine='" + domaine + '\'' +
                ", active=" + active +
                
                '}';
    }
    
    

	public OffreStage(String titre, String description, String domaine, boolean active, int duree, String lieu,
			LocalDate dateDebut, Entreprise entreprise, List<DemandeStage> demandes) {
		super();
		this.titre = titre;
		this.description = description;
		this.domaine = domaine;
		this.active = active;
		this.duree = duree;
		this.lieu = lieu;
		this.dateDebut = dateDebut;
		
		this.demandes = demandes;
	}
}
