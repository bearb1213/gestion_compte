package com.bank.pret.dao;

import com.bank.pret.model.Type;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TypeDao {

    @PersistenceContext(unitName = "PretPU")
    private EntityManager em;

    public void create(Type type) {
        em.persist(type);
    }

    public Type find(int id) {
        return em.find(Type.class, id);
    }

    public List<Type> findAll() {
        return em.createQuery("SELECT t FROM Type t", Type.class)
                 .getResultList();
    }

    public void update(Type type) {
        em.merge(type);
    }

    public void delete(int id) {
        Type t = em.find(Type.class, id);
        if (t != null) {
            em.remove(t);
        }
    }
    public Type findByLibelle(String libelle) {
        List<Type> types = em.createQuery(
                "SELECT s FROM Type s WHERE s.libelle = :libelle", Type.class)
                .setParameter("libelle", libelle)
                .getResultList();
        return types.isEmpty() ? null : types.get(0);
    }
}
