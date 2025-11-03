package com.bank.courant.remote;

import com.bank.courant.model.ActionRole;
import com.bank.courant.model.Utilisateur;

import java.util.List;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Singleton
public class ActionRoleService implements ActionRoleRemote {

    @PersistenceContext(unitName = "CourantPU")
    private EntityManager em;
    
    private List<ActionRole> actionRoles;
    private boolean isLoaded = false;

    public void charge(){
        if (isLoaded) 
            return;
        this.actionRoles = ActionRole.findAll(em);
        this.isLoaded = true;
    }

    public boolean canDo(Utilisateur user,String nomTable , String action){
        // charge();
        for(ActionRole ar : this.actionRoles){
            if( ar.getNomTable().equals(nomTable) && ar.getAction().equals(action) && ar.getRole() <= user.getDirection().getRole() ){
                return true;
            }
        }
        return false;
    }

    public Utilisateur authenticate(String login, String password){
        Utilisateur user = new Utilisateur();
        user.setUsername(login);
        user.setPassword(password);
        return user.authenticate(em);
    }

}
