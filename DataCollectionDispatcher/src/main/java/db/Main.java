package db;

import db.tables.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {

        DatabaseService dbService = new DatabaseService();

        try {
            Connection connection = dbService.connect();

            //CREATE
            /*
            String queryCreate = "INSERT INTO customer (fname, lname, address, zip, country) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementCreate = connection.prepareStatement(queryCreate);

            preparedStatementCreate.setString(1, "Max");
            preparedStatementCreate.setString(2, "Mustermann");
            preparedStatementCreate.setString(3, "Teststraße 4");
            preparedStatementCreate.setInt(4, 4444);
            preparedStatementCreate.setString(5, "Austria");

            preparedStatementCreate.execute();
            */
            //READ
            String queryRead = "SELECT * FROM customer";
            PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);

            ResultSet resultSet = preparedStatementRead.executeQuery();

            while(resultSet.next()){
                System.out.println(resultSet.getString("fname"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
