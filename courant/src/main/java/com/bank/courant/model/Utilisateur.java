package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import com.bank.courant.util.JpaUtil;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    // 🔹 Relation vers Direction
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_direction", nullable = false)
    private Direction direction;

    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static Utilisateur findById(int id,EntityManager em){
        return em.find(Utilisateur.class, id);
    }

    public void delete(EntityManager em){
        Utilisateur relited = em.merge(this);
        em.remove(relited);
    }

    public static List<Utilisateur> findAll(EntityManager em){
        return em.createQuery("SELECT ar FROM Utilisateur ar", Utilisateur.class).getResultList();
    }

    public Utilisateur authenticate(EntityManager em) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.username = :username AND u.password = :password", Utilisateur.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; 
        }
    }



}