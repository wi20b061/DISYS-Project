package dataCollectionReceiver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDataCollection {
    private String customerID;
    private String fname;
    private String lname;
    private String address;
    private String zip;
    private String country;
    private String invoiceID;
    private ArrayList<Charging> chargingData = new ArrayList<>();
}
