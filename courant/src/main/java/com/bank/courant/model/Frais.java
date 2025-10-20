package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "frais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Frais implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement;

    @Column(name = "tenu_compte", nullable = false, precision = 15)
    private Double tenuCompte;

    @Column(name = "decouvert", precision = 15)
    private Double decouvert;

    // 🔹 Relation vers Compte
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compte", nullable = false)
    private Compte compte;
}
