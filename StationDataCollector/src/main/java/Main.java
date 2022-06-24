import model.Charging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //StationDataCollector:
        // -Gathers data for a specific customer from a specific charging station
        // -Sends data to the DataCollectionReciever


        DatabaseService dbService = new DatabaseService();

        try{
            Connection connection = dbService.connect();

            //READ
            // for every station a new query is started for the same customer
            String queryRead = "SELECT * FROM charging WHERE idcustomer=1";
            PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);

            ResultSet resultSet = preparedStatementRead.executeQuery();

            ArrayList<Charging> stationChargings = new ArrayList<Charging>();

            while(resultSet.next()){
                Charging charging = new Charging(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getTimestamp(4)
                );

                stationChargings.add(charging);
            }

            for(int i = 0; i < stationChargings.size(); i++){
                System.out.println(stationChargings.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
