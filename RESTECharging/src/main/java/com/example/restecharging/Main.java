/*package com.example.restecharging;

import com.example.restecharging.activeMQ.Executor;
import com.example.restecharging.service.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {
        List<Runnable> services = new ArrayList<>();

        // put some call for waiters in the queue
        services.add(new DataGatheringService("1"));

        // run the whole application
        Executor executor = new Executor(services);
        executor.start();
    }
}*/
