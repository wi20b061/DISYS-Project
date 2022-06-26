package stationDataCollector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import stationDataCollector.activeMQ.Executor;
import stationDataCollector.database.DatabaseService;
import stationDataCollector.model.Charging;
import stationDataCollector.service.StationDataCollectorService;

import java.sql.*;
import java.util.*;

public class Main {
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {
        //StationDataCollector:
        // -Gathers data for a specific customer from a specific charging station
        // -Sends data to the DataCollectionReciever

        List<Runnable> services = new ArrayList<>();

        // as many services as stations ?

        services.add(new StationDataCollectorService());


        Executor executor = new Executor(services);
        executor.start();


        /*
        HashMap<String, Charging> chargings = new HashMap<>();

        chargings.put("object1", new Charging(1, 1, 1, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())));
        chargings.put("object2", new Charging(2, 2, 2, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())));


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String JSONArray = "";
        try{
            JSONArray = objectMapper.writeValueAsString(chargings);

        }catch (Exception e){
            e.printStackTrace();
        }

        //System.out.println(JSONArray);

        //Map jsonJavaRootObject = new Gson().fromJson(JSONArray, Map.class);

        //System.out.println(jsonJavaRootObject.get("object2"));



        ArrayList<Integer> testis = new ArrayList<>();
        testis.add(1);
        testis.add(2);
        String JsonTest = "";
        try {
            JsonTest = objectMapper.writeValueAsString(testis);
            System.out.println(JsonTest);
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<Integer> jsonJavaRootObject = new Gson().fromJson(JsonTest, ArrayList.class);
        System.out.println(jsonJavaRootObject);*/
        /*
        DatabaseService dbService = new DatabaseService();
        ArrayList<Integer> stationData = new ArrayList<>();
        try{
            Connection connection = dbService.connect();
            String queryRead = "SELECT idcharging FROM charging";
            PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);


            ResultSet resultSet = preparedStatementRead.executeQuery();

            //while loop to create list of objects from db results
                while(resultSet.next()) {


                    stationData.add(resultSet.getInt(1));
                    System.out.println(stationData);
                }
            }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
