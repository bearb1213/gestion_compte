package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.bank.courant.util.JpaUtil;

@Entity
@Table(name = "histo_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compte", nullable = false)
    private Compte compte;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status", nullable = false)
    private StatusCompte status;

    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static HistoStatus findById(int id,EntityManager em){
        return em.find(HistoStatus.class, id);
    }

    public void delete(EntityManager em){
        HistoStatus relited = em.merge(this);
        em.remove(relited);
    }

    public static List<HistoStatus> findAll(EntityManager em){
        return em.createQuery("SELECT hs FROM HistoStatus hs", HistoStatus.class).getResultList();
    }

}
