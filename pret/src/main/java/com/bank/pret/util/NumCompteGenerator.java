package com.bank.pret.util;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class NumCompteGenerator {
    private static AtomicLong counter = new AtomicLong(0);
    
    public static String generate() {
        long timestamp = Instant.now().toEpochMilli(); 
        long sequence = counter.incrementAndGet() % 10000; 
        
        String base = String.format("%016d%04d", timestamp % 10000000000000000L, sequence);
        
        return base.length() > 20 ? base.substring(0, 20) : base;
    }

}
