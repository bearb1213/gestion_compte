package com.bank.pret.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Représente l'historique des changements de statut d'un remboursement.
 * <p>
 * Champs :
 * <ul>
 *   <li>id : identifiant unique de l'historique</li>
 *   <li>datePaiement : date du paiement</li>
 *   <li>montant : montant du paiement</li>
 *   <li>remboursement : remboursement concerné par le changement</li>
 *   <li>statusRemboursement : statut du remboursement appliqué</li>
 * </ul>
 */
@Entity
@Table(name = "histo_status_remboursement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoStatusRemboursement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_paiment", nullable = false)
    private LocalDate datePaiement;

    @Column(nullable = false, precision = 15)
    private Double montant;

    // 🔹 Relation vers Remboursement
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_remboursement", nullable = false)
    private Remboursement remboursement;

    // 🔹 Relation vers StatusRemboursement
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status", nullable = false)
    private StatusRemboursement statusRemboursement;
}