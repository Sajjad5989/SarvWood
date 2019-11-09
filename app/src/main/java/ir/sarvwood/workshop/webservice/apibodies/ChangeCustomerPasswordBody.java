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

public class ChangeCustomerPasswordBody {

    private Integer customerId;
    private String oldPass;
    private String newPass;
}
