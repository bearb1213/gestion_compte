package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 🔹 Compte origine (obligatoire)
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compte_origine", nullable = false)
    private Compte compteOrigine;

    // 🔹 Compte d’arrivée (facultatif)
    @ManyToOne
    @JoinColumn(name = "id_compte_arrive")
    private Compte compteArrive;

    @Column(name = "date_effectif", nullable = false)
    private LocalDate dateEffectif;

    @Column(name = "montant_ar", precision = 15, scale = 2, nullable = false)
    private Double montantAr;

    @Column(name = "montant_entrer", precision = 15, scale = 2, nullable = false)
    private Double montantEntrer;

    // 🔹 Type de transaction
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_type_transaction", nullable = false)
    private TypeTransaction typeTransaction;

    // 🔹 Change utilisé
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_change", nullable = false)
    private HistoChange histoChange;

      
    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static Transaction findById(int id,EntityManager em){
        return em.find(Transaction.class, id);
    }

    public void delete(EntityManager em){
        Transaction relited = em.merge(this);
        em.remove(relited);
    }

    public static List<Transaction> findAll(EntityManager em){ 
        return em.createQuery("SELECT c FROM Transaction c", Transaction.class).getResultList();
    }

}
