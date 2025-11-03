package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import com.bank.courant.util.JpaUtil;

@Entity
@Table(name = "action_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom_table", nullable = false, length = 50)
    private String nomTable;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "role", nullable = false)
    private int role;

    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static ActionRole findById(int id,EntityManager em){
        return em.find(ActionRole.class, id);
    }

    public void delete(EntityManager em){
        JpaUtil jpaUtil = new JpaUtil();
        ActionRole relited = em.merge(this);
        em.remove(relited);
    }

    public static List<ActionRole> findAll(EntityManager em){
        return em.createQuery("SELECT ar FROM ActionRole ar", ActionRole.class).getResultList();
    }


}