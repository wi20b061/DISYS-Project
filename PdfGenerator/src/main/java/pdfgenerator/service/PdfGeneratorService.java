package pdfgenerator.service;

import pdfgenerator.Main;
import pdfgenerator.activeMQ.abstractservices.ConsumerService;
import pdfgenerator.worker.PdfGenerator;

public class PdfGeneratorService extends ConsumerService {
    public static String IN_QUEUE = "PDFDataCollection";

    PdfGenerator pdfGenerator = new PdfGenerator();

    public PdfGeneratorService(){
        super(IN_QUEUE, Main.BROKER_URL);
    }
    protected void execute(String input) {


        pdfGenerator.generatePdf(input);
    }
}
