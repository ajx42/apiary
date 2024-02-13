package org.dbos.apiary.readwriteapp.functions;

import org.dbos.apiary.postgres.PostgresContext;
import org.dbos.apiary.postgres.PostgresFunction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NectarSayHi extends PostgresFunction {

    public static String runFunction(PostgresContext ctxt, String name) throws SQLException {
      String welcome = "Hello!";
      String response = welcome + " " + name;
      return response;
    }
}
