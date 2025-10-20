package com.bank.courant.dao;

import com.bank.courant.model.TypeTransaction;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TypeTransactionDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(TypeTransaction typeTransaction) {
        em.persist(typeTransaction);
    }

    // READ by id
    public TypeTransaction find(int id) {
        return em.find(TypeTransaction.class, id);
    }

    // READ ALL
    public List<TypeTransaction> findAll() {
        return em.createQuery("SELECT t FROM TypeTransaction t", TypeTransaction.class)
                 .getResultList();
    }

    // UPDATE
    public TypeTransaction update(TypeTransaction typeTransaction) {
        return em.merge(typeTransaction);
    }

    // DELETE
    public void delete(int id) {
        TypeTransaction typeTransaction = em.find(TypeTransaction.class, id);
        if (typeTransaction != null) {
            em.remove(typeTransaction);
        }
    }

    // READ by libelle
    public TypeTransaction findByLibelle(String libelle) {
        List<TypeTransaction> result = em.createQuery(
                "SELECT t FROM TypeTransaction t WHERE t.libelle = :libelle", TypeTransaction.class)
                .setParameter("libelle", libelle)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
