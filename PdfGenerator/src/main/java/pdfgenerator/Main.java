package pdfgenerator;

import pdfgenerator.activeMQ.Executor;
import pdfgenerator.service.PdfGeneratorService;
import pdfgenerator.worker.PdfGenerator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {

        List<Runnable> services = new ArrayList<>();

        services.add(new PdfGeneratorService());

        Executor executor = new Executor(services);
        executor.start();

    }
}
