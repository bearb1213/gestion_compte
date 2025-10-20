package com.bank.pret.dao;

import com.bank.pret.model.HistoStatus;
import com.bank.pret.model.Compte;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class HistoStatusDao {

    @PersistenceContext(unitName = "PretPU")
    private EntityManager em;

    // CREATE
    public void create(HistoStatus histoStatus) {
        em.persist(histoStatus);
    }

    // READ by id
    public HistoStatus find(int id) {
        return em.find(HistoStatus.class, id);
    }

    // READ ALL
    public List<HistoStatus> findAll() {
        return em.createQuery("SELECT h FROM HistoStatus h", HistoStatus.class)
                 .getResultList();
    }

    // UPDATE
    public HistoStatus update(HistoStatus histoStatus) {
        return em.merge(histoStatus);
    }

    // DELETE
    public void delete(int id) {
        HistoStatus histoStatus = em.find(HistoStatus.class, id);
        if (histoStatus != null) {
            em.remove(histoStatus);
        }
    }

    // READ ALL BY COMPTE
    public List<HistoStatus> findByCompte(Compte compte) {
        return em.createQuery(
                "SELECT h FROM HistoStatus h WHERE h.compte = :compte ORDER BY h.dateChangement DESC",
                HistoStatus.class)
                .setParameter("compte", compte)
                .getResultList();
    }

    // GET LAST HISTO STATUS BY COMPTE
    public HistoStatus getLastHistoStatus(Compte compte) {
        List<HistoStatus> results = em.createQuery(
                "SELECT h FROM HistoStatus h WHERE h.compte = :compte ORDER BY h.dateChangement DESC",
                HistoStatus.class)
                .setParameter("compte", compte)
                .setMaxResults(1) // Limite à 1 résultat
                .getResultList();
        
        return results.isEmpty() ? null : results.get(0);
    }
}