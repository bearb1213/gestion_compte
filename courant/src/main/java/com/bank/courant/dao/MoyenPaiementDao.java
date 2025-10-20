package com.bank.courant.dao;

import com.bank.courant.model.MoyenPaiement;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class MoyenPaiementDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(MoyenPaiement moyenPaiement) {
        em.persist(moyenPaiement);
    }

    // READ by id
    public MoyenPaiement find(int id) {
        return em.find(MoyenPaiement.class, id);
    }

    // READ ALL
    public List<MoyenPaiement> findAll() {
        return em.createQuery("SELECT m FROM MoyenPaiement m", MoyenPaiement.class)
                 .getResultList();
    }

    // UPDATE
    public MoyenPaiement update(MoyenPaiement moyenPaiement) {
        return em.merge(moyenPaiement);
    }

    // DELETE
    public void delete(int id) {
        MoyenPaiement moyenPaiement = em.find(MoyenPaiement.class, id);
        if (moyenPaiement != null) {
            em.remove(moyenPaiement);
        }
    }

    // READ by libelle
    public MoyenPaiement findByLibelle(String libelle) {
        List<MoyenPaiement> result = em.createQuery(
                "SELECT m FROM MoyenPaiement m WHERE m.libelle = :libelle", MoyenPaiement.class)
                .setParameter("libelle", libelle)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
