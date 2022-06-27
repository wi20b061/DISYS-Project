package pdfgenerator;

import pdfgenerator.worker.PdfGenerator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {
        PdfGenerator pdfGenerator = new PdfGenerator();
        pdfGenerator.generatePdf();
    }
}
