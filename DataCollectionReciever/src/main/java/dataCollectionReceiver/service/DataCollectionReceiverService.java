package dataCollectionReceiver.service;

import dataCollectionReceiver.Main;
import dataCollectionReceiver.activeMQ.abstractservices.ConsumerProducerService;
import dataCollectionReceiver.activeMQ.abstractservices.ConsumerService;
import dataCollectionReceiver.worker.DataCollectionCreator;

public class DataCollectionReceiverService extends ConsumerProducerService {
    public static String IN_QUEUE = "NewDataGatheringJob";
    public static String OUT_QUEUE = "PDFDataCollection";

    private final DataCollectionCreator dataCollectionCreator = new DataCollectionCreator();

    public DataCollectionReceiverService(){
        super(IN_QUEUE, OUT_QUEUE, Main.BROKER_URL);
    }

    @Override
    protected String execute(String input) {
        return dataCollectionCreator.collectDataForCustomer(input);
    }
}
