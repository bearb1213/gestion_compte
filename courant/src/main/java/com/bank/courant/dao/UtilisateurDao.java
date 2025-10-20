package com.bank.courant.dao;

import com.bank.courant.model.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UtilisateurDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }

    // READ by id
    public Utilisateur find(int id) {
        return em.find(Utilisateur.class, id);
    }

    // READ ALL
    public List<Utilisateur> findAll() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class)
                 .getResultList();
    }

    // UPDATE
    public Utilisateur update(Utilisateur utilisateur) {
        return em.merge(utilisateur);
    }

    // DELETE
    public void delete(int id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    // READ by username
    public Utilisateur findByUsername(String username) {
        List<Utilisateur> utilisateurs = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.username = :username", Utilisateur.class)
                .setParameter("username", username)
                .getResultList();
        return utilisateurs.isEmpty() ? null : utilisateurs.get(0);
    }

    // FIND by id_direction
    public List<Utilisateur> findByIdDirection(int idDirection) {
        return em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.direction.id = :idDirection", Utilisateur.class)
                .setParameter("idDirection", idDirection)
                .getResultList();
    }

    // FIND by direction libelle
    public List<Utilisateur> findByDirectionLibelle(String libelle) {
        return em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.direction.libelle = :libelle", Utilisateur.class)
                .setParameter("libelle", libelle)
                .getResultList();
    }

    // CHECK if username exists
    public boolean existsByUsername(String username) {
        Long count = em.createQuery(
                "SELECT COUNT(u) FROM Utilisateur u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    // COUNT by id_direction
    public long countByIdDirection(int idDirection) {
        return em.createQuery(
                "SELECT COUNT(u) FROM Utilisateur u WHERE u.direction.id = :idDirection", Long.class)
                .setParameter("idDirection", idDirection)
                .getSingleResult();
    }

    // FIND ALL with direction (eager loading)
    public List<Utilisateur> findAllWithDirection() {
        return em.createQuery(
                "SELECT u FROM Utilisateur u JOIN FETCH u.direction", Utilisateur.class)
                .getResultList();
    }

    // FIND by username and password (for authentication)
    public Utilisateur findByUsernameAndPassword(String username, String password) {
        List<Utilisateur> utilisateurs = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.username = :username AND u.password = :password", Utilisateur.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
        return utilisateurs.isEmpty() ? null : utilisateurs.get(0);
    }
}