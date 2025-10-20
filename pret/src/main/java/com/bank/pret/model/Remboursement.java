package com.bank.pret.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Représente un remboursement de prêt.
 * <p>
 * Champs :
 * <ul>
 *   <li>id : identifiant unique du remboursement</li>
 *   <li>datePrevue : date prévue pour le remboursement</li>
 *   <li>datePayement : date effective du paiement</li>
 *   <li>montantTotal : montant total du remboursement</li>
 *   <li>montantCapital : montant du capital remboursé</li>
 *   <li>montantInteret : montant des intérêts payés</li>
 *   <li>montantAssurance : montant de l'assurance</li>
 *   <li>statusRemboursement : statut du remboursement</li>
 *   <li>pret : prêt associé au remboursement</li>
 * </ul>
 */
@Entity
@Table(name = "remboursement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Remboursement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_prevue", nullable = false)
    private LocalDate datePrevue;

    @Column(name = "date_payement")
    private LocalDate datePayement;

    @Column(name = "montant_total", nullable = false, precision = 15)
    private Double montantTotal;

    @Column(name = "montant_capital", nullable = false, precision = 15)
    private Double montantCapital;

    @Column(name = "montant_interet", nullable = false, precision = 15)
    private Double montantInteret;

    @Column(name = "montant_assurance", precision = 15)
    private Double montantAssurance;

    // 🔹 Relation vers StatusRemboursement
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status", nullable = false)
    private StatusRemboursement statusRemboursement;

    // 🔹 Relation vers Pret
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pret", nullable = false)
    private Pret pret;
}