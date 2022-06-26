package com.example.restecharging.model;

import java.util.ArrayList;
import java.util.List;

public class CollectorObject {
    private int customerID;
    private List<Integer> stationIDs = new ArrayList<>();

    public CollectorObject(int customerID, List<Integer> stationIDs){
        this.customerID = customerID;
        this.stationIDs = stationIDs;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public List<Integer> getStationIDs() {
        return stationIDs;
    }

    public void setStationIDs(List<Integer> stationIDs) {
        this.stationIDs = stationIDs;
    }
}
