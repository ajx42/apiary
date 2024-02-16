package org.dbos.apiary.readwriteapp;

public class ObjectIdManager {
    private int currentMax = 0;
    
    public synchronized int incrementAndGet() {
        return ++currentMax;
    }
}


