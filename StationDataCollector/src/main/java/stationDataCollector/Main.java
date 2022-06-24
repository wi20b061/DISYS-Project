package stationDataCollector;

import stationDataCollector.activeMQ.Executor;
import stationDataCollector.database.DatabaseService;
import stationDataCollector.service.StationDataCollectorService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {
        //StationDataCollector:
        // -Gathers data for a specific customer from a specific charging station
        // -Sends data to the DataCollectionReciever

        List<Runnable> services = new ArrayList<>();

        // as many services as stations ?
        services.add(new StationDataCollectorService());


        Executor executor = new Executor(services);
        executor.start();

    }
}
