import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {

        //StationDataReceiver:
        // - Receives all collected data
        // - Sort the data to the according gathering job
        // - Sends data to the PdfGenerator when the data is complete


        DatabaseService dbService = new DatabaseService();

        try{
            Connection connection = dbService.connect();

            //READ
            // for every station a new query is started for the same customer
            String queryRead = "SELECT * FROM customer WHERE idcustomer=1";
            PreparedStatement preparedStatementRead = connection.prepareStatement(queryRead);

            ResultSet resultSet = preparedStatementRead.executeQuery();


            while(resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getString(6)
                );

                System.out.println("Customer: "+customer.getIdcustomer()+", "+customer.getFname()+", "+customer.getLname()+", "+customer.getAddress()+", "+customer.getZip()+", "+customer.getCountry());
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
