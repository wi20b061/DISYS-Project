package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private int idcustomer;
    private String fname;
    private String lname;
    private String address;
    private int zip;
    private String country;
}
