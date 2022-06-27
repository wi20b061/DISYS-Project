package stationDataCollector.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import stationDataCollector.database.DatabaseService;
import stationDataCollector.model.Charging;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StationDataCollector {
    //input is JSON with all stationIDs and the customerID -> {"customerID": 1, "stationIDs": [1, 2]}
    public String getStationData(String input){

        // ObjectMapper to convert array of objects to JSON(resource https://makeinjava.com/convert-array-objects-json-jackson-objectmapper/)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ArrayList<Charging> stationData = new ArrayList<Charging>();

        //Get data from db for specific customer and station

        DatabaseService dbService = new DatabaseService();
        String JSONArray = "";

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

            for(int i = 0; i < stationIDsInt.size(); i++){
                String queryRead = "SELECT * FROM charging WHERE idstation=? AND idcustomer=? AND usedForInvoice=?";
                PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);
                System.out.println(stationIDsInt.get(i));
                preparedStatementRead.setInt(1, stationIDsInt.get(i));
                preparedStatementRead.setInt(2, idCustomer);
                preparedStatementRead.setInt(3, 1);

                // charging isInInvoice auf 1 setzen
                String queryUpdate = "UPDATE charging SET usedForInvoice=1 WHERE idcustomer=? AND idstation=? ";
                PreparedStatement preparedStatementUpdate = connection.prepareStatement(queryUpdate);

                preparedStatementUpdate.setInt(1, stationIDsInt.get(i));
                preparedStatementUpdate.setInt(2, idCustomer);

                ResultSet resultSet = preparedStatementRead.executeQuery();
                stationResults.add(resultSet);

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

            }
            System.out.println(stationResults);

            connection.close();
            //Convert List to JSON String
            JSONArray = objectMapper.writeValueAsString(stationData);




        }catch (Exception e){
            e.printStackTrace();
        }

        return JSONArray;
    }
}
