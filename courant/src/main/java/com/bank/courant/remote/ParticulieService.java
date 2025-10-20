package com.bank.courant.remote;

import com.bank.courant.model.Particulie;
import com.bank.courant.dao.ParticulieDao;
import jakarta.ejb.Stateless;
import jakarta.ejb.EJB;

import java.util.List;

@Stateless
public class ParticulieService implements ParticulieServiceRemote {

    @EJB
    private ParticulieDao particulieDao ;

    // CREATE
    @Override
    public void create(Particulie particulie) {
        particulieDao.create(particulie);
    }

    // READ by id
    @Override
    public Particulie find(int id) {
        return particulieDao.find(id);
    }

    // READ by cin
    @Override
    public Particulie findByCin(String cin) {
        return particulieDao.findByCin(cin);
    }

    // READ ALL
    @Override
    public List<Particulie> findAll() {
        return particulieDao.findAll();
    }

    // UPDATE
    @Override
    public Particulie update(Particulie particulie) {
        return particulieDao.update(particulie);
    }

    // DELETE
    @Override
    public void delete(int id) {
        particulieDao.delete(id);
    }
}
