package com.iset.gestion_stage.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class DemandeStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statut;
    private String message;
    private String cvFileName;

    @Column(name = "date_depot")
    private LocalDateTime dateDepot;

    public LocalDateTime getDateDemande() {
        return dateDepot;
    }

    public void setDateDemande(LocalDateTime localDateTime) {
        this.dateDepot = localDateTime;
    }

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "offre_id")
    private OffreStage offre;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCvFileName() { return cvFileName; }
    public void setCvFileName(String cvFileName) { this.cvFileName = cvFileName; }

    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }

    public OffreStage getOffre() { return offre; }
    public void setOffre(OffreStage offre) { this.offre = offre; }

    public DemandeStage() {}

    public DemandeStage(String statut, String message, String cvFileName, Etudiant etudiant, OffreStage offre) {
        this.statut = statut;
        this.message = message;
        this.cvFileName = cvFileName;
        this.etudiant = etudiant;
        this.offre = offre;
    }

	public void setAccepte(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
