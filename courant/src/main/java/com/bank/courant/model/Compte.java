package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Représente un compte bancaire courant.
 * <p>
 * Champs :
 * <ul>
 *   <li>id : identifiant unique du compte</li>
 *   <li>numeroCompte : numéro du compte bancaire</li>
 *   <li>dateOuverture : date d'ouverture du compte</li>
 *   <li>montantDecouvert : montant autorisé du découvert</li>
 *   <li>particulier : propriétaire du compte</li>
 * </ul>
 */
@Entity
@Table(name = "compte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "numero_compte", nullable = false, unique = true, length = 50)
    private String numeroCompte;

    @Column(name = "date_ouverture", nullable = false)
    private LocalDate dateOuverture;

    @Column(name = "montant_decouvert", precision = 15)
    private Double montantDecouvert;

    // 🔹 Relation vers Particulie
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_particulier", nullable = false)
    private Particulie particulier;

}
