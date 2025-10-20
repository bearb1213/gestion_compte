package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "type_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String libelle;
}
