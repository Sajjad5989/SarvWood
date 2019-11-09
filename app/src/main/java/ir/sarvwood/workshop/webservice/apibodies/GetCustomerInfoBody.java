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
public class GetCustomerInfoBody {

    private String username;
    private String pass;
    private String applicationVersion;
    private String sdkVersion;
    private String deviceName;
    private String deviceModel;
}
