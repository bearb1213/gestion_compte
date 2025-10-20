package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "direction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    @Column(name = "niveau", nullable = false)
    private int niveau;

    @Column(name = "role", nullable = false)
    private int role;
}   