package com.bank.pret.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Représente un type de prêt.
 * <p>
 * Champs :
 * <ul>
 *   <li>id : identifiant unique du type</li>
 *   <li>libelle : libellé du type de prêt</li>
 * </ul>
 */
@Entity
@Table(name = "type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Type implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String libelle;
}