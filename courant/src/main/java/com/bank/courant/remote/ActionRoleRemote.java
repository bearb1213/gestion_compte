package com.bank.courant.remote;

import com.bank.courant.model.Utilisateur;

import jakarta.ejb.Remote;

@Remote
public interface ActionRoleRemote {
    void charge();
    boolean canDo(Utilisateur user,String nomTable , String action);
    Utilisateur authenticate(String login, String password);

}
