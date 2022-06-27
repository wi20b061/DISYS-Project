package pdfgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String idcustomer;
    private String fname;
    private String lname;
    private String address;
    private String zip;
    private String country;
}
