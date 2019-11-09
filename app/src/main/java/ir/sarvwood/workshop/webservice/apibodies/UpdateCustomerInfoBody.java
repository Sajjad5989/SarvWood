package ir.sarvwood.workshop.webservice.apibodies;


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
public class UpdateCustomerInfoBody {

    private Integer customerId;
    private String fullName;
    private String companyName;
    private String phone;
    private String address;
    private String longtiude;
    private String latitiude;
}
