package org.dbos.apiary.microbench;

import java.sql.SQLException;

import org.dbos.apiary.microbench.functions.NectarBatchCreate;
import org.dbos.apiary.microbench.functions.NectarCreate;
import org.dbos.apiary.microbench.functions.NectarHashing;
import org.dbos.apiary.microbench.functions.NectarReadWrite;
import org.dbos.apiary.postgres.PostgresConnection;
import org.dbos.apiary.utilities.ApiaryConfig;
import org.dbos.apiary.worker.ApiaryNaiveScheduler;
import org.dbos.apiary.worker.ApiaryWorker;

public class MicrobenchWorker {
    public static void main(String[] args) throws SQLException, InterruptedException {
        ApiaryConfig.captureUpdates = false;
        ApiaryConfig.captureReads = false;
        ApiaryConfig.captureMetadata = false;
        ApiaryConfig.isolationLevel = ApiaryConfig.SERIALIZABLE;
        ApiaryConfig.provenancePort = 5432;  // Store provenance data in the same database.

        PostgresConnection conn = new PostgresConnection("localhost", ApiaryConfig.postgresPort, "postgres", "dbos");
        conn.dropTable("ObjectStore"); // For testing.
        conn.createTable("ObjectStore", "ObjectId VARCHAR(10) NOT NULL, Key INT NOT NULL, Val TEXT");

        int cores = Runtime.getRuntime().availableProcessors();
        
        ApiaryWorker worker = new ApiaryWorker(new ApiaryNaiveScheduler(), cores, ApiaryConfig.postgres, ApiaryConfig.provenanceDefaultAddress);
        worker.registerConnection(ApiaryConfig.postgres, conn);
        worker.registerFunction("NectarHashing", ApiaryConfig.postgres, NectarHashing::new);
        worker.registerFunction("NectarCreate", ApiaryConfig.postgres, NectarCreate::new);
        worker.registerFunction("NectarBatchCreate", ApiaryConfig.postgres, NectarBatchCreate::new);
        worker.registerFunction("NectarReadWrite", ApiaryConfig.postgres, NectarReadWrite::new);
        
        worker.startServing();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Stopping Apiary worker server.");
            worker.shutdown();
        }));
        Thread.sleep(Long.MAX_VALUE);
        worker.shutdown();
    }
}
