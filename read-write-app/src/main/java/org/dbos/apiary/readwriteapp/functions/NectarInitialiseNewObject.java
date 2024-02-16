package org.dbos.apiary.readwriteapp.functions;

import org.dbos.apiary.postgres.PostgresContext;
import org.dbos.apiary.postgres.PostgresFunction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Array;

public class NectarInitialiseNewObject extends PostgresFunction {
    // Just a function to test that application is alive!
    public static final String insertKey = "INSERT INTO ObjectStore (ObjectId, Key, Val) VALUES (?, ?, ?);";
    public static int runFunction(PostgresContext ctxt, int objectId, int numEntries, int entrySize) throws SQLException {
      String value = new String(new char[entrySize]).replace('\0', 'x');
      for ( int i = 0; i < numEntries; i++ ) {
        ctxt.executeUpdate(insertKey, objectId, i, value);
      }
      return 0;
    }
}
