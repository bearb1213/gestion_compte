package com.bank.pret.remote;

import jakarta.ejb.Remote;
import com.bank.pret.model.Pret;
import com.bank.pret.model.Remboursement;
import com.bank.pret.model.Type;
import com.bank.pret.model.Status;
import com.bank.pret.util.RemboursementVisualisation;

import java.util.List;

import com.bank.pret.model.Compte;


@Remote
public interface PretServiceRemote {
    Compte createLoanAccount(int idParticulier) ;
    
    Compte findAccountById(int id) ;
    
    Compte findAccountByNumero(String numeroCompte) ;

    List<Compte> findAllAccounts();

    List<Pret> findAllLoans();

    List<Pret> findLoansByAccount(Compte compte) ;

    Status getStatus(Compte compte) ;

    void changeStatus(Compte compte, Status newStatus) ;

    void blockAccount(Compte compte) ;

    void unblockAccount(Compte compte) ;
    
    void applyForLoan(Compte compte, Double amount, int durationInMonths , Double interestRate , Type type) ;

    void applyForLoan(Compte compte, Double amount, int durationInMonths , Double interestRate) ;

    void repayLoan(Pret pret, Double amount) ;

    List<Remboursement> getRepaymentSchedule(Pret pret) ;

    List<RemboursementVisualisation> viewRepaymentSchedule(Pret pret) ;

    List<RemboursementVisualisation> viewRepaymentSchedule(Double amount, int durationInMonths, Double interestRate) ;

    


}
