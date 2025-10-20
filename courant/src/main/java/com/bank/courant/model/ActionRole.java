package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "action_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom_table", nullable = false, length = 50)
    private String nomTable;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "role", nullable = false)
    private int role;
}