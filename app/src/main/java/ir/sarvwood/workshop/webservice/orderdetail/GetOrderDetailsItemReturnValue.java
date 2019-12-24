package ir.sarvwood.workshop.webservice.orderdetail;

import java.io.Serializable;

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

public class GetOrderDetailsItemReturnValue  implements Serializable  {


    private int id;
    private int orderId;
    private int pvcLenghtNo;
    private int pvcWidthNo;
    private float pieceLength;
    private float pieceWidth;
    private int pieceCount;
    private int persianCutLenghtNo;
    private int persianCutWidthNo;
    private int grooveLenghtNo;
    private int grooveWidthNo;
    private String desc;


}
