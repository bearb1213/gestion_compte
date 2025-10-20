package com.bank.pret.dao;

import com.bank.pret.model.Status;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class StatusDao {

    @PersistenceContext(unitName = "PretPU")
    private EntityManager em;

    public void create(Status status) {
        em.persist(status);
    }

    public Status find(int id) {
        return em.find(Status.class, id);
    }

    public List<Status> findAll() {
        return em.createQuery("SELECT s FROM Status s", Status.class)
                 .getResultList();
    }

    public void update(Status status) {
        em.merge(status);
    }

    public void delete(int id) {
        Status s = em.find(Status.class, id);
        if (s != null) {
            em.remove(s);
        }
    }

    public Status findByLibelle(String libelle) {
        List<Status> statuses = em.createQuery(
                "SELECT s FROM Status s WHERE s.libelle = :libelle", Status.class)
                .setParameter("libelle", libelle)
                .getResultList();
        return statuses.isEmpty() ? null : statuses.get(0);
    }
}
