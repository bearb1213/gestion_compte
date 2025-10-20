package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "revenu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Revenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, precision = 15)
    private Double montant;

    @Column(name = "date_changement", nullable = false)
    private LocalDate dateChangement;

    // 🔹 Relation vers TypeRevenu
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_type", nullable = false)
    private TypeRevenu typeRevenu;

    // 🔹 Relation vers Particulie
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_particulier", nullable = false)
    private Particulie particulier;
}
