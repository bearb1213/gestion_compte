package com.bank.pret.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Représente l'historique des changements de statut d'un compte.
 * <p>
 * Champs :
 * <ul>
 *   <li>id : identifiant unique de l'historique</li>
 *   <li>dateChangement : date et heure du changement de statut</li>
 *   <li>compte : compte concerné par le changement</li>
 *   <li>status : nouveau statut appliqué</li>
 * </ul>
 */
@Entity
@Table(name = "histo_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement;

    // 🔹 Relation vers Compte
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compte", nullable = false)
    private Compte compte;

    // 🔹 Relation vers Status
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;
}