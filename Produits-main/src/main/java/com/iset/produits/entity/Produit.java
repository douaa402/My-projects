package com.iset.produits.entity;

import java.util.Date;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;


@Entity 
public class Produit {  

@Id 
@GeneratedValue (strategy = GenerationType.IDENTITY) 
private Long idProduit;
	@NotNull
	@Size(min = 4,max = 15)
private String nomProduit;
	@NotNull
	@Min(value = 10)
	@Max(value = 10000)
private Double prixProduit;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent
private Date dateCreation;

public Produit(String nomProduit, Double prixProduit, Date dateCreation) {
	super();
	this.nomProduit = nomProduit;
	this.prixProduit = prixProduit;
	this.dateCreation = dateCreation;
}
	@ManyToOne
	private Categorie categorie;
 public Produit() {}
 
public Long getIdProduit() {
	return idProduit;
}
public void setIdProduit(Long idProduit) {
	this.idProduit = idProduit;
}
public String getNomProduit() {
	return nomProduit;
}
public void setNomProduit(String nomProduit) {
	this.nomProduit = nomProduit;
}
public Double getPrixProduit() {
	return prixProduit;
}
public void setPrixProduit(Double prixProduit) {
	this.prixProduit = prixProduit;
}
public Date getDateCreation() {
	return dateCreation;
}
public void setDateCreation(Date dateCreation) {
	this.dateCreation = dateCreation;
}
 
 } 