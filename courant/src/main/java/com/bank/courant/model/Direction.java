package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import com.bank.courant.util.JpaUtil;

@Entity
@Table(name = "direction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    @Column(name = "niveau", nullable = false)
    private int niveau;

    @Column(name = "role", nullable = false)
    private int role;

    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static Direction findById(int id,EntityManager em){
        return em.find(Direction.class, id);
    }

    public void delete(EntityManager em){
        Direction relited = em.merge(this);
        em.remove(relited);
    }

    public static List<Direction> findAll(EntityManager em){
        return em.createQuery("SELECT d FROM Direction d", Direction.class).getResultList();
    }
}   