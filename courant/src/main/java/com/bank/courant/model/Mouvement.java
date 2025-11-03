package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "mouvement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mouvement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(precision = 15, scale = 2, nullable = false)
    private Double montant;

    // 🔹 Relation vers Compte
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compte", nullable = false)
    private Compte compte;

    @Column(length = 1, nullable = false)
    private String mvnt;  // Exemple : 'C' (crédit) ou 'D' (débit)

    @Lob
    private String description;

      
    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static Mouvement findById(int id,EntityManager em){
        return em.find(Mouvement.class, id);
    }

    public void delete(EntityManager em){
        Mouvement relited = em.merge(this);
        em.remove(relited);
    }

    public static List<Mouvement> findAll(EntityManager em){ 
        return em.createQuery("SELECT c FROM Mouvement c", Mouvement.class).getResultList();
    }


}
