package activeMQ.abstactservices;

import activeMQ.Consumer;
import activeMQ.Producer;

public abstract class ConsumerProducerService implements Runnable {

    private final String inQueue;
    private final String outQueue;
    private final String brokerUrl;

    public ConsumerProducerService(String inQueue, String outQueue, String brokerUrl) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.brokerUrl = brokerUrl;
    }

    @Override
    public void run() {
        while (true) {
            String input = Consumer.receive(inQueue, 10000, brokerUrl);

            if (null == input) {
                return;
            }

            String output = execute(input);

            Producer.send(output, outQueue, brokerUrl);
        }
    }

    protected abstract String execute(String input);
}
