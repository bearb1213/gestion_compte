package com.bank.courant.remote;

import com.bank.courant.model.Compte;
import com.bank.courant.model.CompteComplet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.ejb.Stateless;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class CompteService implements CompteRemote {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;
    
    
    public CompteComplet createCompte(int id_particulier , LocalDate dateOuverture , Double montantDecouvert , Double plafond ) throws Exception {
        return new CompteComplet().createCompte(id_particulier, dateOuverture, montantDecouvert, plafond,em);
    }

    public CompteComplet createCompte(String id_particulier , String dateOuverture , String montantDecouvert , String plafond ) throws Exception {
        int idParticulier = Integer.parseInt(id_particulier);
        LocalDate dateOuv = LocalDate.parse(dateOuverture);
        Double montantDec = Double.parseDouble(montantDecouvert);
        Double plaf = Double.parseDouble(plafond);
        return new CompteComplet().createCompte(idParticulier, dateOuv, montantDec, plaf,em);
    }

    public CompteComplet findById(int id) throws Exception {
        CompteComplet compte = new CompteComplet() ;
        compte.findById(id,em);
        if (compte.getCompte() == null) {
            throw new IllegalArgumentException("Compte avec id " + id + " n'existe pas.");
            
        }
        return compte;
    }

    public CompteComplet findByNumeroCompte(String numeroCompte) throws Exception {
        CompteComplet compte = new CompteComplet() ;
        compte.findByNumeroCompte(numeroCompte,em);
        if (compte.getCompte() == null) {
            throw new IllegalArgumentException("Compte avec numeroCompte " + numeroCompte + " n'existe pas.");
        }
        return compte;
    }

    public List<Compte> getAllComptes() {
        return Compte.findAll(em);
    }

    public Compte findCompteById(int id){
        Compte c = Compte.findById(id, em);
        return c;
    }

}
