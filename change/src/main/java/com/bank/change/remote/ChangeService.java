package com.bank.change.remote;

import java.util.ArrayList;
import java.util.List;

import com.bank.change.model.Change;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.InputStream;



@Singleton
@Startup
public class ChangeService implements ChangeServiceRemote {
    
    private List<Change> changes ;

    private boolean isCharged;

    @PostConstruct
    public void init() {
        // Chargement des changes au démarrage
        charge();
    }

    private void charge(){
        if(isCharged)
            return;
        
        synchronized (this) {
            if(isCharged) 
                return;
                
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                
                InputStream inputStream = getClass().getClassLoader()
                        .getResourceAsStream("change.json");
                
                if (inputStream != null) {
                    List<Change> loadedChanges = objectMapper.readValue(inputStream, 
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Change.class));
                    
                    // FILTRER les changes avant de les assigner
                    LocalDate now = LocalDate.now();
                    List<Change> validChanges = new ArrayList<>();
                    
                    for (Change change : loadedChanges) {
                        if (change.getDateFin() == null || change.getDateFin().isAfter(now)) {
                            validChanges.add(change);
                        }
                    }
                    
                    changes = validChanges;
                    isCharged = true;
                    System.out.println("Changes chargés : " + changes.size() + " éléments valides");
                } else {
                    changes = new ArrayList<>();
                    isCharged = false;
                    System.err.println("Fichier change.json non trouvé");
                }
            } catch (Exception e) {
                e.printStackTrace();
                changes = new ArrayList<>();
                isCharged = false;
            }
        }
    }

    @Override
    public List<Change> getAllChanges(){
        if(!isCharged){
            charge();
        }
        return changes;
        
    }

    @Override
    public void addChange(Change change){
    }

    @Override
    public void updateChange(Change change){
    }
    @Override
    public void deleteChange(String libelle){
    }
    @Override
    public Double getTauxByLibelle(String libelle){
        LocalDate today = LocalDate.now();
        if(!isCharged)
        charge();

        for (Change change : changes) {
            if(change.getLibelle().equals(libelle))
                if(change.getDateDebut().isBefore(today) && (change.getDateFin()==null ||change.getDateFin().isAfter(today) || change.getDateFin().isEqual(today)   ))
                    return change.getTauxChange();
        }
        return 1.0;

    }

    @Override
    public Double convertAmount(String libelle, Double amount){
        Double taux = getTauxByLibelle(libelle);
        return amount * taux;
    }    
    public Double getTauxById(int id){
        LocalDate today = LocalDate.now();
        if(!isCharged)
        charge();

        for (Change change : changes) {
            if(change.getId() == id)
                if(change.getDateDebut().isBefore(today) && (change.getDateFin()==null ||change.getDateFin().isAfter(today) || change.getDateFin().isEqual(today)   ))
                    return change.getTauxChange();
        }
        return 1.0;

    }

    public Double convertAmount(int id, Double amount){
        Double taux = getTauxById(id);
        return amount * taux;
    }

}
