package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.bank.courant.util.JpaUtil;
import com.bank.courant.util.NumeroGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteComplet implements Serializable {
    private Compte compte;
    private StatusCompte statusActuelCompte;
    private List<HistoStatus> histoStatuses;
    

    public CompteComplet createCompte(int id_particulier , LocalDate dateOuverture , Double montantDecouvert , Double plafond , EntityManager em ) throws Exception {
        Particulier particulier = Particulier.findById(id_particulier,em);
        if(particulier == null){
            throw new IllegalArgumentException("Particulier avec id " + id_particulier + " n'existe pas.");
        }
        if(montantDecouvert < 0){
            throw new IllegalArgumentException("Montant decouvert ne doit pas etre negatif");
        }
        if(plafond < 0){   
            throw new IllegalArgumentException("Montant ne doit pas etre negatif");
        }

        this.compte = new Compte();
        this.compte.setParticulier(particulier);
        this.compte.setDateOuverture(dateOuverture);
        this.compte.setMontantDecouvert(montantDecouvert);
        this.compte.setPlafond(plafond);
        this.compte.setNumeroCompte(NumeroGenerator.generateNumeroCompteCourant());

        StatusCompte statusCompte = StatusCompte.findById(1,em); // Statut "Actif"
        if(statusCompte == null){
            throw new IllegalArgumentException("StatusCompte avec id 1 n'existe pas.");
        }
        this.statusActuelCompte = statusCompte;

        HistoStatus histoStatus = new HistoStatus();
        histoStatus.setCompte(this.compte);
        histoStatus.setStatus(statusCompte);
        histoStatus.setDateChangement(LocalDateTime.now());

        this.histoStatuses = List.of(histoStatus);

        try {
            em.persist(this.compte);
            em.persist(histoStatus);
        } catch (Exception e) {
            throw e;
        }

        
        return this;


    }
    
    public CompteComplet findById(int id ,EntityManager em){
        Compte compte = em.find(Compte.class, id);
        if(compte == null){
            return null;
        }
        List<HistoStatus> histoStatuses = em.createQuery("SELECT hs FROM HistoStatus hs WHERE hs.compte.id = :compteId ORDER BY hs.dateChangement DESC", HistoStatus.class)
                .setParameter("compteId", id)
                .getResultList();
        StatusCompte statusActuelCompte = histoStatuses.isEmpty() ? null : histoStatuses.get(0).getStatus();

        this.compte = compte;
        this.statusActuelCompte = statusActuelCompte;
        this.histoStatuses = histoStatuses;

        return CompteComplet.builder()
                .compte(compte)
                .statusActuelCompte(statusActuelCompte)
                .histoStatuses(histoStatuses)
                .build();
    }

    public CompteComplet findByNumeroCompte(String numeroCompte ,EntityManager em){
        Compte compte = Compte.findByNumeroCompte(numeroCompte,em);
        if(compte == null){
            return null;
        }
        int id = compte.getId();
        List<HistoStatus> histoStatuses = em.createQuery("SELECT hs FROM HistoStatus hs WHERE hs.compte.id = :compteId ORDER BY hs.dateChangement DESC", HistoStatus.class)
                .setParameter("compteId", id)
                .getResultList();
        StatusCompte statusActuelCompte = histoStatuses.isEmpty() ? null : histoStatuses.get(0).getStatus();

        this.compte = compte;
        this.statusActuelCompte = statusActuelCompte;
        this.histoStatuses = histoStatuses;

        return CompteComplet.builder()
                .compte(compte)
                .statusActuelCompte(statusActuelCompte)
                .histoStatuses(histoStatuses)
                .build();
    }

    
    




    



}
