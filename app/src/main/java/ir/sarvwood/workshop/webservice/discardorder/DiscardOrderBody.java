package ir.sarvwood.workshop.webservice.discardorder;

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
public class DiscardOrderBody {

    private int orderId;
    private int customerId;
    private int discardOptionId;
}
