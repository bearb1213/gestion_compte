package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "particulie")
@Data                   // Génère getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Génère un constructeur vide
@AllArgsConstructor     // Génère un constructeur complet
@Builder                // Permet la construction fluide (Particulie.builder()...)
public class Particulie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 50)
    private String cin;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(length = 50)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(length = 50)
    private String adresse;

    @Column(length = 50)
    private String telephone;

    @Column(unique = true, length = 50)
    private String email;
}
