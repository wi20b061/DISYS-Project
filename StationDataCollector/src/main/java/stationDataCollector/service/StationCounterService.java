package stationDataCollector.service;

import stationDataCollector.Main;
import stationDataCollector.activeMQ.abstractservices.ConsumerService;
import stationDataCollector.worker.StationCounter;

public class StationCounterService extends ConsumerService {
    public static String IN_QUEUE = "GetStationInformationForCustomer";

    private final StationCounter stationCounter = new StationCounter();

    public StationCounterService(){
        super(IN_QUEUE, Main.BROKER_URL);
    }

    @Override
    protected void execute(String input) {
        stationCounter.getDataForStations(input);
    }
}
