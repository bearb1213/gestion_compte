package com.bank.courant.dao;

import com.bank.courant.model.Direction;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DirectionDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(Direction direction) {
        em.persist(direction);
    }

    // READ by id
    public Direction find(int id) {
        return em.find(Direction.class, id);
    }

    // READ ALL
    public List<Direction> findAll() {
        return em.createQuery("SELECT d FROM Direction d", Direction.class)
                 .getResultList();
    }

    // UPDATE
    public Direction update(Direction direction) {
        return em.merge(direction);
    }

    // DELETE
    public void delete(int id) {
        Direction direction = em.find(Direction.class, id);
        if (direction != null) {
            em.remove(direction);
        }
    }

    // READ by libelle
    public Direction findByLibelle(String libelle) {
        List<Direction> directions = em.createQuery(
                "SELECT d FROM Direction d WHERE d.libelle = :libelle", Direction.class)
                .setParameter("libelle", libelle)
                .getResultList();
        return directions.isEmpty() ? null : directions.get(0);
    }

    // FIND by niveau
    public List<Direction> findByNiveau(int niveau) {
        return em.createQuery(
                "SELECT d FROM Direction d WHERE d.niveau = :niveau", Direction.class)
                .setParameter("niveau", niveau)
                .getResultList();
    }

    // FIND by role
    public List<Direction> findByRole(int role) {
        return em.createQuery(
                "SELECT d FROM Direction d WHERE d.role = :role", Direction.class)
                .setParameter("role", role)
                .getResultList();
    }

    // FIND by niveau and role
    public List<Direction> findByNiveauAndRole(int niveau, int role) {
        return em.createQuery(
                "SELECT d FROM Direction d WHERE d.niveau = :niveau AND d.role = :role", Direction.class)
                .setParameter("niveau", niveau)
                .setParameter("role", role)
                .getResultList();
    }

    // CHECK if exists by libelle
    public boolean existsByLibelle(String libelle) {
        Long count = em.createQuery(
                "SELECT COUNT(d) FROM Direction d WHERE d.libelle = :libelle", Long.class)
                .setParameter("libelle", libelle)
                .getSingleResult();
        return count > 0;
    }

    // COUNT by niveau
    public long countByNiveau(int niveau) {
        return em.createQuery(
                "SELECT COUNT(d) FROM Direction d WHERE d.niveau = :niveau", Long.class)
                .setParameter("niveau", niveau)
                .getSingleResult();
    }
}