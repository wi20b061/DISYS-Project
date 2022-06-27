package dataCollectionReceiver.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataCollectionReceiver.Main;
import dataCollectionReceiver.activeMQ.Consumer;
import dataCollectionReceiver.model.Charging;

import java.util.ArrayList;
import java.util.List;

public class DataCollectionCreatorService {
    public static String IN_QUEUE = "StationDataForCustomer";

    // consumer direkt verwendet für rückgabewert
    public List<Charging> execute() {
        //input is the data of one station
        String input = Consumer.receive(IN_QUEUE, 10000, Main.BROKER_URL);

        List<Charging> data = new ArrayList<>();
        try {

             data = new ObjectMapper().readValue(input, new TypeReference<List<Charging>>(){});


        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
