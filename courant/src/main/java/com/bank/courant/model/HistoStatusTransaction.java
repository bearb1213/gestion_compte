package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "histo_status_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoStatusTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 🔹 Relation vers Transaction
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_transaction", nullable = false)
    private Transaction transaction;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement;

    // 🔹 Relation vers StatusTransaction
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status", nullable = false)
    private StatusTransaction statusTransaction;

      
    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static HistoStatusTransaction findById(int id,EntityManager em){
        return em.find(HistoStatusTransaction.class, id);
    }

    public void delete(EntityManager em){
        HistoStatusTransaction relited = em.merge(this);
        em.remove(relited);
    }

    public static List<HistoStatusTransaction> findAll(EntityManager em){ 
        return em.createQuery("SELECT c FROM HistoStatusTransaction c", HistoStatusTransaction.class).getResultList();
    }

}
