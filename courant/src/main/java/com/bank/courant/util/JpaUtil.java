package com.bank.courant.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final EntityManagerFactory emf;

    static {
        try {
            // Doit correspondre au nom dans persistence.xml
            emf = Persistence.createEntityManagerFactory("CourantPU");
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Erreur lors de l'initialisation de l'EntityManagerFactory : " + e.getMessage());
        }
    }

    public static EntityManager em() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}