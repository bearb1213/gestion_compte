package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Column(nullable = false, precision = 15)
    private Double montant;

    @Column(name = "date_transaction", nullable = false)
    private LocalDateTime dateTransaction;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 1)
    private String mouvement; // "D" pour dépôt, "R" pour retrait, etc.
  
    @Column(name = "montant_actuel", nullable = false, precision = 18)
    private Double montantActuel;

    // 🔹 Relation vers MoyenPaiement (optionnel)
    @ManyToOne
    @JoinColumn(name = "id_moyen")
    private MoyenPaiement moyenPaiement;

    // 🔹 Relation vers TypeTransaction (optionnel)
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_type_transaction")
    private TypeTransaction typeTransaction;

    // 🔹 Relation vers Compte (obligatoire)
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compte", nullable = false)
    private Compte compte;
}
