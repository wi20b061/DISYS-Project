/*package com.example.restecharging.service;

import com.example.restecharging.ResteChargingApplication;
import com.example.restecharging.activeMQ.Consumer;
import com.example.restecharging.activeMQ.abstactservices.ConsumerService;

public class ConsumeInvoiceService {
    public static final String IN_QUEUE = "GetInvoiceData";

    public String execute() {
        //for input the path to the invoice pdf should be given
        return Consumer.receive(IN_QUEUE, 10000, ResteChargingApplication.BROKER_URL);
    }
}*/
