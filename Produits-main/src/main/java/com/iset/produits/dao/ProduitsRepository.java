package com.iset.produits.dao;

import com.iset.produits.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iset.produits.entity.Produit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitsRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByNomProduit(String nomProduit);

    List<Produit> findByNomProduitContains(String nom);
    List<Produit> findByCategorieIdCat(Long id);
    List<Produit> findByOrderByNomProduitAsc();
    @Query("select p from Produit p where p.nomProduit like %?1 and p.prixProduit > ?2")
    List<Produit> findByNomPrix (String nom, Double prix);

    @Query("select p from Produit p where p.nomProduit like %:nom and p.prixProduit > :prix")
    List<Produit> findByNomPrix1 (@Param("nom") String nom, @Param("prix") Double prix);

    @Query("select p from Produit p where p.categorie = ?1")
    List<Produit> findByCategorie (Categorie categorie);

    @Query("select p from Produit p order by p.nomProduit ASC, p.prixProduit DESC")
    List<Produit> trierProduitsNomsPrix ();
}
