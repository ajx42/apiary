package org.dbos.apiary.readwriteapp.messages;

public class SingleReadWriteMsg {

    public int getNumOps() {
        return numOps;
    }

    public void setNumOps(int numOps) {
        this.numOps = numOps;
    }

    public int getNumEntries() {
        return numEntries;
    }

    public void setNumEntries(int numEntries) {
        this.numEntries = numEntries;
    }

    public int getEntrySize() {
        return entrySize;
    }

    public void setEntrySize(int entrySize) {
        this.entrySize = entrySize;
    }

    public int getWriteChance() {
        return writeChance;
    }

    public void setWriteChance(int writeChance) {
        this.writeChance = writeChance;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    private int numOps;
    private int numEntries;
    private int entrySize;
    private int writeChance;
    private int objectId;
}
