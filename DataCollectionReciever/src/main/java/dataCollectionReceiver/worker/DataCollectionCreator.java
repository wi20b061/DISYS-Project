package dataCollectionReceiver.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dataCollectionReceiver.Main;
import dataCollectionReceiver.activeMQ.Consumer;
import dataCollectionReceiver.activeMQ.Executor;
import dataCollectionReceiver.database.DatabaseService;
import dataCollectionReceiver.model.Charging;
import dataCollectionReceiver.model.Customer;
import dataCollectionReceiver.model.CustomerDataCollection;
import dataCollectionReceiver.service.DataCollectionCreatorService;
import dataCollectionReceiver.service.DataCollectionReceiverService;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Stream;

public class DataCollectionCreator {
    public String collectDataForCustomer(String input){
        //input is {"stationQty":"3","invoiceID":"2350", "customerID":"1"}
        //for every station a new consumer for data is created
        System.out.println(input);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        DatabaseService dbService = new DatabaseService();
        List<Charging> queueStationDataInput = new ArrayList<>();
        CustomerDataCollection customerDataCollection = new CustomerDataCollection();
        String jsonstring = "";
        try {
            Connection connection = dbService.connect();

            //get json data from input string
            Map jsonJavaRootObject = new Gson().fromJson(input, Map.class);

            String stationCount = (String)jsonJavaRootObject.get("stationQty");
            String customerID = (String)jsonJavaRootObject.get("customerID");
            String invoiceID = (String)jsonJavaRootObject.get("invoiceID");


            ArrayList<Charging> allChargings = new ArrayList<>();

            for(int i = 0; i < Integer.parseInt(stationCount); i++){

                DataCollectionCreatorService creatorService = new DataCollectionCreatorService();
                queueStationDataInput = creatorService.execute();
                allChargings.addAll(queueStationDataInput);

            }

            //get customer information from db
            String queryRead = "SELECT * FROM customer WHERE idcustomer=?";
            PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);
            preparedStatementRead.setInt(1, Integer.parseInt(customerID));
            ResultSet resultSet = preparedStatementRead.executeQuery();

            Customer customer = new Customer();

            while(resultSet.next()){
                customer.setIdcustomer(resultSet.getString(1));
                customer.setFname(resultSet.getString(2));
                customer.setLname(resultSet.getString(3));
                customer.setAddress(resultSet.getString(4));
                customer.setZip(resultSet.getString(5));
                customer.setCountry(resultSet.getString(6));
            }

            customerDataCollection.setCustomer(customer);
            customerDataCollection.setInvoiceID(invoiceID);
            customerDataCollection.setChargingData(allChargings);

            jsonstring = objectMapper.writeValueAsString(customerDataCollection);

        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonstring;
    }
}
