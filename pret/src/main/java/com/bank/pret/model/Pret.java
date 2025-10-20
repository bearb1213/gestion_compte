package com.bank.pret.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Représente un prêt bancaire.
 * <p>
 * Champs :
 * <ul>
 *   <li>id : identifiant unique du prêt</li>
 *   <li>montant : montant du prêt</li>
 *   <li>dateCommencement : date de début du prêt</li>
 *   <li>taux : taux d'intérêt du prêt</li>
 *   <li>dureeMois : durée du prêt en mois</li>
 *   <li>type : type de prêt</li>
 *   <li>compte : compte associé au prêt</li>
 * </ul>
 */
@Entity
@Table(name = "pret")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pret implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, precision = 18)
    private Double montant;

    @Column(name = "date_commencement", nullable = false)
    private LocalDate dateCommencement;

    @Column(nullable = false, precision = 5)
    private Double taux;

    @Column(name = "duree_mois")
    private Integer dureeMois;

    // 🔹 Relation vers Type
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_type", nullable = false)
    private Type type;

    // 🔹 Relation vers Compte
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compte", nullable = false)
    private Compte compte;
}