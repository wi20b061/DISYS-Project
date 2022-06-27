package dataCollectionReceiver.activeMQ.abstractservices;

import dataCollectionReceiver.activeMQ.Consumer;

public abstract class ConsumerService implements Runnable {
    private final String inQueue;
    private final String brokerUrl;

    public ConsumerService(String inQueue, String brokerUrl) {
        this.inQueue = inQueue;
        this.brokerUrl = brokerUrl;
    }

    @Override
    public void run() {
        while (true) {
            String input = Consumer.receive(inQueue, 10000, brokerUrl);

            if (null == input) {
                return;
            }

            execute(input);

        }
    }

    protected abstract String execute(String input);
}
