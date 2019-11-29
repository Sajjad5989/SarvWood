package ir.sarvwood.workshop.webservice.updatecustomerinfo;

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
public class UpdateCustomerInfoReturnValue {
   private int   id;
    private int  signout;
}
