package com.example.restecharging;

import com.example.restecharging.activeMQ.Executor;
import com.example.restecharging.service.DataGatheringService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ResteChargingApplication {
    public static final String BROKER_URL = "tcp://localhost:61616";
    public int id;

    public static void main(String[] args) {
        SpringApplication.run(ResteChargingApplication.class, args);

        List<Runnable> services = new ArrayList<>();

        // put some call for waiters in the queue
        services.add(new DataGatheringService("1"));

        // run the whole application
        Executor executor = new Executor(services);
        executor.start();
    }

}
