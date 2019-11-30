package ir.sarvwood.workshop.webservice.myorders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class MyOrderReturnValue {


    private int orderId;
    private int customerId;
    private String customerFullName;
    private String customerCompanyName;
    private String desc;
    private int insrtTime;
    private String insrtDatePersian;
    private String insrtDatePersian1;
    private String insrtDatePersian2;
    private String insrtTimeSimple;
    private String insrtTimePersian;
    private String updteTime;
    private String updteDatePersian;
    private String updteDatePersian1;
    private String updteDatePersian2;
    private String updteTimeSimple;
    private String updteTimePersian;
    private String trackingCode;
    private int state;
    private String stateTitle;
    private int sheetSupplier;
    private String sheetSupplierTitle;

}
