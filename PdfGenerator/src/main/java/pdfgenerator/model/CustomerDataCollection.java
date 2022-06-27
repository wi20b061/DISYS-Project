package pdfgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDataCollection {
    private Customer customer;
    private String invoiceID;
    private ArrayList<Charging> chargingData = new ArrayList<>();
}
