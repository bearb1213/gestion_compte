package com.bank.courant.dao;

import com.bank.courant.model.Status;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class StatusDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(Status status) {
        em.persist(status);
    }

    // READ by id
    public Status find(int id) {
        return em.find(Status.class, id);
    }

    // READ ALL
    public List<Status> findAll() {
        return em.createQuery("SELECT s FROM Status s", Status.class)
                 .getResultList();
    }

    // UPDATE
    public Status update(Status status) {
        return em.merge(status);
    }

    // DELETE
    public void delete(int id) {
        Status status = em.find(Status.class, id);
        if (status != null) {
            em.remove(status);
        }
    }

    // READ by libelle
    public Status findByLibelle(String libelle) {
        List<Status> result = em.createQuery(
                "SELECT s FROM Status s WHERE s.libelle = :libelle", Status.class)
                .setParameter("libelle", libelle)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
