package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.bank.courant.util.JpaUtil;

@Entity
@Table(name = "particulier")
@Data                  
@NoArgsConstructor      
@AllArgsConstructor     
@Builder                
public class Particulier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 50)
    private String cin;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(length = 50)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(length = 50)
    private String adresse;

    @Column(length = 50)
    private String telephone;

    @Column(unique = true, length = 50)
    private String email;

    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static Particulier findById(int id,EntityManager em){
        return em.find(Particulier.class, id);
    }

    public void delete(EntityManager em){
        Particulier relited = em.merge(this);
        em.remove(relited);
    }

    public static List<Particulier> findAll(EntityManager em){
        return em.createQuery("SELECT p FROM Particulier p", Particulier.class).getResultList();
    }

}
