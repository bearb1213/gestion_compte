package com.bank.change.remote;

import com.bank.change.model.Change;
import jakarta.ejb.Remote;
import java.util.List;



@Remote
public interface ChangeServiceRemote {
    
    
    List<Change> getAllChanges();
    void addChange(Change change);
    void updateChange(Change change);
    void deleteChange(String libelle);
    Double getTauxByLibelle(String libelle);
    Double getTauxById(int id);
    Double convertAmount(String libelle, Double amount);
    Double convertAmount(int id, Double amount);
}
