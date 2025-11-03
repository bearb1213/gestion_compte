package com.bank.courant.util;

import java.util.concurrent.atomic.AtomicLong;

public class NumeroGenerator {
    private static final AtomicLong courant = new AtomicLong(1000000000L);
    private static final AtomicLong pret = new AtomicLong(1000000000L);
    private static final AtomicLong depot = new AtomicLong(1000000000L);


    public static String generateNumeroCompteCourant() {
        long numero = courant.getAndIncrement();
        return "0" + numero;
    }


}




