package com.bank.courant.dao;

import com.bank.courant.model.TypeRevenu;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TypeRevenuDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(TypeRevenu typeRevenu) {
        em.persist(typeRevenu);
    }

    // READ by id
    public TypeRevenu find(int id) {
        return em.find(TypeRevenu.class, id);
    }

    // READ ALL
    public List<TypeRevenu> findAll() {
        return em.createQuery("SELECT t FROM TypeRevenu t", TypeRevenu.class)
                 .getResultList();
    }

    // UPDATE
    public TypeRevenu update(TypeRevenu typeRevenu) {
        return em.merge(typeRevenu);
    }

    // DELETE
    public void delete(int id) {
        TypeRevenu typeRevenu = em.find(TypeRevenu.class, id);
        if (typeRevenu != null) {
            em.remove(typeRevenu);
        }
    }

    // READ by libelle
    public TypeRevenu findByLibelle(String libelle) {
        List<TypeRevenu> result = em.createQuery(
                "SELECT t FROM TypeRevenu t WHERE t.libelle = :libelle", TypeRevenu.class)
                .setParameter("libelle", libelle)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
