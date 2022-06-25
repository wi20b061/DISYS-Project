package com.example.restecharging.controllers;


import com.example.restecharging.activeMQ.Executor;
import com.example.restecharging.database.StationGathering;
import com.example.restecharging.service.DataGatheringService;
import com.example.restecharging.service.NewDataGateringJob;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class EChargingController {

    /*
    //Returns Cutomser invoice
    @GetMapping(value = "/invoices", produces = "application/json")
    public ArrayList<Integer> getAllIDs(){
        return allIDs;
    }

    }*/

    //start of getting a invoice for the specific customer
    @PostMapping(path = "/invoice/{id}", produces = "application/json")
    public boolean postUserID(@PathVariable int id){

        //generate new random jobID
        Random r = new Random();
        String invoiceID = String.format("%4d", Integer.valueOf(r.nextInt(10001)));


        List<Runnable> services = new ArrayList<>();

        //**** for StationDataCollector ****
        //get stationIDs from all available stations
        //StationGathering gatherIDs = new StationGathering();
        //List<Integer> stationIDs = gatherIDs.getStationIDs();
        List<Integer> stationIDs = new ArrayList<>();//this is just for testing until db is working..
        stationIDs.add(1);
        stationIDs.add(2);
        // for a dataGeathering message into the queue & provides customerID
        services.add(new DataGatheringService(String.format("%d", id)));

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


    @GetMapping(path = "/invoice/{invoiceID}")
    public String getInvoice(@PathVariable int invoiceID){
        //watch time and wait for 1 minute, if no invoice-file was found in that time return "not found"
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() < startTime + 60000){
            //search for invoice at specified invoiceID-path (invoiceID=jobID)
            File invoice = new File("DISYS_Projekt/invoices/" + invoiceID);
            if (invoice.exists()) {
                return "DISYS_Projekt/invoices/" + invoiceID;
            }
        }
        return "no invoice found";
       /* ConsumeInvoiceService invoiceService  = new ConsumeInvoiceService();
        //the path of the invoice-pdf is sent back to the Frontend
        return invoiceService.execute();*/
    }


}
