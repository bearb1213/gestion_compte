package com.bank.courant.remote;

import jakarta.ejb.Remote;
import com.bank.courant.model.Particulie;
import com.bank.courant.model.Compte;
import com.bank.courant.model.Status;
import java.time.LocalDate;

@Remote
public interface CompteServiceRemote {
    Compte createAccount(Particulie particulie, Double montantDecouvert ,Double tenu_compte , Double frais_decouvert) ;

    Compte findAccountById(int id) ;

    Compte findAccountByNumero(String numeroCompte) ;

    Status getStatus(Compte compte) ;

    void changeStatus(Compte compte, Status newStatus) ;

    void blockAccount(Compte compte) ;  

    void unblockAccount(Compte compte) ;

    Double getBalance(Compte compte) ;
    
    Double getBalance(Compte compte , LocalDate date) ;

    Double getBalance(String numeroCompte) ;

    Double getBalance(String numeroCompte , LocalDate date) ;

    void deposit(Compte compte, Double amount) ;

    void deposit(String numeroCompte, Double amount) ;

    boolean withdraw(Compte compte, Double amount) ;

    boolean withdraw(String numeroCompte, Double amount) ;

    void transfer(Compte fromCompte, Compte toCompte, Double amount)throws IllegalArgumentException ;

    void transfer(String fromNumeroCompte, String toNumeroCompte, Double amount)throws IllegalArgumentException ;
    

}
