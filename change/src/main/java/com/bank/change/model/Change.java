package com.bank.change.model;

import java.time.LocalDate;

import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Change implements Serializable {
    
    private int id;
    private String libelle;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double tauxChange;

    public String toJson() {
        return "{" +
                "\"id\":" + id + "," +
                "\"libelle\":\"" + libelle + "\"," +
                "\"dateDebut\":\"" + dateDebut + "\"," +
                "\"dateFin\":\"" + dateFin + "\"," +
                "\"tauxChange\":" + tauxChange +
                "}";
    }
    public String toString() {
        return ""+ id + ";" + libelle + ";" + dateDebut +
               ";" + dateFin + ";" + tauxChange + ";";
    }
}
