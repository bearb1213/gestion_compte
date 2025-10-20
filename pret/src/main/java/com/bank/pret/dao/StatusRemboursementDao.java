package com.bank.pret.dao;

import com.bank.pret.model.StatusRemboursement;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class StatusRemboursementDao {

    @PersistenceContext(unitName = "PretPU")
    private EntityManager em;

    public void create(StatusRemboursement status) {
        em.persist(status);
    }

    public StatusRemboursement find(int id) {
        return em.find(StatusRemboursement.class, id);
    }

    public List<StatusRemboursement> findAll() {
        return em.createQuery("SELECT s FROM StatusRemboursement s", StatusRemboursement.class)
                 .getResultList();
    }

    public void update(StatusRemboursement status) {
        em.merge(status);
    }

    public void delete(int id) {
        StatusRemboursement s = em.find(StatusRemboursement.class, id);
        if (s != null) {
            em.remove(s);
        }
    }
    public StatusRemboursement findByLibelle(String libelle) {
        List<StatusRemboursement> statuses = em.createQuery(
                "SELECT s FROM StatusRemboursement s WHERE s.libelle = :libelle", StatusRemboursement.class)
                .setParameter("libelle", libelle)
                .getResultList();
        return statuses.isEmpty() ? null : statuses.get(0);
    }
}
