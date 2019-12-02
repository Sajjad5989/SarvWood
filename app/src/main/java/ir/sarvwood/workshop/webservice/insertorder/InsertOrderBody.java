package ir.sarvwood.workshop.webservice.insertorder;

import java.util.List;

import ir.sarvwood.workshop.model.WoodOrderModel;
import ir.sarvwood.workshop.model.order.WoodModel;
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

public class InsertOrderBody {

    private int customerId;
    private String desc;
    private List<WoodModel> items;

}
