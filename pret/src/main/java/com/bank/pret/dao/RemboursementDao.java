package com.bank.pret.dao;

import com.bank.pret.model.Remboursement;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import com.bank.pret.model.Pret;

@Stateless
public class RemboursementDao {

    @PersistenceContext(unitName = "PretPU")
    private EntityManager em;

    public void create(Remboursement remboursement) {
        em.persist(remboursement);
    }

    public Remboursement find(int id) {
        return em.find(Remboursement.class, id);
    }

    public List<Remboursement> findAll() {
        return em.createQuery("SELECT r FROM Remboursement r", Remboursement.class)
                 .getResultList();
    }

    public List<Remboursement> findByPretId(int pretId) {
        return em.createQuery("SELECT r FROM Remboursement r WHERE r.pret.id = :pretId", Remboursement.class)
                 .setParameter("pretId", pretId)
                 .getResultList();
    }
    public List<Remboursement> findByPretId(Pret pret) {
        return em.createQuery("SELECT r FROM Remboursement r WHERE r.pret = :pret", Remboursement.class)
                 .setParameter("pret", pret)
                 .getResultList();
    }

    public void update(Remboursement remboursement) {
        em.merge(remboursement);
    }

    public void delete(int id) {
        Remboursement r = em.find(Remboursement.class, id);
        if (r != null) {
            em.remove(r);
        }
    }

    public List<Remboursement> findNonPaidRemboursements(Pret pret){
        return em.createQuery("SELECT r FROM Remboursement r WHERE r.pret = :pret AND r.statusRemboursement.libelle != 'Payer'", Remboursement.class)
                 .setParameter("pret", pret)
                 .getResultList();
    }
}
