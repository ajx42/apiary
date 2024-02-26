package org.dbos.apiary.microbench;

import org.dbos.apiary.client.ApiaryWorkerClient;
import org.dbos.apiary.hashing.functions.NectarHashing;
import org.dbos.apiary.postgres.PostgresConnection;
import org.dbos.apiary.utilities.ApiaryConfig;
import org.dbos.apiary.worker.ApiaryNaiveScheduler;
import org.dbos.apiary.worker.ApiaryWorker;
import org.springframework.web.bind.annotation.*;

import com.google.protobuf.InvalidProtocolBufferException;

import java.sql.*;

@RestController
public class NectarController {
    private ThreadLocal<ApiaryWorkerClient> client;
    private final ApiaryWorker worker;

    public NectarController() throws SQLException {
        ApiaryConfig.captureUpdates = false;
        ApiaryConfig.captureReads = false;
        ApiaryConfig.captureMetadata = false;
        ApiaryConfig.isolationLevel = ApiaryConfig.SERIALIZABLE;
        ApiaryConfig.provenancePort = 5432;  // Store provenance data in the same database.

        PostgresConnection conn = new PostgresConnection("localhost", ApiaryConfig.postgresPort, "postgres", "dbos");
        int cores = Runtime.getRuntime().availableProcessors();
        
        this.worker = new ApiaryWorker(new ApiaryNaiveScheduler(), cores, ApiaryConfig.postgres, ApiaryConfig.provenanceDefaultAddress);
        worker.registerConnection(ApiaryConfig.postgres, conn);
        worker.registerFunction("NectarHashing", ApiaryConfig.postgres, NectarHashing::new);
        worker.startServing();
    }

    @PostMapping("/hashing")
    public void index(@RequestBody HashingArgs args) throws InvalidProtocolBufferException {
    	if (client.get() == null) {
    		client.set(new ApiaryWorkerClient("localhost"));
    	}
        client.get().executeFunction("NectarHashing", args.getNumHashes(), args.getInputLen());
    }
}
