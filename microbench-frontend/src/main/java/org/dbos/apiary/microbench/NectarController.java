package org.dbos.apiary.microbench;

import org.dbos.apiary.client.ApiaryWorkerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.google.protobuf.InvalidProtocolBufferException;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.IOException;
import java.sql.SQLException;

@RestController
public class NectarController {
    private ThreadLocal<ApiaryWorkerClient> client;

    // shared counter to assign object ids
    private static AtomicInteger counter = new AtomicInteger(0);
    
    @Value("${apiary.address}")
    private String apiaryAddress;


    public NectarController() {
        this.client = new ThreadLocal<ApiaryWorkerClient>();
    }

    @PostMapping("/hashing")
    public void index(@RequestBody HashingArgs args) throws InvalidProtocolBufferException {
    	if (client.get() == null) {
    		client.set(new ApiaryWorkerClient(this.apiaryAddress));
    	}
      client.get().executeFunction("NectarHashing", args.getNumHashes(), args.getInputLen());
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int index(@RequestBody CreateArgs msg) throws IOException {
        if (client.get() == null) {
          client.set(new ApiaryWorkerClient(this.apiaryAddress));
        }
        int objectId = counter.incrementAndGet();
        client.get().executeFunction("NectarCreate", objectId, msg.getNumEntries(), msg.getEntrySize());
        return objectId;
    }
}
