package com.bank.courant.remote;

import java.util.List;

import com.bank.courant.model.Compte;
import com.bank.courant.model.CompteComplet;

import jakarta.ejb.Remote;

@Remote
public interface CompteRemote {
    
    CompteComplet createCompte(String id_particulier , String dateOuverture , String montantDecouvert , String plafond ) throws Exception ;
    
    CompteComplet findById(int id) throws Exception ;

    CompteComplet findByNumeroCompte(String numeroCompte) throws Exception ;

    List<Compte> getAllComptes();
}
