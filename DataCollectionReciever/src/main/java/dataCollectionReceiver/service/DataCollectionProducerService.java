package dataCollectionReceiver.service;

import dataCollectionReceiver.Main;
import dataCollectionReceiver.activeMQ.abstractservices.ProducerService;

public class DataCollectionProducerService extends ProducerService {
    public static String OUT_QUEUE = "PDFDataCollection";

    public DataCollectionProducerService(String input, String outQueue, String brokerUrl) {
        super(input, OUT_QUEUE, Main.BROKER_URL);

    }
}
