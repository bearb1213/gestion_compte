package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "change")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Change implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String libelle;

    // // 🔹 Relation inverse vers HistoChange
    // @OneToMany(mappedBy = "change", cascade = CascadeType.ALL)
    // private List<HistoChange> histoChanges;

    
    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static Change findById(int id,EntityManager em){
        return em.find(Change.class, id);
    }

    public void delete(EntityManager em){
        Change relited = em.merge(this);
        em.remove(relited);
    }

    public static List<Change> findAll(EntityManager em){ 
        return em.createQuery("SELECT c FROM Change c", Change.class).getResultList();
    }

}
