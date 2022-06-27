package stationDataCollector.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import stationDataCollector.activeMQ.Executor;
import stationDataCollector.database.DatabaseService;
import stationDataCollector.model.Charging;
import stationDataCollector.service.StationCounterService;
import stationDataCollector.service.StationDataCollectorService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationCounter {
    public void getDataForStations(String input){
        // ObjectMapper to convert array of objects to JSON(resource https://makeinjava.com/convert-array-objects-json-jackson-objectmapper/)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);



        //Get data from db for specific customer and station

        DatabaseService dbService = new DatabaseService();
        //String JSONArray = "";

        try {

            Connection connection = dbService.connect();

            // extract station id and customer id from input string JSON
            Map jsonJavaRootObject = new Gson().fromJson(input, Map.class);

            double cID = (double)jsonJavaRootObject.get("customerID");
            int idCustomer = (int) cID;


            ArrayList<Double> stationIDs = new ArrayList<>();
            stationIDs = (ArrayList<Double>) jsonJavaRootObject.get("stationIDs");

            //da wir wenn wir den json strin auslesen doubles erhalten müssen wir arraylist in type integer umwandeln
            ArrayList<Integer> stationIDsInt = new ArrayList<>();
            ArrayList<ResultSet> stationResults = new ArrayList<>();

            for(int j = 0; j <stationIDs.size(); j++){
                stationIDsInt.add(stationIDs.get(j).intValue());
            }

            List<Runnable> services = new ArrayList<>();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            String JSONObject = "";

            for(int i = 0; i < stationIDsInt.size(); i++){
                String queryRead = "SELECT * FROM charging WHERE idstation=? AND idcustomer=?";
                PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);
                System.out.println(stationIDsInt.get(i));
                preparedStatementRead.setInt(1, stationIDsInt.get(i));
                preparedStatementRead.setInt(2, idCustomer);

                ResultSet resultSet = preparedStatementRead.executeQuery();
                stationResults.add(resultSet);
                List<Charging> stationData = new ArrayList<Charging>();
                //while loop to create list of objects from db results
                while(resultSet.next()){
                    Charging charging = new Charging(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getTimestamp(5),
                            resultSet.getBoolean(6)

                    );

                    stationData.add(charging);

                }
                //Convert List to JSON String




                // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
                // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
                 JSONObject = gson.toJson(stationData);


                services.add(new StationDataCollectorService(JSONObject));

            }


            connection.close();
            Executor executor = new Executor(services);
            executor.start();

        }catch (Exception e){
            e.printStackTrace();
        }

        /*
        List<Runnable> services = new ArrayList<>();

        //hier StationDataServiceAufrufen: für jede Station soll eine eigene message in die queue geschickt werden
        for(int i = 0; i <stationIDsInt.size(); i++){
            services.add(new StationDataCollectorService());
        }
        Executor executor = new Executor(services);
        executor.start();*/

    }
}
