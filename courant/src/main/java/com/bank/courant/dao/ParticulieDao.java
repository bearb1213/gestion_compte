package com.bank.courant.dao;

import com.bank.courant.model.Particulie;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ParticulieDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(Particulie p) {
        em.persist(p);
    }

    // READ
    public Particulie find(int id) {
        return em.find(Particulie.class, id);
    }

    //Read by cin
    public Particulie findByCin(String cin) {
        List<Particulie> results = em.createQuery("SELECT p FROM Particulie p WHERE p.cin = :cin", Particulie.class)
                                     .setParameter("cin", cin)
                                     .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }


    // READ ALL
    public List<Particulie> findAll() {
        return em.createQuery("SELECT p FROM Particulie p", Particulie.class)
                 .getResultList();
    }

    // UPDATE
    public Particulie update(Particulie p) {
        return em.merge(p);
    }

    // DELETE
    public void delete(int id) {
        Particulie p = em.find(Particulie.class, id);
        if (p != null) {
            em.remove(p);
        }
    }
}
