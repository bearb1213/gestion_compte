package com.bank.courant.dao;

import com.bank.courant.model.Transaction;
import com.bank.courant.model.Compte;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Stateless
public class TransactionDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(Transaction transaction) {
        em.persist(transaction);
    }

    // READ by id
    public Transaction find(int id) {
        return em.find(Transaction.class, id);
    }

    // READ ALL
    public List<Transaction> findAll() {
        return em.createQuery("SELECT t FROM Transaction t", Transaction.class)
                 .getResultList();
    }

    // UPDATE
    public Transaction update(Transaction transaction) {
        return em.merge(transaction);
    }

    // DELETE
    public void delete(int id) {
        Transaction transaction = em.find(Transaction.class, id);
        if (transaction != null) {
            em.remove(transaction);
        }
    }

    // READ ALL BY COMPTE
    public List<Transaction> findByCompte(Compte compte) {
        return em.createQuery(
                "SELECT t FROM Transaction t WHERE t.compte = :compte ORDER BY t.dateTransaction DESC",
                Transaction.class)
                .setParameter("compte", compte)
                .getResultList();
    }

    // READ ALL BY MOUVEMENT ('D' ou 'C')
    public List<Transaction> findByMouvement(String mouvement) {
        return em.createQuery(
                "SELECT t FROM Transaction t WHERE t.mouvement = :mouvement ORDER BY t.dateTransaction DESC",
                Transaction.class)
                .setParameter("mouvement", mouvement)
                .getResultList();
    }

    public List<Transaction> findByCompteAfterDate(Compte compte, LocalDateTime date) {
        return em.createQuery(
                "SELECT t FROM Transaction t WHERE t.compte = :compte AND t.dateTransaction > :date ORDER BY t.dateTransaction DESC",
                Transaction.class)
                .setParameter("compte", compte)
                .setParameter("date", date)
                .getResultList();
    }

    public List<Transaction> findByCompteBeforeDate(Compte compte, LocalDateTime date) {
        return em.createQuery(
                "SELECT t FROM Transaction t WHERE t.compte = :compte AND t.dateTransaction < :date ORDER BY t.dateTransaction DESC",
                Transaction.class)
                .setParameter("compte", compte)
                .setParameter("date", date)
                .getResultList();
    }
    
    public Optional<Transaction> findLatestByCompte(Compte compte) {
        List<Transaction> results = em.createQuery(
                "SELECT t FROM Transaction t WHERE t.compte = :compte ORDER BY t.dateTransaction DESC",
                Transaction.class)
                .setParameter("compte", compte)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<Transaction> findLatestByCompteBeforeDate(Compte compte, LocalDateTime date) {
        List<Transaction> results = em.createQuery(
                "SELECT t FROM Transaction t WHERE t.compte = :compte AND t.dateTransaction < :date ORDER BY t.dateTransaction DESC",
                Transaction.class)
                .setParameter("compte", compte)
                .setParameter("date", date)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }




}
