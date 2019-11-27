package ir.sarvwood.workshop.webservice.baseinfo;

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
public class BaseInfoReturnValue {


    private String androidAppDlLink;
    private String androidAppDlLinkDirect;
    private String androidAppDlLinkBazaar;
    private String androidAppDlLinkGoogleplay;
    private String instagramLink;
    private String telegramLink;
    private String email;
    private String address;
    private Float longitude;
    private Float latitude;
    private String phone;
    private String telegramPhone;
    private String whatsappPhone;
    private String supportPhone;
    private String androidGlobalAppVer;
    private Integer androidGlobalAppForceUpdate;

}
