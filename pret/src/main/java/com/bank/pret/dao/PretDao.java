package com.bank.pret.dao;

import com.bank.pret.model.Pret;
import com.bank.pret.model.Compte;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PretDao {

    @PersistenceContext(unitName = "PretPU")
    private EntityManager em;

    public void create(Pret pret) {
        em.persist(pret);
    }

    public Pret find(int id) {
        return em.find(Pret.class, id);
    }

    public List<Pret> findAll() {
        return em.createQuery("SELECT p FROM Pret p", Pret.class)
                 .getResultList();
    }

    public List<Pret> findByCompteId(int compteId) {
        return em.createQuery("SELECT p FROM Pret p WHERE p.compte.id = :id", Pret.class)
                 .setParameter("id", compteId)
                 .getResultList();
    }

    public List<Pret> findByCompte(Compte compte) {
        return em.createQuery("SELECT p FROM Pret p WHERE p.compte = :compte", Pret.class)
                 .setParameter("compte", compte)
                 .getResultList();
    }

    public void update(Pret pret) {
        em.merge(pret);
    }

    public void delete(int id) {
        Pret p = em.find(Pret.class, id);
        if (p != null) {
            em.remove(p);
        }
    }
}
