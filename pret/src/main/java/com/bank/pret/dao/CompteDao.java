package com.bank.pret.dao;

import com.bank.pret.model.Compte;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CompteDao {

    @PersistenceContext(unitName = "PretPU")
    private EntityManager em;

    public void create(Compte compte) {
        em.persist(compte);
    }

    public Compte find(int id) {
        return em.find(Compte.class, id);
    }

    public List<Compte> findAll() {
        return em.createQuery("SELECT c FROM Compte c", Compte.class)
                 .getResultList();
    }

    public void update(Compte compte) {
        em.merge(compte);
    }

    public void delete(int id) {
        Compte c = em.find(Compte.class, id);
        if (c != null) {
            em.remove(c);
        }
    }

    // READ by numero_compte
    public Compte findByNumero(String numero) {
        List<Compte> comptes = em.createQuery(
                "SELECT c FROM Compte c WHERE c.numero = :numero", Compte.class)
                .setParameter("numero", numero)
                .getResultList();
        return comptes.isEmpty() ? null : comptes.get(0);
    }


}
