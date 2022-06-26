package stationDataCollector.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import stationDataCollector.database.DatabaseService;
import stationDataCollector.model.Charging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

public class StationDataCollector {
    //input is JSON with all stationIDs and the customerID -> {"customerID": 1, "stationIDs": [1, 2]}
    public String getStationData(String input){

        System.out.println("test in stationdatacollector");

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

            int idCustomer = (int) jsonJavaRootObject.get("customerID");
            ArrayList<Integer> stationIDs = new Gson().fromJson((JsonElement) jsonJavaRootObject.get("stationIDs"), ArrayList.class);


            //READ from db by iterating over array of stations

            for(int i = 0; i < stationIDs.size(); i++){
                String queryRead = "SELECT * FROM charging WHERE idstation=? AND idcustomer=?";
                PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);
                preparedStatementRead.setInt(1, stationIDs.get(i)); //stationid
                preparedStatementRead.setInt(2, idCustomer);

                ResultSet resultSet = preparedStatementRead.executeQuery();

                //while loop to create list of objects from db results
                while(resultSet.next()){
                    Charging charging = new Charging(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getTimestamp(4)
                    );

                    stationData.add(charging);

                }

            }

            connection.close();
            //Convert List to JSON String
            JSONArray = objectMapper.writeValueAsString(stationData);
            System.out.println(JSONArray);

        }catch (Exception e){
            e.printStackTrace();
        }

        return JSONArray;
    }
}
