package ir.sarvwood.workshop.webservice.apibodies;


import java.util.List;

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
public class InsrtOrderBody {

    private int customerId;
    private String desc;
    private List<Object> items;
}
