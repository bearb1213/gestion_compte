package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "histo_change")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoChange implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(precision = 15, scale = 2, nullable = false)
    private Double montant;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement;

    // 🔹 Relation vers Change
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_change", nullable = false)
    private Change change;

    // 🔹 Relation inverse vers Transaction
    @OneToMany(mappedBy = "histoChange", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

      
    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static HistoChange findById(int id,EntityManager em){
        return em.find(HistoChange.class, id);
    }

    public void delete(EntityManager em){
        HistoChange relited = em.merge(this);
        em.remove(relited);
    }

    public static List<HistoChange> findAll(EntityManager em){ 
        return em.createQuery("SELECT c FROM HistoChange c", HistoChange.class).getResultList();
    }


}
