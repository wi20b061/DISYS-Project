package dataCollectionReceiver.service;

import dataCollectionReceiver.Main;
import dataCollectionReceiver.activeMQ.Consumer;
import dataCollectionReceiver.activeMQ.abstractservices.ConsumerService;
import dataCollectionReceiver.activeMQ.abstractservices.ProducerService;

public class DataCollectionCreatorService {
    public static String IN_QUEUE = "StationDataForCustomer";

    // consumer direkt verwendet für rückgabewert
    public String execute() {
        //input is the data of one station
        String input = Consumer.receive(IN_QUEUE, 10000, Main.BROKER_URL);

        return input;
    }
}
