package com.bank.courant.dao;

import com.bank.courant.model.Revenu;
import com.bank.courant.model.Particulie;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RevenuDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(Revenu revenu) {
        em.persist(revenu);
    }

    // READ by id
    public Revenu find(int id) {
        return em.find(Revenu.class, id);
    }

    // READ ALL
    public List<Revenu> findAll() {
        return em.createQuery("SELECT r FROM Revenu r", Revenu.class)
                 .getResultList();
    }

    // UPDATE
    public Revenu update(Revenu revenu) {
        return em.merge(revenu);
    }

    // DELETE
    public void delete(int id) {
        Revenu revenu = em.find(Revenu.class, id);
        if (revenu != null) {
            em.remove(revenu);
        }
    }

    // READ ALL BY PARTICULIER
    public List<Revenu> findByParticulier(Particulie particulier) {
        return em.createQuery(
                "SELECT r FROM Revenu r WHERE r.particulier = :particulier ORDER BY r.dateChangement DESC",
                Revenu.class)
                .setParameter("particulier", particulier)
                .getResultList();
    }
}
