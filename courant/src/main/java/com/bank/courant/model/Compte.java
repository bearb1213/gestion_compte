package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;
import com.bank.courant.util.JpaUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.bank.courant.util.JpaUtil;
import com.bank.courant.util.NumeroGenerator;;

/**
 * Représente un compte bancaire courant.
 * <p>
 * Champs :
 * <ul>
 *   <li>id : identifiant unique du compte</li>
 *   <li>numeroCompte : numéro du compte bancaire</li>
 *   <li>dateOuverture : date d'ouverture du compte</li>
 *   <li>montantDecouvert : montant autorisé du découvert</li>
 *   <li>particulier : propriétaire du compte</li>
 * </ul>
 */
@Entity
@Table(name = "compte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "numero_compte", nullable = false, unique = true, length = 50)
    private String numeroCompte;

    @Column(name = "date_ouverture", nullable = false)
    private LocalDate dateOuverture;

    @Column(name = "montant_decouvert", precision = 15)
    private Double montantDecouvert;

    @Column(name = "plafond", precision = 15)
    private Double plafond;

    // 🔹 Relation vers Particulier
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_particulier", nullable = false)
    private Particulier particulier;

    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static Compte findById(int id,EntityManager em){
        return em.find(Compte.class, id);
    }

    public static Compte findByNumeroCompte(String numeroCompte,EntityManager em){
        List<Compte> comptes = em.createQuery("SELECT c FROM Compte c WHERE c.numeroCompte = :numeroCompte", Compte.class)
                .setParameter("numeroCompte", numeroCompte)
                .getResultList();
        return comptes.isEmpty() ? null : comptes.get(0);
    }

    public void delete(EntityManager em){
        Compte relited = em.merge(this);
        em.remove(relited);
    }

    public static List<Compte> findAll(EntityManager em){ 
        return em.createQuery("SELECT c FROM Compte c", Compte.class).getResultList();
    }


    public Compte CreateCompte(int id_particulier , LocalDate dateOuverture , Double montantDecouvert , Double plafond,EntityManager em ) throws Exception {
        Particulier particulier = Particulier.findById(id_particulier,em);
        if(particulier == null){
            throw new IllegalArgumentException("Particulier avec id " + id_particulier + " n'existe pas.");
        }
        this.particulier = particulier;
        this.dateOuverture = dateOuverture;
        this.montantDecouvert = montantDecouvert;
        this.plafond = plafond;
        this.numeroCompte = NumeroGenerator.generateNumeroCompteCourant();

        StatusCompte statusCompte = StatusCompte.findById(1,em); // Statut "Actif"
        if(statusCompte == null){
            throw new IllegalArgumentException("StatusCompte avec id 1 n'existe pas.");
        }

        HistoStatus histoStatus = new HistoStatus();
        histoStatus.setCompte(this);
        histoStatus.setStatus(statusCompte);
        histoStatus.setDateChangement(LocalDateTime.now());


        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(this);
            em.persist(histoStatus);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }

        
        return this;
    }
}
