package com.iset.gestion_stage.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "attestations") // facultatif, mais bon pour éviter conflits de nom
public class Attestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_entreprise", nullable = false)
    private String nomEntreprise;

    @Column(name = "date_envoi", nullable = false)
    private LocalDate dateEnvoi;

    @Column(name = "fichier_attestation", nullable = false)
    private String fichierAttestation;  // Nom du fichier stocké

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public LocalDate getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDate dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getFichierAttestation() {
        return fichierAttestation;
    }

    public void setFichierAttestation(String fichierAttestation) {
        this.fichierAttestation = fichierAttestation;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}
