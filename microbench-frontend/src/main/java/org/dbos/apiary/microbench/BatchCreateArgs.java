package org.dbos.apiary.microbench;

public class BatchCreateArgs {
    public void setNumObjects(int numObjects) {
        this.numObjects = numObjects;
    }

    public int getNumObjects() {
        return numObjects;
    }
    public void setNumEntries(int numEntries) {
        this.numEntries = numEntries;
    }

    public int getNumEntries() {
        return numEntries;
    }

    public void setEntrySize(int entrySize) {
        this.entrySize = entrySize;
    }

    public int getEntrySize() {
        return entrySize;
    }

    private int numObjects;
    private int numEntries;
    private int entrySize;
}
