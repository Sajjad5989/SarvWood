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
public class SendSmsOfCnfrmCodeBody {


    private String  mobileNo;
}
