package com.example.restecharging.service;

import com.example.restecharging.ResteChargingApplication;
import com.example.restecharging.activeMQ.abstactservices.ProducerService;

public class DataGatheringService extends ProducerService{
    public static final String OUT_QUEUE = "NewDataGatheringJob";

    public DataGatheringService(String input){
        super(input, OUT_QUEUE, ResteChargingApplication.BROKER_URL);
    }
}
