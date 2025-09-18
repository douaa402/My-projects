package com.iset.gestion_stage.entities;

import jakarta.persistence.*;

@Entity
public class RapportFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fichierPath;

    @OneToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    //Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFichierPath() {
        return fichierPath;
    }
    public void setFichierPath(String fichierPath) {
        this.fichierPath = fichierPath;
    }
    public Etudiant getEtudiant() {
        return etudiant;
    }
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    // Constructors
    public RapportFinal() {
    }
    public RapportFinal(String fichierPath, Etudiant etudiant) {
        this.fichierPath = fichierPath;
        this.etudiant = etudiant;
    }
    // toString method
    @Override
    public String toString() {
        return "RapportFinal{" +
                "id=" + id +
                ", fichierPath='" + fichierPath + '\'' +
                ", etudiant=" + etudiant +
                '}';
    }
}

