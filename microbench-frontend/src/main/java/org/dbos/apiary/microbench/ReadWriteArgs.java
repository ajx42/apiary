package org.dbos.apiary.microbench;

import java.util.List;

public class ReadWriteArgs {
    private List<String> objectIds;
    private int opsPerObject;
    private int entriesPerObject;
    private int entrySize;
    private int writeChance;
    
	public int getOpsPerObject() {
		return opsPerObject;
	}
	public void setOpsPerObject(int opsPerObject) {
		this.opsPerObject = opsPerObject;
	}
	public List<String> getObjectIds() {
		return objectIds;
	}
	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
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
}
