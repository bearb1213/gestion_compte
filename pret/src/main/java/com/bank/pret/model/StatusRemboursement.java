package com.bank.pret.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Représente un statut pour les remboursements.
 * <p>
 * Champs :
 * <ul>
 *   <li>id : identifiant unique du statut</li>
 *   <li>libelle : libellé du statut</li>
 * </ul>
 */
@Entity
@Table(name = "status_remboursement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusRemboursement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String libelle;
}