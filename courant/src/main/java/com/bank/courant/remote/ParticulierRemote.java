package com.bank.courant.remote;

import jakarta.ejb.Remote;

import java.time.LocalDate;
import com.bank.courant.model.Particulier;
import java.util.List;

@Remote
public interface ParticulierRemote {

    Particulier createParticulier(String cin, String nom, String prenom, String dateNaissance, String adresse, String telephone, String email) throws Exception ;
        
    Particulier updateParticulier(String id,String cin, String nom, String prenom, String dateNaissance, String adresse, String telephone, String email) throws Exception;

    Particulier getParticulierById(int id);

    void deleteParticulier(int id);

    void updateParticulier(int id, String cin, String nom, String prenom, LocalDate dateNaissance, String adresse, String telephone, String email) throws Exception;

    List<Particulier> getAllParticuliers();

}
