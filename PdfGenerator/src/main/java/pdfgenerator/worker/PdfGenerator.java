package pdfgenerator.worker;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;


//Library: Apache PDFBox
//Resource: https://www.javatpoint.com/java-create-pdf

public class PdfGenerator {
    public void generatePdf(){
        //create pdf with data, safe it to file system
        try {
            PDDocument pdfdoc= new PDDocument();
            pdfdoc.addPage(new PDPage());
            //path where the PDF file will be store
            pdfdoc.save("C:");
            //prints the message if the PDF is created successfully
            System.out.println("PDF created");
            //closes the document
            pdfdoc.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
