package ir.sarvwood.workshop.webservice.changepassword;

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

    private int customerId;
    private String oldPass;
    private String newPass;
}
