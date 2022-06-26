package dataCollectionReceiver.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import dataCollectionReceiver.activeMQ.Executor;
import dataCollectionReceiver.database.DatabaseService;
import dataCollectionReceiver.service.DataCollectionCreatorService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataCollectionCreator {
    public String collectDataForCustomer(String input){
        //input is {"stationQty":"3","invoiceID":"2350"}
        //for every station a new consumer for data is created

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        DatabaseService dbService = new DatabaseService();
        List<Runnable> services = new ArrayList<>();

        try {
            Connection connection = dbService.connect();

            //get json data from input string
            Map jsonJavaRootObject = new Gson().fromJson(input, Map.class);

            String stationCount = (String)jsonJavaRootObject.get("stationQty");
            String customerID = (String)jsonJavaRootObject.get("invoiceID");

            /*for(int i = 0; i < Integer.parseInt(stationCount); i++){
                services.add(new DataCollectionCreatorService());
            }*/



            Executor executor = new Executor(services);
            executor.start();

        }catch (Exception e){
            e.printStackTrace();
        }

        return "pdfPath for userDataCollection";
    }
}
