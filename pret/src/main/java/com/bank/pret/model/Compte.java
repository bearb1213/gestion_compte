package com.bank.pret.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Représente un compte bancaire de prêt.
 * <p>
 * Champs :
 * <ul>
 *   <li>id : identifiant unique du compte</li>
 *   <li>numero : numéro du compte bancaire</li>
 *   <li>dateOuverture : date d'ouverture du compte</li>
 *   <li>idParticulier : identifiant du propriétaire du compte</li>
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

    @Column(nullable = false, unique = true, length = 50)
    private String numero;

    @Column(name = "date_ouverture", nullable = false)
    private LocalDate dateOuverture;

    @Column(name = "id_particulier", nullable = false)
    private int idParticulier;
}