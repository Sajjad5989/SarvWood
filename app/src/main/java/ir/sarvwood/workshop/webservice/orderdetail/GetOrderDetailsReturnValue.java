package ir.sarvwood.workshop.webservice.orderdetail;

import java.io.Serializable;
import java.util.List;

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
public class GetOrderDetailsReturnValue<T> implements Serializable {


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
    private int updteTime;
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
    private String discardDesc;
    private List<T> items;

}
