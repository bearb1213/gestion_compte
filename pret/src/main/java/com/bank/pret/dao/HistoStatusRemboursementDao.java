package com.bank.pret.dao;

import com.bank.pret.model.HistoStatusRemboursement;
import com.bank.pret.model.Remboursement;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class HistoStatusRemboursementDao {

    @PersistenceContext(unitName = "PretPU")
    private EntityManager em;

    public void create(HistoStatusRemboursement histo) {
        em.persist(histo);
    }

    public HistoStatusRemboursement find(int id) {
        return em.find(HistoStatusRemboursement.class, id);
    }

    public List<HistoStatusRemboursement> findAll() {
        return em.createQuery("SELECT h FROM HistoStatusRemboursement h", HistoStatusRemboursement.class)
                 .getResultList();
    }

    public List<HistoStatusRemboursement> findByRemboursementId(int remboursementId) {
        return em.createQuery("SELECT h FROM HistoStatusRemboursement h WHERE h.remboursement.id = :id", HistoStatusRemboursement.class)
                 .setParameter("id", remboursementId)
                 .getResultList();
    }

    public Double sumMontantByRemboursementId(int remboursementId) {
        return em.createQuery("SELECT SUM(h.montant) FROM HistoStatusRemboursement h WHERE h.remboursement.id = :id", Double.class)
                 .setParameter("id", remboursementId)
                 .getSingleResult();
    }
    public Double sumMontantByRemboursement(Remboursement remboursement) {
        return em.createQuery("SELECT SUM(h.montant) FROM HistoStatusRemboursement h WHERE h.remboursement = :remboursement", Double.class)
                 .setParameter("remboursement", remboursement)
                 .getSingleResult();
    }


    public void update(HistoStatusRemboursement histo) {
        em.merge(histo);
    }

    public void delete(int id) {
        HistoStatusRemboursement h = em.find(HistoStatusRemboursement.class, id);
        if (h != null) {
            em.remove(h);
        }
    }
}
