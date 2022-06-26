package com.example.restecharging;

import com.example.restecharging.database.DatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class ResteChargingApplication {
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) {
        SpringApplication.run(ResteChargingApplication.class, args);
    }
}
