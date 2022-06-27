package dataCollectionReceiver.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import dataCollectionReceiver.Main;
import dataCollectionReceiver.activeMQ.Consumer;
import dataCollectionReceiver.activeMQ.Executor;
import dataCollectionReceiver.database.DatabaseService;
import dataCollectionReceiver.service.DataCollectionCreatorService;
import dataCollectionReceiver.service.DataCollectionReceiverService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataCollectionCreator {
    public String collectDataForCustomer(String input){
        //input is {"stationQty":"3","invoiceID":"2350", "customerID":"1"}
        //for every station a new consumer for data is created
        System.out.println(input);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        DatabaseService dbService = new DatabaseService();
        List<String> queueStationDataInput = new ArrayList<>();

        try {
            Connection connection = dbService.connect();

            //get json data from input string
            Map jsonJavaRootObject = new Gson().fromJson(input, Map.class);

            String stationCount = (String)jsonJavaRootObject.get("stationQty");
            String customerID = (String)jsonJavaRootObject.get("customerID");
            String invoiceID = (String)jsonJavaRootObject.get("invoiceID");

            //get customer information from db
            String queryRead = "SELECT * FROM customer WHERE idcustomer=?";
            PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);
            preparedStatementRead.setInt(1, Integer.parseInt(customerID));
            ResultSet resultSet = preparedStatementRead.executeQuery();
            System.out.println(resultSet);

            //get messages from queue with the customerStation Data
            for(int i = 0; i < Integer.parseInt(stationCount); i++){
                DataCollectionCreatorService creatorService = new DataCollectionCreatorService();
                queueStationDataInput.add(creatorService.execute());
            }

            System.out.println(queueStationDataInput.get(0));
            System.out.println(queueStationDataInput.get(1));
            System.out.println(queueStationDataInput.get(2));



            //Executor executor = new Executor(services);
            //executor.start();

        }catch (Exception e){
            e.printStackTrace();
        }

        return "pdfPath for userDataCollection";
    }
}
