package pdfgenerator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import pdfgenerator.Main;
import pdfgenerator.activeMQ.abstractservices.ConsumerService;
import pdfgenerator.model.CustomerDataCollection;
import pdfgenerator.worker.PdfGenerator;

import java.util.List;
import java.util.Map;

public class PdfGeneratorService extends ConsumerService {
    public static String IN_QUEUE = "PDFDataCollection";

    PdfGenerator pdfGenerator = new PdfGenerator();

    public PdfGeneratorService(){
        super(IN_QUEUE, Main.BROKER_URL);
    }
    protected void execute(String input) {


        CustomerDataCollection customerDataCollection = new Gson().fromJson(input, CustomerDataCollection.class);
        System.out.println(customerDataCollection);

        pdfGenerator.generatePdf(customerDataCollection);
    }
}
