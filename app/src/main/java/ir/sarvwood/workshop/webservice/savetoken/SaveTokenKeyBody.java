package ir.sarvwood.workshop.webservice.savetoken;


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
public class SaveTokenKeyBody {


    private int customerId;
    private String  deviceTokenKey;
}
