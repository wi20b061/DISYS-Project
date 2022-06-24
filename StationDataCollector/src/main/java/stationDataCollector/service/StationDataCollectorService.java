package stationDataCollector.service;

import stationDataCollector.Main;
import stationDataCollector.activeMQ.abstractservices.ConsumerProducerService;
import stationDataCollector.activeMQ.abstractservices.ConsumerService;
import stationDataCollector.model.Charging;
import stationDataCollector.worker.StationDataCollector;

public class StationDataCollectorService extends ConsumerProducerService {

    // name of message we need to listen for in queue
    public static String IN_QUEUE = "NewDataGatheringJob";
    public static String OUT_QUEUE = "StationDataForCustomer";

    //führt db abfrage durch
    private final StationDataCollector stationDataCollector = new StationDataCollector();

    public StationDataCollectorService(){
        super(IN_QUEUE, OUT_QUEUE,Main.BROKER_URL);
    }

    @Override
    protected String execute(String input) {
        // inqueue timeout brokerurl from constructor
        return stationDataCollector.getStationData(input);
    }
}
