package dataCollectionReceiver.service;

import dataCollectionReceiver.Main;
import dataCollectionReceiver.activeMQ.abstractservices.ConsumerService;
import dataCollectionReceiver.worker.DataCollectionCreator;

public class DataCollectionReceiverService extends ConsumerService {
    public static String IN_QUEUE = "NewDataGatheringJob";

    private final DataCollectionCreator dataCollectionCreator = new DataCollectionCreator();

    public DataCollectionReceiverService(){
        super(IN_QUEUE, Main.BROKER_URL);
    }

    @Override
    protected void execute(String input) {
        dataCollectionCreator.collectDataForCustomer(input);
    }
}
