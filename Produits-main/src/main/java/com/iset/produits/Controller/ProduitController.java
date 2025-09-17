package com.iset.produits.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.iset.produits.entity.Produit;
import com.iset.produits.service.ProduitService;

@Controller
public class ProduitController {

    @Autowired
    ProduitService produitService;

    @GetMapping("/")
    public String Template() {
        return "Template";
    }

    @RequestMapping("/showCreate")
    public String showCreate(ModelMap modelMap) {
        modelMap.addAttribute("produit", new Produit());
        return "createProduit";
    }

    @RequestMapping("/saveProduit")
    public String saveProduit(@Valid Produit produit,
                              BindingResult bindingResult,
                              ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            return "createProduit";
        }
        Produit saveProduit = produitService.saveProduit(produit);
        String msg = "produit enregistré avec Id " +
                saveProduit.getIdProduit();
        modelMap.addAttribute("msg", msg);
        return "createProduit";
    }

    @RequestMapping("/ListeProduits")
    public String listeProduits(
            ModelMap modelMap,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size) {
        Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeProduits";
    }

    @RequestMapping("/supprimerProduit")
    public String supprimerProduit(@RequestParam("id") Long id, ModelMap
                                           modelMap,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "5") int size) {
        produitService.deleteProduitById(id);
        Page<Produit> prods = produitService.getAllProduitsParPage(page,
                size);
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeProduits";
    }

    @GetMapping("/modifierProduit")
    public String editerProduit(@RequestParam("id") Long id, Model model) {
        Produit p = produitService.getProduit(id);
        model.addAttribute("produit", p);
        return "editerProduit";
    }

    @PostMapping("/updateProduit")
    public String updateProduit(@ModelAttribute Produit produit, @RequestParam("date") String date, Model model)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        produit.setDateCreation(dateFormat.parse(date));

        produitService.updateProduit(produit);
        model.addAttribute("produits", produitService.getAllProduits());

        return "listeProduits";
    }

    @GetMapping("/searchProduit")
    public String searchProduit(@RequestParam("id") Long id, Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "6") int size) {
        Produit produit = produitService.getProduit(id);
        if (produit != null) {
            model.addAttribute("searchedProduit", produit);
        } else {
            model.addAttribute("error", "Produit introuvable !");
        }

        // Keep the paginated list of products
        Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
        model.addAttribute("produits", prods);
        model.addAttribute("pages", new int[prods.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        return "listeProduits";
    }
}