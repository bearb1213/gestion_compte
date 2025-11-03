package com.bank.pret.remote;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;


import com.bank.pret.model.Compte;
import com.bank.pret.model.Pret;
import com.bank.pret.model.Remboursement;
import com.bank.pret.model.Status;
import com.bank.pret.model.StatusRemboursement;
import com.bank.pret.model.Type;
import com.bank.pret.model.HistoStatus;
import com.bank.pret.model.HistoStatusRemboursement;

import com.bank.pret.util.RemboursementVisualisation;
import com.bank.pret.util.RemboursementCalculator;
import com.bank.pret.util.NumCompteGenerator;

import com.bank.pret.dao.TypeDao;
import com.bank.pret.dao.CompteDao;
import com.bank.pret.dao.StatusDao;
import com.bank.pret.dao.HistoStatusDao;
import com.bank.pret.dao.RemboursementDao;
import com.bank.pret.dao.PretDao;
import com.bank.pret.dao.StatusRemboursementDao;
import com.bank.pret.dao.HistoStatusRemboursementDao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Stateless
public class PretService implements PretServiceRemote {

    @EJB
    private CompteDao compteDao;
    
    @EJB 
    private HistoStatusDao histoStatusDao;

    @EJB
    private StatusDao statusDao;

    @EJB
    private PretDao pretDao;

    @EJB
    private RemboursementDao remboursementDao;

    @EJB 
    private StatusRemboursementDao statusRemboursementDao;

    @EJB 
    private HistoStatusRemboursementDao histoStatusRemboursementDao;

    @EJB 
    private TypeDao typeDao;

    @Override
    public Compte createLoanAccount(int idParticulier) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        
        Compte compte = new Compte();
        compte.setNumero(NumCompteGenerator.generate());
        compte.setDateOuverture(today);
        compte.setIdParticulier(idParticulier);
        compteDao.create(compte);

        Status initialStatus = statusDao.findByLibelle("Actif");
        HistoStatus histoStatus = new HistoStatus();
        histoStatus.setCompte(compte);
        histoStatus.setStatus(initialStatus);
        histoStatus.setDateChangement(now);

