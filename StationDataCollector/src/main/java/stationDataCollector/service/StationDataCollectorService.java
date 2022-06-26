package stationDataCollector.service;

import stationDataCollector.Main;

import stationDataCollector.activeMQ.abstractservices.ProducerService;


public class StationDataCollectorService extends ProducerService {

    public static String OUT_QUEUE = "StationDataForCustomer";


    public StationDataCollectorService(String input) {
        super(input, OUT_QUEUE, Main.BROKER_URL);
    }
}
