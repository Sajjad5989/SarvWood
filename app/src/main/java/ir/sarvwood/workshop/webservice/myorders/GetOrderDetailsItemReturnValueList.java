package ir.sarvwood.workshop.webservice.myorders;

import java.io.Serializable;
import java.util.List;

import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsItemReturnValue;

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
public class GetOrderDetailsItemReturnValueList implements Serializable {

    private List<GetOrderDetailsItemReturnValue> getOrderDetailsItemReturnValues;

}
