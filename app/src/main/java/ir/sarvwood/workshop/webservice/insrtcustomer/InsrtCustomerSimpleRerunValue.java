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
public class InsrtCustomerSimpleRerunValue {

    private int id;
    private String personCode;
    private String accessToken;
}
