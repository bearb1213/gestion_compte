package com.bank.courant.dao;

import com.bank.courant.model.ActionRole;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ActionRoleDao {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;

    // CREATE
    public void create(ActionRole actionRole) {
        em.persist(actionRole);
    }

    // READ by id
    public ActionRole find(int id) {
        return em.find(ActionRole.class, id);
    }

    // READ ALL
    public List<ActionRole> findAll() {
        return em.createQuery("SELECT a FROM ActionRole a", ActionRole.class)
                 .getResultList();
    }

    // UPDATE
    public ActionRole update(ActionRole actionRole) {
        return em.merge(actionRole);
    }

    // DELETE
    public void delete(int id) {
        ActionRole actionRole = em.find(ActionRole.class, id);
        if (actionRole != null) {
            em.remove(actionRole);
        }
    }

    // READ by nom_table and action
    public ActionRole findByNomTableAndAction(String nomTable, String action) {
        List<ActionRole> actionRoles = em.createQuery(
                "SELECT a FROM ActionRole a WHERE a.nomTable = :nomTable AND a.action = :action", ActionRole.class)
                .setParameter("nomTable", nomTable)
                .setParameter("action", action)
                .getResultList();
        return actionRoles.isEmpty() ? null : actionRoles.get(0);
    }

    // FIND by nom_table
    public List<ActionRole> findByNomTable(String nomTable) {
        return em.createQuery(
                "SELECT a FROM ActionRole a WHERE a.nomTable = :nomTable", ActionRole.class)
                .setParameter("nomTable", nomTable)
                .getResultList();
    }

    // FIND by action
    public List<ActionRole> findByAction(String action) {
        return em.createQuery(
                "SELECT a FROM ActionRole a WHERE a.action = :action", ActionRole.class)
                .setParameter("action", action)
                .getResultList();
    }

    // FIND by role
    public List<ActionRole> findByRole(int role) {
        return em.createQuery(
                "SELECT a FROM ActionRole a WHERE a.role = :role", ActionRole.class)
                .setParameter("role", role)
                .getResultList();
    }

    // FIND by nom_table and role
    public List<ActionRole> findByNomTableAndRole(String nomTable, int role) {
        return em.createQuery(
                "SELECT a FROM ActionRole a WHERE a.nomTable = :nomTable AND a.role = :role", ActionRole.class)
                .setParameter("nomTable", nomTable)
                .setParameter("role", role)
                .getResultList();
    }

    // CHECK if exists by nom_table and action and role
    public boolean existsByNomTableAndActionAndRole(String nomTable, String action, int role) {
        Long count = em.createQuery(
                "SELECT COUNT(a) FROM ActionRole a WHERE a.nomTable = :nomTable AND a.action = :action AND a.role = :role", Long.class)
                .setParameter("nomTable", nomTable)
                .setParameter("action", action)
                .setParameter("role", role)
                .getSingleResult();
        return count > 0;
    }

    // COUNT by nom_table
    public long countByNomTable(String nomTable) {
        return em.createQuery(
                "SELECT COUNT(a) FROM ActionRole a WHERE a.nomTable = :nomTable", Long.class)
                .setParameter("nomTable", nomTable)
                .getSingleResult();
    }
}