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
public class SaveDeviceInfoBody {

    private Integer customerId;
    private String applicationVersion;
    private String sdkVersion;
    private String deviceName;
    private String deviceModel;
    private String updteAppTime;
    private String updteAppYear;
    private String updteAppMonth;
    private String updteAppDay;
}
