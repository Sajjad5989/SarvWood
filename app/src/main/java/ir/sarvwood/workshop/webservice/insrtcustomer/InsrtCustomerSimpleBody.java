package ir.sarvwood.workshop.webservice.insrtcustomer;


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

public class InsrtCustomerSimpleBody {

    private String fullName;
    private String companyName;
    private String mobileNo;
    private String pass;
}
