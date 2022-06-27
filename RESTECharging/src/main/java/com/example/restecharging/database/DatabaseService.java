package com.example.restecharging.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private String url = "jdbc:mysql://localhost:3306/jdbc-projekt";


    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, "root", "#Schubidu99");
    }
}