        histoStatusDao.create(histoStatus);
        return compte;
    }
    
    @Override
    public Compte findAccountById(int id) { 
        return compteDao.find(id);
    }
    
    @Override
    public Compte findAccountByNumero(String numeroCompte) {
        return compteDao.findByNumero(numeroCompte);
    }

    @Override
    public List<Compte> findAllAccounts() {
        return compteDao.findAll();
    }

    @Override
    public List<Pret> findAllLoans(){
        return pretDao.findAll();
    }

    @Override
    public List<Pret> findLoansByAccount(Compte compte) {
        return pretDao.findByCompte(compte);
    }

    @Override
    public Status getStatus(Compte compte) {
        HistoStatus lastHistoStatus = histoStatusDao.getLastHistoStatus(compte);
        return lastHistoStatus != null ? lastHistoStatus.getStatus() : null;
    }

    @Override
    public void changeStatus(Compte compte, Status newStatus) {
        HistoStatus histoStatus = new HistoStatus();
        histoStatus.setCompte(compte);
        histoStatus.setStatus(newStatus);
        histoStatus.setDateChangement(LocalDateTime.now());
        histoStatusDao.create(histoStatus);
    }

    @Override
    public void blockAccount(Compte compte) {
        Status blockedStatus = statusDao.findByLibelle("Bloquer");
        changeStatus(compte, blockedStatus);
    }

    @Override
    public void unblockAccount(Compte compte) {
        Status activeStatus = statusDao.findByLibelle("Actif");
        changeStatus(compte, activeStatus);
    }
    
    @Override
    public void applyForLoan(Compte compte, Double amount, int durationInMonths , Double interestRate , Type type) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();

        LocalDate datePayement = today.plusMonths(2);
        
        
        Pret pret = new Pret();
        pret.setMontant(amount);
        pret.setDateCommencement(today);
        pret.setTaux(interestRate);
        pret.setDureeMois(durationInMonths);
        pret.setType(type);
        pret.setCompte(compte);

        pretDao.create(pret);

        StatusRemboursement initialStatus = statusRemboursementDao.findByLibelle("En cours");
        //ajouter l'ajout du rembousement 
        List<RemboursementVisualisation> schedule = RemboursementCalculator.calculateSchedule(amount, durationInMonths, interestRate);
        for (RemboursementVisualisation rv : schedule) {
            Remboursement remboursement = new Remboursement();
            remboursement.setDatePrevue(datePayement);
            remboursement.setMontantTotal(rv.getMontantTotal());
            remboursement.setMontantCapital(rv.getMontantCapital());
            remboursement.setMontantInteret(rv.getMontantInteret());
            remboursement.setMontantAssurance(rv.getMontantAssurance());
            remboursement.setStatusRemboursement(initialStatus);
            remboursement.setPret(pret);

            remboursementDao.create(remboursement);
            datePayement = datePayement.plusMonths(1);

            HistoStatusRemboursement histoStatusRemboursement = new HistoStatusRemboursement();
            histoStatusRemboursement.setRemboursement(remboursement);
            histoStatusRemboursement.setStatusRemboursement(initialStatus);
            histoStatusRemboursement.setDatePaiement(today);
            histoStatusRemboursement.setMontant(0.0);

            histoStatusRemboursementDao.create(histoStatusRemboursement);
        }

    }

    @Override
    public void applyForLoan(Compte compte, Double amount, int durationInMonths , Double interestRate) {
        applyForLoan(compte, amount, durationInMonths, interestRate, typeDao.findByLibelle("Personnel"));
    }

    @Override
    public void repayLoan(Pret pret, Double amount) {
        // Implémentation du remboursement de prêt
        // Cette méthode devrait localiser le prêt actif du compte,
        // appliquer le montant remboursé aux remboursements en cours,
        // et mettre à jour les statuts en conséquence.
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        List<Remboursement> remboursements = remboursementDao.findNonPaidRemboursements(pret);
        Double remainingAmount = amount;
        while (remainingAmount > 0) {
            if (remboursements.isEmpty()) {
                break; 
            }
            Remboursement remboursement = remboursements.get(0);
            Double montantRemboursement = histoStatusRemboursementDao.sumMontantByRemboursement(remboursement);
            if (remainingAmount >= montantRemboursement) {
                // Remboursement complet
                remainingAmount -= montantRemboursement;
                StatusRemboursement paidStatus = statusRemboursementDao.findByLibelle("Payer");
                remboursement.setStatusRemboursement(paidStatus);
                remboursementDao.update(remboursement);

                HistoStatusRemboursement histoStatusRemboursement = new HistoStatusRemboursement();
                histoStatusRemboursement.setRemboursement(remboursement);
                histoStatusRemboursement.setMontant(montantRemboursement);
                histoStatusRemboursement.setStatusRemboursement(paidStatus);
                histoStatusRemboursement.setDatePaiement(today);
                histoStatusRemboursementDao.create(histoStatusRemboursement);

                remboursements.remove(0); 
            } else {
                // Remboursement partiel
                StatusRemboursement nonPaidStatus = statusRemboursementDao.findByLibelle("En cours");

                HistoStatusRemboursement histoStatusRemboursement = new HistoStatusRemboursement();
                histoStatusRemboursement.setRemboursement(remboursement);
                histoStatusRemboursement.setMontant(remainingAmount);
                histoStatusRemboursement.setStatusRemboursement(nonPaidStatus);
                histoStatusRemboursement.setDatePaiement(today);
                histoStatusRemboursementDao.create(histoStatusRemboursement);
                
                break;
            }
        }

    }

    @Override
    public List<Remboursement> getRepaymentSchedule(Pret pret) {
        return remboursementDao.findByPretId(pret);
    }

    @Override
    public List<RemboursementVisualisation> viewRepaymentSchedule(Pret pret) {
        List<RemboursementVisualisation> schedule = RemboursementCalculator.calculateSchedule(pret.getMontant(), pret.getDureeMois(), pret.getTaux()); 
        return schedule;
    }

    @Override
    public List<RemboursementVisualisation> viewRepaymentSchedule(Double amount, int durationInMonths, Double interestRate) {
        List<RemboursementVisualisation> schedule = RemboursementCalculator.calculateSchedule(amount, durationInMonths, interestRate); 
        return schedule;
    }
}
