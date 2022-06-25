package dataCollectionReceiver.service;

import dataCollectionReceiver.Main;
import dataCollectionReceiver.activeMQ.abstractservices.ConsumerProducerService;
import dataCollectionReceiver.worker.DataCollectionReceiver;


// we get collections of charging Data for every Station for one specific customer
// sends the whole CustomerDataCollection (charging Data + CustomerInfo)
public class DataCollectionReceiverService extends ConsumerProducerService {
    public static String IN_QUEUE = "StationDataForCustomer";
    public static String OUT_QUEUE = "PDFDataCollection";

    private final DataCollectionReceiver dataCollectionReceiver = new DataCollectionReceiver();

    public DataCollectionReceiverService(){
        super(IN_QUEUE, OUT_QUEUE, Main.BROKER_URL);
    }

    @Override
    protected String execute(String input) {
        return dataCollectionReceiver.collectDataForCustomer(input);
    }
}
