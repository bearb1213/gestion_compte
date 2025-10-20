package com.bank.pret.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RemboursementCalculator implements Serializable {
    
    public static List<RemboursementVisualisation> calculateSchedule(Double amount, int durationInMonths, Double interestRate) {
        return calculateSchedule(amount, durationInMonths, interestRate, 0.0);
    }

    public static List<RemboursementVisualisation> calculateSchedule(Double amount, int durationInMonths, Double interestRate, Double insuranceRate) {
        List<RemboursementVisualisation> schedule = new ArrayList<>();
        double monthlyInterestRate = interestRate / 12 / 100; // Conversion taux annuel en taux mensuel
        
        // Calcul de la mensualite constante
        double mensualite = calculerMensualiteConstante(amount, monthlyInterestRate, durationInMonths);
        
        double capitalRestant = amount;
        LocalDate dateDebut = LocalDate.now();
        
        for (int mois = 1; mois <= durationInMonths; mois++) {
            // Calcul des interets du mois
            double interets = capitalRestant * monthlyInterestRate;
            
            // Calcul du capital rembourse ce mois
            double capitalRembourse = mensualite - interets;
            
            // Calcul de l'assurance (sur capital initial)
            double assurance = amount * (insuranceRate / 100 / 12); // Taux d'assurance mensuel
            
            // Montant total de l'echeance
            double montantTotal = mensualite + assurance;
            
            // Ajustement pour la derniere echeance (arrondis)
            if (mois == durationInMonths) {
                capitalRembourse = capitalRestant;
                montantTotal = capitalRembourse + interets + assurance;
            }
            
            // Creation de l'echeance de visualisation
            RemboursementVisualisation echeance = new RemboursementVisualisation();
            echeance.setNumeroEcheance(mois);
            echeance.setDatePrevue(dateDebut.plusMonths(mois));
            echeance.setMontantTotal(round(montantTotal));
            echeance.setMontantCapital(round(capitalRembourse));
            echeance.setMontantInteret(round(interets));
            echeance.setMontantAssurance(round(assurance));
            
            schedule.add(echeance);
            
            // Mise à jour du capital restant
            capitalRestant -= capitalRembourse;
            
            // eviter les valeurs negatives dues aux arrondis
            if (capitalRestant < 0.01) {
                capitalRestant = 0.0;
            }
        }
        
        return schedule;
    }

    private static double calculerMensualiteConstante(double capital, double tauxMensuel, int dureeMois) {
        if (tauxMensuel == 0) {
            return capital / dureeMois;
        }
        
        double coefficient = tauxMensuel / (1 - Math.pow(1 + tauxMensuel, -dureeMois));
        double mensualite = capital * coefficient;
        
        return mensualite;
    }
    
    /**
     * Arrondit un double à 2 decimales
     */
    private static Double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
    
    /**
     * Version avec amortissement constant
     */
    public static List<RemboursementVisualisation> calculateScheduleAmortissementConstant(Double amount, int durationInMonths, Double interestRate) {
        return calculateScheduleAmortissementConstant(amount, durationInMonths, interestRate, 0.0);
    }
    
    public static List<RemboursementVisualisation> calculateScheduleAmortissementConstant(Double amount, int durationInMonths, Double interestRate, Double insuranceRate) {
        List<RemboursementVisualisation> schedule = new ArrayList<>();
        double monthlyInterestRate = interestRate / 12 / 100;
        
        double capitalMensuel = amount / durationInMonths;
        double capitalRestant = amount;
        LocalDate dateDebut = LocalDate.now();
        
        for (int mois = 1; mois <= durationInMonths; mois++) {
            // Calcul des interets du mois
            double interets = capitalRestant * monthlyInterestRate;
            
            // Calcul de l'assurance
            double assurance = amount * (insuranceRate / 100 / 12);
            
            // Montant total de l'echeance
            double montantTotal = capitalMensuel + interets + assurance;
            
            RemboursementVisualisation echeance = new RemboursementVisualisation();
            echeance.setNumeroEcheance(mois);
            echeance.setDatePrevue(dateDebut.plusMonths(mois));
            echeance.setMontantTotal(round(montantTotal));
            echeance.setMontantCapital(round(capitalMensuel));
            echeance.setMontantInteret(round(interets));
            echeance.setMontantAssurance(round(assurance));
            
            schedule.add(echeance);
            
            capitalRestant -= capitalMensuel;
        }
        
        return schedule;
    }
}
    

