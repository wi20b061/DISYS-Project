package stationDataCollector.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Charging {
    private int idcharging;
    private int idstation;
    private int idcustomer;
    private int kwh;
    private Timestamp datetime;
    private boolean usedForInvoice;
}
