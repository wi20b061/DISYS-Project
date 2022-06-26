package com.example.restecharging.controllers;


import com.example.restecharging.activeMQ.Executor;
import com.example.restecharging.database.StationGathering;
import com.example.restecharging.model.CollectorObject;
import com.example.restecharging.service.DataGatheringService;
import com.example.restecharging.service.NewDataGateringJob;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    @PostMapping(path = "/invoice/{customerID}", produces = "application/json")
    public String postUserID(@PathVariable int customerID){
        //generate new random jobID
        Random r = new Random();
        //String invoiceID = String.format("%4d", Integer.valueOf(r.nextInt(10001)));
        String invoiceID = UUID.randomUUID().toString();
        List<Runnable> services = new ArrayList<>();
        //**** for StationDataCollector ****
        //get stationIDs from all available stations
        StationGathering gatherIDs = new StationGathering();
        List<Integer> stationIDs = gatherIDs.getStationIDs();
        /*without db connection
            List<Integer> stationIDs = new ArrayList<>();//this is just for testing until db is working..
            stationIDs.add(1);
            stationIDs.add(2);
         */
        // for a dataGeathering message into the queue & provides customerID
        HashMap<String, String> collectorhashmap = new HashMap<>();
        CollectorObject object = new CollectorObject(customerID, stationIDs);

        //Convert List to JSON String
        String collectorjson = null;
        try {
            // ObjectMapper to convert object to JSON(resource https://makeinjava.com/convert-array-objects-json-jackson-objectmapper/)
            collectorjson = new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        services.add(new DataGatheringService(collectorjson));

        //**** for DataCollectionReceiver ****
        //add service that will inform DataCollectionReceiver about the new invoice-job with
        //the amount of station-information that will be sent from the StationDataCollector
        HashMap<String, String> receiverhashmap = new HashMap<>();
        receiverhashmap.put("stationQty", Integer.toString(stationIDs.size())); //{ "stationQty : 3",
        receiverhashmap.put("invoiceID", invoiceID); //"invoiceID" : "2353" }
        //Convert List to JSON String
        String receiverjson = null;
        try {
            // ObjectMapper to convert array of objects to JSON(resource https://makeinjava.com/convert-array-objects-json-jackson-objectmapper/)
            receiverjson = new ObjectMapper().writeValueAsString(receiverhashmap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        services.add(new NewDataGateringJob(receiverjson));

        // run the whole application
        Executor executor = new Executor(services);
        executor.start();
        //return true to frontend to signalize that the start of the process was successful
        return invoiceID;
    }


    @GetMapping(path = "/invoice/{invoiceID}")
    public String getInvoice(@PathVariable String invoiceID){
        //try to find invoice 10 times - every 5 seconds, if no invoice-file was found return "not found"
        for(int i=0; i<10;i++){
            //search for invoice at specified invoiceID-path (invoiceID=jobID)
            try {
                File invoice = new File("DISYS_Projekt/invoices/" + invoiceID + ".pdf");
                //File invoice = new File("C:/Users/Fiona/IdeaProjects/DISYS_Projekt/invoices/test.pdf"); //TESTING!
                Scanner myReader = new Scanner(invoice);
                //return path if file exists
                return "DISYS_Projekt/invoices/" + invoiceID + ".pdf";
                //return "C:/Users/Fiona/IdeaProjects/DISYS_Projekt/invoices/test.pdf"; //TESTING!
            } catch (Exception e) {
                System.out.println("Invoice-pdf not found");
            }
            /*
            File invoice = new File("DISYS_Projekt/invoices/" + invoiceID);
            if (invoice.exists()) {
                return "DISYS_Projekt/invoices/" + invoiceID;
            }*/
            try {
                System.out.println("5 Wait  seconds");
                Thread.sleep(5000);
                System.out.println("5 seconds are over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);//404: "no invoice found";
       /* ConsumeInvoiceService invoiceService  = new ConsumeInvoiceService();
        //the path of the invoice-pdf is sent back to the Frontend
        return invoiceService.execute();*/

    }


}
