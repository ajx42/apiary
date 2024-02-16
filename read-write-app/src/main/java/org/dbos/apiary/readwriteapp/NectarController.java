package org.dbos.apiary.readwriteapp;

import com.google.protobuf.InvalidProtocolBufferException;
import org.dbos.apiary.postgres.PostgresConnection;
import org.dbos.apiary.readwriteapp.functions.NectarSayHi;
import org.dbos.apiary.readwriteapp.functions.NectarInitialiseNewObject;
import org.dbos.apiary.readwriteapp.messages.HelloMsg;
import org.dbos.apiary.readwriteapp.messages.InitialiseMsg;
import org.dbos.apiary.readwriteapp.ObjectIdManager;
import org.dbos.apiary.utilities.ApiaryConfig;
import org.dbos.apiary.worker.ApiaryNaiveScheduler;
import org.dbos.apiary.worker.ApiaryWorker;
import org.dbos.apiary.client.ApiaryWorkerClient;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NectarController {
    ApiaryWorkerClient client;
    ObjectIdManager oMgr;

    public NectarController() throws SQLException {
        ApiaryConfig.captureUpdates = true;
        ApiaryConfig.captureReads = true;
        ApiaryConfig.provenancePort = 5432;  // Store provenance data in the same database.

        PostgresConnection conn = new PostgresConnection("localhost", ApiaryConfig.postgresPort, "postgres", "dbos");
        conn.dropTable("ObjectStore"); // For testing.
        conn.createTable("ObjectStore", "ObjectId INT NOT NULL, Key INT NOT NULL, Val TEXT");

        ApiaryWorker apiaryWorker = new ApiaryWorker(new ApiaryNaiveScheduler(), 4, ApiaryConfig.postgres, ApiaryConfig.provenanceDefaultAddress);
        apiaryWorker.registerConnection(ApiaryConfig.postgres, conn);
        apiaryWorker.registerFunction("NectarSayHi", ApiaryConfig.postgres, NectarSayHi::new);
        apiaryWorker.registerFunction("NectarInitialiseNewObject", ApiaryConfig.postgres, NectarInitialiseNewObject::new);
        apiaryWorker.startServing();

        this.client = new ApiaryWorkerClient("localhost");
        this.oMgr = new ObjectIdManager();
    }

    @PostMapping("/sayhi")
    public String registrationSubmit(@RequestBody HelloMsg msg) throws IOException {
        String welcomeMsg = client.executeFunction("NectarSayHi", msg.getName()).getString();
        return welcomeMsg;
    }

    @PostMapping("/initialise")
    public int registrationSubmit(@RequestBody InitialiseMsg msg) throws IOException {
        int objectId = oMgr.incrementAndGet();
        client.executeFunction("NectarInitialiseNewObject", objectId, msg.getNumEntries(), msg.getEntrySize());
        return objectId;
    }
}
