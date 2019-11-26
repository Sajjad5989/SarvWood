package ir.sarvwood.workshop.webservice.getcustomerinfo;

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

public class GetCustomerInfoReturnValue {
    private int customerId;
    private String fullName;
    private String companyName;
    private String phone;
    private String address;
    private String longtiude;
    private String latitiude;
    private String code;
    private String desc;
    private int lastLoginDate;
    private String lastLoginDatePersian;
    private String lastLoginIP;
    private int membershipTime;
    private String insrtTimePersian;
    private int insrtTime;
    private String accessToken;


}
