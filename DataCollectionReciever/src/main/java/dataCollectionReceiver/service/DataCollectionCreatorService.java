package dataCollectionReceiver.service;

import dataCollectionReceiver.Main;
import dataCollectionReceiver.activeMQ.abstractservices.ConsumerService;
import dataCollectionReceiver.activeMQ.abstractservices.ProducerService;

public class DataCollectionCreatorService extends ConsumerService {
    public static String IN_QUEUE = "StationDataForCustomer";

    public DataCollectionCreatorService() {
        super(IN_QUEUE, Main.BROKER_URL);
    }

    @Override
    protected void execute(String input) {

    }
}
