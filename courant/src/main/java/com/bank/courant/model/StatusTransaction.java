package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "status_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String libelle;

    // 🔹 Relation inverse vers HistoStatusTransaction
    @OneToMany(mappedBy = "statusTransaction", cascade = CascadeType.ALL)
    private List<HistoStatusTransaction> histoStatusTransactions;

      
    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static StatusTransaction findById(int id,EntityManager em){
        return em.find(StatusTransaction.class, id);
    }

    public void delete(EntityManager em){
        StatusTransaction relited = em.merge(this);
        em.remove(relited);
    }

    public static List<StatusTransaction> findAll(EntityManager em){ 
        return em.createQuery("SELECT c FROM StatusTransaction c", StatusTransaction.class).getResultList();
    }

}
