package com.bank.courant.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compte", nullable = false)
    private Compte compte;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;
}
