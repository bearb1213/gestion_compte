package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import com.bank.courant.util.JpaUtil;

@Entity
@Table(name = "status_compte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusCompte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String libelle;

    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static StatusCompte findById(int id, EntityManager em){
        return em.find(StatusCompte.class, id);
    }

    public void delete(EntityManager em){
        StatusCompte relited = em.merge(this);
        em.remove(relited);
    }

    public static List<StatusCompte> findAll(EntityManager em){
        return em.createQuery("SELECT ar FROM StatusCompte ar", StatusCompte.class).getResultList();
    }
}
