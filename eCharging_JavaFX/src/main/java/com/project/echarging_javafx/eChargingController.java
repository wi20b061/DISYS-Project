package com.project.echarging_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class eChargingController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField input;
    @FXML
    private Label message;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onGenerateButtonClick(){
        String url = "http://localhost:8080/invoice/";

        if(!input.getText().isBlank()){

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + input.getText()))
                    .POST(HttpRequest.BodyPublishers.ofString(input.getText()))
                    .build();

            try {
                HttpResponse<String> response = HttpClient.newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    //TO DO: return invoice PDF with download-link and creation time
                    message.setText(response.body());
                }
                if (response.statusCode() == 404) {
                    message.setText("ID not found");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}