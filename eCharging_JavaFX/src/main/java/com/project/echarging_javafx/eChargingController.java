package com.project.echarging_javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class eChargingController {
    @FXML
    private TextField input;
    @FXML
    private Label message;
    @FXML
    private Hyperlink pdfLink;

    public String invoiceID = "";

    private String url = "http://localhost:8080/invoice/";
    @FXML
    protected void onPdfLinkClick(){

    }

    @FXML
    protected void onGenerateButtonClick(){
        // String invoiceID = "";

        //if a customerID was typed in
        if(!input.getText().isBlank()) {
            //send a post request to Springboot-Server to start the invoice-process for the specific customer
            HttpRequest postrequest = HttpRequest.newBuilder()
                    .uri(URI.create(url + input.getText()))
                    .POST(HttpRequest.BodyPublishers.ofString(input.getText()))
                    .build();
            try {
                //as a response the new invoiceID will be recieved
                HttpResponse<String> postresponse = HttpClient.newHttpClient()
                        .send(postrequest, HttpResponse.BodyHandlers.ofString());

                if (postresponse.statusCode() == 200) {
                    //TO DO: return invoice PDF with download-link and creation time
                    //message.setText(response.body());
                    System.out.println("test");
                    invoiceID = postresponse.body();
                    message.setText("Invoice will be generated...");
                }
                if (postresponse.statusCode() == 404) {
                    message.setText("Error: Sending CustomerID was not successful!");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(invoiceID);
            if(invoiceID != ""){
                new Thread(
                        ()->{
                            sendGetRequest(invoiceID);
                        })
                        .start();
            }
        }

    }

    protected void sendGetRequest(String invoiceID){
        //get invoicePath of new invoice -> try to find get an answer every 2 seconds
        HttpRequest getrequest = HttpRequest.newBuilder()
                .uri(URI.create(url + invoiceID))
                .GET()
                .build();
        try{
            HttpResponse<String> getresponse = HttpClient.newHttpClient()
                    .send(getrequest, HttpResponse.BodyHandlers.ofString());
            if(getresponse.statusCode() == 200){
                Platform.runLater(() -> {
                    pdfLink.setVisible(true);
                    pdfLink.setText(getresponse.body());
                });
            }
            if(getresponse.statusCode() == 404){
                Platform.runLater(() -> {
                    message.setText("No Invoice found");
                });
            }
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}