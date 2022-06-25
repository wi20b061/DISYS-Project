package dataCollectionReceiver;

import dataCollectionReceiver.activeMQ.Executor;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {
        //DataCollectionReceiver:


        List<Runnable> services = new ArrayList<>();



        //services.add();


        Executor executor = new Executor(services);
        executor.start();

    }
}
