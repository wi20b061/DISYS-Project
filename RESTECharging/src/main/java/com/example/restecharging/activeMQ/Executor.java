package com.example.restecharging.activeMQ;

import com.example.restecharging.activeMQ.abstactservices.ConsumerProducerService;

import java.util.List;

public class Executor {
    private final List<Runnable> services;

    public Executor(List<Runnable> services) {
        this.services = services;
    }

    public void start() {
        for (Runnable service: services) {
            Thread thread = new Thread(service);
            thread.start();
        }
    }
}
