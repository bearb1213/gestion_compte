package com.bank.courant.remote;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.EJB;

import com.bank.courant.model.Particulie;
import com.bank.courant.model.Compte;
import com.bank.courant.model.Status;
import com.bank.courant.model.HistoStatus;
import com.bank.courant.model.Frais;
import com.bank.courant.model.Transaction;

import com.bank.courant.dao.CompteDao;
import com.bank.courant.dao.StatusDao;
import com.bank.courant.dao.HistoStatusDao;
import com.bank.courant.dao.FraisDao;
import com.bank.courant.dao.TransactionDao;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Stateless
public class CompteService implements CompteServiceRemote {

    @EJB
    private CompteDao compteDao ;

    @EJB
    private StatusDao statusDao ;

    @EJB
    private HistoStatusDao histoStatusDao ;

    @EJB
    private FraisDao fraisDao ;

    @EJB 
    private TransactionDao transactionDao ;

    @Override
    public Compte createAccount(Particulie particulie, Double montantDecouvert ,Double tenu_compte , Double frais_decouvert) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        
        Compte compte = new Compte();
        compte.setNumeroCompte(NumCompteGenerator.generate());
        compte.setDateOuverture(today);
        compte.setMontantDecouvert(montantDecouvert);
        compte.setParticulier(particulie);
        compteDao.create(compte);
        // Initial status
        Status initialStatus = statusDao.findByLibelle("Actif");
        HistoStatus histo = new HistoStatus();
        histo.setCompte(compte);
        histo.setStatus(initialStatus);
        histo.setDateChangement(now);
        histoStatusDao.create(histo);
        // Initial fees
        Frais frais = new Frais();
        frais.setCompte(compte);
        frais.setDateChangement(now);
        frais.setTenuCompte(tenu_compte);
        frais.setDecouvert(frais_decouvert);
        fraisDao.create(frais);
        
        
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
    public Status getStatus(Compte compte) {
        HistoStatus histo = histoStatusDao.getLastHistoStatus(compte);
        return histo != null ? histo.getStatus() : null;
    }

    @Override
    public void changeStatus(Compte compte, Status newStatus) {
        HistoStatus histo = new HistoStatus();
        histo.setCompte(compte);
        histo.setStatus(newStatus);
        histo.setDateChangement(LocalDateTime.now());
        histoStatusDao.create(histo);
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
    public Double getBalance(Compte compte) {
        return transactionDao.findLatestByCompte(compte).map(Transaction::getMontantActuel).orElse(0.0);
    }

    @Override
    public Double getBalance(Compte compte , LocalDate date) {
        date.plusDays(1);
        LocalDateTime dateTime = date.atStartOfDay();
        return transactionDao.findLatestByCompteBeforeDate(compte, dateTime).map(Transaction::getMontantActuel).orElse(0.0);
    }

    @Override
    public Double getBalance(String numeroCompte) {
        Compte compte = compteDao.findByNumero(numeroCompte);
        return getBalance(compte);
    }
    
    @Override
    public Double getBalance(String numeroCompte , LocalDate date) {
        Compte compte = compteDao.findByNumero(numeroCompte);
        return getBalance(compte, date);
    }

    @Override
    public void deposit(Compte compte, Double amount) {
        LocalDateTime now = LocalDateTime.now();
        Double currentBalance = getBalance(compte);
        Double newBalance = currentBalance + amount;

        Transaction transaction = new Transaction();
        transaction.setMontant(amount);
        transaction.setDateTransaction(now);
        transaction.setDescription("Dépôt de " + amount + " la date " + now);
        transaction.setMouvement("D"); // 'D' pour Dépôt
        transaction.setMontantActuel(newBalance);
        transaction.setCompte(compte);
        transactionDao.create(transaction);

    }

    @Override
    public void deposit(String numeroCompte, Double amount) {
        Compte compte = compteDao.findByNumero(numeroCompte);
        deposit(compte, amount);
    }

    @Override
    public boolean withdraw(Compte compte, Double amount) {
        Double currentBalance = getBalance(compte);
        Double newBalance = currentBalance - amount;

        // Vérifier le découvert autorisé
        Double allowedOverdraft = compte.getMontantDecouvert();
        if (newBalance < -allowedOverdraft) {
            return false; // Retrait refusé, dépassement du découvert autorisé
        }

        LocalDateTime now = LocalDateTime.now();

        Transaction transaction = new Transaction();
        transaction.setMontant(amount);
        transaction.setDateTransaction(now);
        transaction.setDescription("Retrait de " + amount +  " la date " + now);
        transaction.setMouvement("R"); // 'R' pour Retrait
        transaction.setMontantActuel(newBalance);
        transaction.setCompte(compte);
        transactionDao.create(transaction);

        return true; 
    }

    @Override
    public boolean withdraw(String numeroCompte, Double amount) {
        Compte compte = compteDao.findByNumero(numeroCompte);
        return withdraw(compte, amount);
    }

    @Override
    public void  transfer(Compte fromCompte, Compte toCompte, Double amount) throws IllegalArgumentException {
        Double currentBalanceFrom = getBalance(fromCompte);
        //add frais si demander
        Double newBalanceFrom = currentBalanceFrom - amount;

        Double allowedOverDraft = fromCompte.getMontantDecouvert();
        if (newBalanceFrom < -allowedOverDraft) {
            throw new IllegalArgumentException("Fonds insuffisants pour le transfert, dépassement du découvert autorisé.");
        }
    
        LocalDateTime now = LocalDateTime.now();

        Transaction transactionFrom = new Transaction();
        transactionFrom.setMontant(amount);
        transactionFrom.setDateTransaction(now);
        transactionFrom.setDescription("Transfert de " + amount + " vers compte " + toCompte.getNumeroCompte() + " la date " + now);
        transactionFrom.setMouvement("R"); // 'R' pour Retrait
        transactionFrom.setMontantActuel(newBalanceFrom);
        transactionFrom.setCompte(fromCompte);
        transactionDao.create(transactionFrom);

        // Dépôt dans le compte destinataire
        Double currentBalanceTo = getBalance(toCompte);
        Double newBalanceTo = currentBalanceTo + amount;

        Transaction transactionTo = new Transaction();
        transactionTo.setMontant(amount);
        transactionTo.setDateTransaction(now);
        transactionTo.setDescription("Transfert de " + amount + " depuis compte " + fromCompte.getNumeroCompte() + " la date " + now);
        transactionTo.setMouvement("D"); // 'D' pour Dépôt
        transactionTo.setMontantActuel(newBalanceTo);
        transactionTo.setCompte(toCompte);
        transactionDao.create(transactionTo);

    }

    @Override
    public void transfer(String fromNumeroCompte, String toNumeroCompte, Double amount) throws IllegalArgumentException {
        Compte fromCompte = compteDao.findByNumero(fromNumeroCompte);
        Compte toCompte = compteDao.findByNumero(toNumeroCompte);
        transfer(fromCompte, toCompte, amount);
    }


}