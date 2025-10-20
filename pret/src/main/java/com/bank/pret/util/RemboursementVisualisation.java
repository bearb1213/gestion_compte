package com.bank.pret.util;

import java.time.LocalDate;
import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemboursementVisualisation implements Serializable {
    private int numeroEcheance;
    private LocalDate datePrevue;
    private Double montantTotal;
    private Double montantCapital;
    private Double montantInteret;
    private Double montantAssurance;
}
