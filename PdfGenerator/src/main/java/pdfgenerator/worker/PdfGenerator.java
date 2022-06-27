package pdfgenerator.worker;



import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import pdfgenerator.model.Charging;
import pdfgenerator.model.Customer;
import pdfgenerator.model.CustomerDataCollection;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


//Library: Apache PDFBox
//Resource: https://www.javatpoint.com/java-create-pdf

public class PdfGenerator {
    public void generatePdf(CustomerDataCollection customerDataCollection){

        //create pdf with data, safe it to file system
        //created PDF document instance
        //generate Testdata
        Customer customer = customerDataCollection.getCustomer();
        String fname = customer.getFname();
        String lname = customer.getLname();
        String address = customer.getAddress();
        String zip = customer.getZip();
        String country = customer.getCountry();
        String invoiceID = customerDataCollection.getInvoiceID();
        ArrayList<Charging> allChargings = customerDataCollection.getChargingData();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String invoiceDate = dtf.format(now);

        List<Charging> chargingList = new ArrayList<>();
        chargingList.add(new Charging(1,1,1,600, "22-01-2023" , false));
        chargingList.add(new Charging(2,3,1,600, "22-01-2023" , false));
        //created PDF document instance
        Document doc = new Document();
        try
        {
            //generate a PDF at the specified location
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\Fiona\\IdeaProjects\\DISYS_Projekt\\invoices\\" + invoiceID +".pdf"));
            System.out.println("PDF created.");
            //opens the PDF
            doc.open();
            //adding customer information
            doc.add(new Paragraph(String.format("%s %s\n%s\n%s\n%s\n\n", fname, lname, address, zip + " Wien", country)));
            //adding company information
            doc.add(new Paragraph(String.format("%s\n%s\n%s\n%s\n\n", "Company GmbH", "Adiradstraße 22", "1020 Wien", "AT")));
            //adding invoice number and date
            doc.add(new Paragraph(String.format("Invoice Number: %s\n", invoiceID)));
            doc.add(new Paragraph(String.format("Invoice Date: %s\n\n",  invoiceDate)));
            int i = 0;
            doc.add(new Paragraph((String.format("|%1$-10s|%2$-10s|%3$-10s|%4$-22s|\n",  "Row Number", "Station Number", "KWH", "Datetime"))));
            for (Charging charging : chargingList) {
                doc.add(new Paragraph(String.format("|%20d%s%24d%s%12d%s%20s|", i,"|", charging.getIdstation(),"|", charging.getKwh(),"|", charging.getDatetime())));
                i++;
            }
            //close the PDF file
            doc.close();
            //closes the writer
            writer.close();
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


        //this below is working:
        /*try {
            FileOutputStream fos = new FileOutputStream("C:/Users/Fiona/IdeaProjects/DISYS_Projekt/invoices/test3.pdf");
            System.out.println("File generated");
            PDDocument invoice = new PDDocument();


            PDDocument pdfdoc= new PDDocument();
            pdfdoc.addPage(new PDPage());
            //path where the PDF file will be store
            pdfdoc.save("C:/Users/Fiona/IdeaProjects/DISYS_Projekt/invoices/test2.pdf");
            //prints the message if the PDF is created successfully
            System.out.println("PDF created");
            //closes the document
            pdfdoc.close();

        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
