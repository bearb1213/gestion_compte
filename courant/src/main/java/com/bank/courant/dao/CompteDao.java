package com.bank.courant.dao;

import com.bank.courant.model.Compte;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CompteDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(Compte compte) {
        em.persist(compte);
    }

    // READ by id
    public Compte find(int id) {
        return em.find(Compte.class, id);
    }

    // READ ALL
    public List<Compte> findAll() {
        return em.createQuery("SELECT c FROM Compte c", Compte.class)
                 .getResultList();
    }

    // UPDATE
    public Compte update(Compte compte) {
        return em.merge(compte);
    }

    // DELETE
    public void delete(int id) {
        Compte compte = em.find(Compte.class, id);
        if (compte != null) {
            em.remove(compte);
        }
    }

    // READ by numero_compte
    public Compte findByNumero(String numero) {
        List<Compte> comptes = em.createQuery(
                "SELECT c FROM Compte c WHERE c.numeroCompte = :numero", Compte.class)
                .setParameter("numero", numero)
                .getResultList();
        return comptes.isEmpty() ? null : comptes.get(0);
    }
}
