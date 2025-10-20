package com.bank.courant.remote;

import jakarta.ejb.Remote;
import com.bank.courant.model.Particulie;
import java.util.List;

@Remote
public interface ParticulieServiceRemote {

    // CREATE
    void create(Particulie particulie) ;
    // READ by id
    Particulie find(int id) ;
    // READ by cin
    Particulie findByCin(String cin) ;
    // READ ALL
    List<Particulie> findAll() ;
    // UPDATE
    Particulie update(Particulie particulie) ; 
    // DELETE
    void delete(int id) ;

}
