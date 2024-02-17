package org.dbos.apiary.readwriteapp.functions;

import org.dbos.apiary.postgres.PostgresContext;
import org.dbos.apiary.postgres.PostgresFunction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Array;
import java.util.Random;

public class NectarReadWriteOperation extends PostgresFunction {
    public static final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String updateRow = "UPDATE ObjectStore SET Val = ? WHERE ObjectId = ? AND Key = ?";
    public static final String readVal = "SELECT Val FROM ObjectStore WHERE ObjectId = ? AND Key = ?";
    
    public static int runFunction(PostgresContext ctxt, int objectId, int opsPerObject, int entriesPerObject, int entrySize, int writeChance) throws SQLException {
      Random rng = new Random(System.currentTimeMillis());
      for ( int i = 0; i < opsPerObject; i++ ) {
          int coin = rng.nextInt(100);
          int idx = rng.nextInt(entriesPerObject);
          if ( coin < writeChance ) {
              String value = new String(new char[entrySize]).replace('\0', alphanumeric.charAt(rng.nextInt(alphanumeric.length())));
              ctxt.executeUpdate(updateRow, value, objectId, idx);
          } else {
              ResultSet rs = ctxt.executeQuery(readVal, objectId, idx);
              //if ( rs.next() ) {
              //    System.out.println(rs.getString("Val"));
              //}
          }
      }
      return 0;
    }
}
