package com.example.restecharging.controllers;


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

    @PostMapping(value = "/invoices", produces = "application/json")
    public Integer addID(){

    }*/
    @PostMapping(path = "/invoice/{id}", produces = "application/json")
    public boolean postUserID(@PathVariable int id){//(@RequestBody final String id){
        List<Integer> =


        return true;

        /*return switch (id){
            case 1 -> "Customer 1";
            case 2 -> "Customer 2";
            default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        };*/
    }


    @GetMapping(path = "/invoice/{id}")
    public String getInvoice(@PathVariable int id){
        //alle paar sekunden schauen ob eine invoice gepostet wurde
        return "invoice nr: " + id;
    }


}
