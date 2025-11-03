package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "type_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String libelle;

    // // 🔹 Relation inverse vers Transaction
    // @OneToMany(mappedBy = "typeTransaction", cascade = CascadeType.ALL)
    // private List<Transaction> transactions;

      
    public void save(EntityManager em){
        if(id==0){
            em.persist(this);
        } else {
            em.merge(this);
        }
    }

    public static TypeTransaction findById(int id,EntityManager em){
        return em.find(TypeTransaction.class, id);
    }

    public void delete(EntityManager em){
        TypeTransaction relited = em.merge(this);
        em.remove(relited);
    }

    public static List<TypeTransaction> findAll(EntityManager em){ 
        return em.createQuery("SELECT c FROM TypeTransaction c", TypeTransaction.class).getResultList();
    }

}
