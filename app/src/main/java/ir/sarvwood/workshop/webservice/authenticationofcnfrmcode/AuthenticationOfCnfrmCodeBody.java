package ir.sarvwood.workshop.webservice.authenticationofcnfrmcode;


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


public class AuthenticationOfCnfrmCodeBody {

    private String mobileNo;
    private String cofirmationCode;
    private String applicationVersion;
    private String sdkVersion;
    private String deviceName;
    private String deviceModel;
}
