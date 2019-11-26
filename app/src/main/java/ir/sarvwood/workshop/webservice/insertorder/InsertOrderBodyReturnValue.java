package ir.sarvwood.workshop.webservice.insertorder;

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
public class InsertOrderBodyReturnValue {

    private int id;
    private int trackingCode;
    private String accessToken;
}
