package dataCollectionReceiver;

import dataCollectionReceiver.activeMQ.Executor;
import dataCollectionReceiver.service.DataCollectionReceiverService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {

        List<Runnable> services = new ArrayList<>();

        services.add(new DataCollectionReceiverService());

        Executor executor = new Executor(services);
        executor.start();

    }
}
