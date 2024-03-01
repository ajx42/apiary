package org.dbos.apiary.microbench.functions;

import org.dbos.apiary.postgres.PostgresContext;
import org.dbos.apiary.postgres.PostgresFunction;

import java.sql.SQLException;
import java.util.Random;

public class NectarReadWrite extends PostgresFunction {
	public static final String getEntry = "SELECT Val FROM ObjectStore WHERE ObjectId=? AND Key=?";
    public static final String putEntry = "INSERT INTO ObjectStore (ObjectId, Key, Val) VALUES (?, ?, ?);";
   
    public static int runFunction(PostgresContext ctxt, String objectId, int opsPerObject,
    		int entriesPerObject, int entrySize, int writeChance) throws SQLException {
    	Random rand = new Random();

    	//for (String objectId : objectIds) {
    		for (int i = 0; i < opsPerObject; ++i) {
    			int key = rand.nextInt(entriesPerObject);
    			int rval = rand.nextInt(100);
    			if (rval < writeChance) {
    				String value = new String(new char[entrySize]).replace('\0', 'x');
    				ctxt.executeUpdate(putEntry, objectId, key, value);
    			} else {
    				ctxt.executeQuery(getEntry, objectId, key);
    			}
    		}
    	//}
    	
    	return 0;
    }	
}