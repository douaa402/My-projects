package com.iset.gestion_stage.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Encadrant extends Utilisateur {

    private String departement;

    @OneToMany(mappedBy = "encadrant")
    private List<Etudiant> etudiantsEncadres;

    //getters and setters
    public String getDepartement() {
        return departement;
    }
    public void setDepartement(String departement) {
        this.departement = departement;
    }
    public List<Etudiant> getEtudiantsEncadres() {
        return etudiantsEncadres;
    }
    public void setEtudiantsEncadres(List<Etudiant> etudiantsEncadres) {
        this.etudiantsEncadres = etudiantsEncadres;
    }
    @Override
    public String toString() {
        return "Encadrant{" +
                "departement='" + departement + '\'' +
                ", etudiantsEncadres=" + etudiantsEncadres +
                '}';
    }
    //constructor
    public Encadrant() {
    }
   
    public Encadrant(String nom, String prenom, String email, String motDePasse, Role role, String departement) {
        super(nom, prenom, email, motDePasse, role);
        this.departement = departement;
    }
    public Encadrant(String nom, String prenom, String email, String motDePasse, Role role, String departement, List<Etudiant> etudiantsEncadres) {
        super(nom, prenom, email, motDePasse, role);
        this.departement = departement;
        this.etudiantsEncadres = etudiantsEncadres;
    }
    public Encadrant(String nom, String prenom, String email, String motDePasse, Role role, List<Etudiant> etudiantsEncadres) {
        super(nom, prenom, email, motDePasse, role);
        this.etudiantsEncadres = etudiantsEncadres;
    }
}

