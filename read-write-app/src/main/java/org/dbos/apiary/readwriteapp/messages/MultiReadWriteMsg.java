package org.dbos.apiary.readwriteapp.messages;

public class MultiReadWriteMsg {

    public int getOpsPerObject() {
        return opsPerObject;
    }

    public void setOpsPerObject(int opsPerObject) {
        this.opsPerObject = opsPerObject;
    }

    public int getEntriesPerObject() {
        return entriesPerObject;
    }

    public void setEntriesPerObject(int entriesPerObject) {
        this.entriesPerObject = entriesPerObject;
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

    public int[] getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(int[] objectIds) {
        this.objectIds = objectIds;
    }

    private int opsPerObject;
    private int entriesPerObject;
    private int entrySize;
    private int writeChance;
    private int[] objectIds;
}
