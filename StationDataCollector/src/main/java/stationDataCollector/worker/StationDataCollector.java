package stationDataCollector.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mysql.cj.xdevapi.JsonString;
import stationDataCollector.database.DatabaseService;
import stationDataCollector.model.Charging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StationDataCollector {
    //input is Station stationID+customerID
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

            // extract station id and customer id from input string
            int idCustomer = Integer.parseInt(input.substring(2,3));
            int idStation = Integer.parseInt(input.substring(0,1));

            //READ from db
            String queryRead = "SELECT * FROM charging WHERE idstation=? AND idcustomer=?";
            PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);
            preparedStatementRead.setInt(1, idStation);
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

            connection.close();
            //Convert List to JSON String
            JSONArray = objectMapper.writeValueAsString(stationData);

        }catch (Exception e){
            e.printStackTrace();
        }

        return JSONArray;
    }
}
