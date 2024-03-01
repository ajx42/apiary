package org.dbos.apiary.microbench.functions;

import org.dbos.apiary.postgres.PostgresContext;
import org.dbos.apiary.postgres.PostgresFunction;

import java.sql.SQLException;

public class NectarCreate extends PostgresFunction {
    public static final String insertKey = "INSERT INTO ObjectStore (ObjectId, Key, Val) VALUES (?, ?, ?);";
    public static int runFunction(PostgresContext ctxt, String objectId, int numEntries, int entrySize) throws SQLException {
      String value = new String(new char[entrySize]).replace('\0', 'x');
      for ( int key = 0; key < numEntries; key++ ) {
        ctxt.executeUpdate(insertKey, objectId, key, value);
      }
      return 0;
    }
}