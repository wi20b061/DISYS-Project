package com.example.restecharging.controllers;


import com.example.restecharging.activeMQ.Executor;
import com.example.restecharging.database.StationGathering;
import com.example.restecharging.service.DataGatheringService;
import com.example.restecharging.service.NewDataGateringJob;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EChargingController {

    /*private ArrayList<Integer> allIDs = new ArrayList<>(){
        {
            add(1);
        }
        {
            add(2);
        }
    };

    //Returns Cutomser invoice
    @GetMapping(value = "/invoices", produces = "application/json")
    public ArrayList<Integer> getAllIDs(){
        return allIDs;
    }

    }*/

    //start of getting a invoice for the specific customer
    @PostMapping(path = "/invoice/{id}", produces = "application/json")
    public boolean postUserID(@PathVariable int id){
        //**** for StationDataCollector ****
        //get stationIDs from all available stations
        //StationGathering gatherIDs = new StationGathering();
        //List<Integer> stationIDs = gatherIDs.getStationIDs();
        List<Integer> stationIDs = new ArrayList<>();//this is just for testing until db is working..
        stationIDs.add(1);
        stationIDs.add(2);
        List<Runnable> services = new ArrayList<>();
        // put a call for every available station into the queue + give customerID
        stationIDs.forEach((n) -> services.add(new DataGatheringService(Integer.toString(n)+ id)));

        //**** for DataCollectionReceiver ****
        //add service that will inform DataCollectionReceiver about the new invoice-job with
        //the amount of station-information that will be sent from the StationDataCollector
        services.add(new NewDataGateringJob(Integer.toString(stationIDs.size())));

        // run the whole application
        Executor executor = new Executor(services);
        executor.start();

        //return true to frontend to signalize that the start of the process was successful
        return true;
    }


    @GetMapping(path = "/invoice/{id}")
    public String getInvoice(@PathVariable int id){
        //alle paar sekunden schauen ob eine invoice gepostet wurde
        return "invoice nr: " + id;
    }


}
