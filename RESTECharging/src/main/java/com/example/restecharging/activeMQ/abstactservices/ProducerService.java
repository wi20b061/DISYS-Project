package com.example.restecharging.activeMQ.abstactservices;

import com.example.restecharging.activeMQ.Producer;

public class ProducerService implements Runnable {
    private final String input;
    private final String outQueue;
    private final String brokerUrl;

    public ProducerService(String input, String outQueue, String brokerUrl) {
        this.input = input;
        this.outQueue = outQueue;
        this.brokerUrl = brokerUrl;
    }

    @Override
    public void run() {
        if (null == input) {
            return;
        }
        Producer.send(input, outQueue, brokerUrl);
    }
}
