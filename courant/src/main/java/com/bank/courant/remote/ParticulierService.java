package com.bank.courant.remote;

import java.time.LocalDate;
import java.util.List;

import com.bank.courant.model.Particulier;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



@Stateless
public class ParticulierService implements ParticulierRemote {
    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;
    
    
    public Particulier createParticulier(String cin, String nom, String prenom, LocalDate dateNaissance, String adresse, String telephone, String email)throws Exception {
        if (LocalDate.now().getYear()-dateNaissance.getYear()<18) {
            throw new Exception("Vous n'etes pas assez age");
        }
        Particulier particulier = new Particulier();
        particulier.setCin(cin);
        particulier.setNom(nom);
        particulier.setPrenom(prenom);
        particulier.setDateNaissance(dateNaissance);
        particulier.setAdresse(adresse);
        particulier.setTelephone(telephone);
        particulier.setEmail(email);
        particulier.save(em);

        return particulier;

    }

    public Particulier createParticulier(String cin, String nom, String prenom, String dateNaissance, String adresse, String telephone, String email) throws Exception {
        
        LocalDate dateNaiss = LocalDate.parse(dateNaissance);
        if (LocalDate.now().getYear()-dateNaiss.getYear()<18) {
            throw new Exception("Vous n'etes pas assez age");
        }

        Particulier particulier = new Particulier();
        particulier.setCin(cin);
        particulier.setNom(nom);
        particulier.setPrenom(prenom);
        particulier.setDateNaissance(dateNaiss);
        particulier.setAdresse(adresse);
        particulier.setTelephone(telephone);
        particulier.setEmail(email);
        particulier.save(em);

        return particulier;

    }

    public Particulier updateParticulier(String id,String cin, String nom, String prenom, String dateNaissance, String adresse, String telephone, String email) throws Exception {
        

        int ids= Integer.parseInt(id);
        LocalDate dateNaiss = LocalDate.parse(dateNaissance);
        if (LocalDate.now().getYear()-dateNaiss.getYear()<18) {
            throw new Exception("Vous n'etes pas assez age");
        }
        
        Particulier particulier = new Particulier();
        particulier.setId(ids);
        particulier.setCin(cin);
        particulier.setNom(nom);
        particulier.setPrenom(prenom);
        particulier.setDateNaissance(dateNaiss);
        particulier.setAdresse(adresse);
        particulier.setTelephone(telephone);
        particulier.setEmail(email);
        particulier.save(em);

        return particulier;

    }

    


    public Particulier getParticulierById(int id) {
        return Particulier.findById(id,em);
    }

    public void deleteParticulier(int id) {
        Particulier particulier = Particulier.findById(id,em);
        if (particulier != null) {
            particulier.delete(em);
        }
    }

    public void updateParticulier(int id, String cin, String nom, String prenom, LocalDate dateNaissance, String adresse, String telephone, String email) throws Exception {
        Particulier particulier = Particulier.findById(id,em);
        if (particulier != null) {
            particulier.setCin(cin);
            particulier.setNom(nom);
            particulier.setPrenom(prenom);
            particulier.setDateNaissance(dateNaissance);
            particulier.setAdresse(adresse);
            particulier.setTelephone(telephone);
            particulier.setEmail(email);
            particulier.save(em);
        } else {
            throw new IllegalArgumentException("Particulier avec id " + id + " n'existe pas.");
        }
    }

    public List<Particulier> getAllParticuliers() {
        return Particulier.findAll(em);
    }


}
