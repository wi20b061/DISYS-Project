package com.example.restecharging.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StationGathering {

    public List<Integer> getStationIDs(){
        DatabaseService dbService = new DatabaseService();
        List<Integer> stationIDs = new ArrayList<>();
        try{
            Connection con = dbService.connect();
            //get a list of all Stations
            String query = "SELECT idstation FROM station WHERE available = true";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            while(result.next()) {
                stationIDs.add(result.getInt(1));
                System.out.println("Station ID: " + result.getInt(1) + "\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return stationIDs;
    }
}
