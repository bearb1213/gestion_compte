package com.bank.courant.dao;

import com.bank.courant.model.Frais;
import com.bank.courant.model.Compte;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class FraisDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(Frais frais) {
        em.persist(frais);
    }

    // READ by id
    public Frais find(int id) {
        return em.find(Frais.class, id);
    }

    // READ ALL
    public List<Frais> findAll() {
        return em.createQuery("SELECT f FROM Frais f", Frais.class)
                 .getResultList();
    }

    // UPDATE
    public Frais update(Frais frais) {
        return em.merge(frais);
    }

    // DELETE
    public void delete(int id) {
        Frais frais = em.find(Frais.class, id);
        if (frais != null) {
            em.remove(frais);
        }
    }

    // READ ALL BY COMPTE
    public List<Frais> findByCompte(Compte compte) {
        return em.createQuery(
                "SELECT f FROM Frais f WHERE f.compte = :compte", Frais.class)
                .setParameter("compte", compte)
                .getResultList();
    }
}
