package com.example.restecharging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Authors: Lara Roth, Fiona Kumhofer, Akib Khan

@SpringBootApplication
public class ResteChargingApplication {
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {
        SpringApplication.run(ResteChargingApplication.class, args);
    }
}
