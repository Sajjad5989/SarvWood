package ir.sarvwood.workshop.webservice.deletetoken;


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
public class DeleteTokenKeyBody {


    private int  customerId;
}
