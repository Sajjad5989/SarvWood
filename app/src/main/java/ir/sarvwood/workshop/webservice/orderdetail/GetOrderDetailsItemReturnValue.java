package ir.sarvwood.workshop.webservice.orderdetail;

import java.io.Serializable;

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

public class GetOrderDetailsItemReturnValue implements Serializable {

    private int id;
    private int orderId;
    private int woodType;
    private String woodTypeTitle;
    private String color;
    private String pvcColor;
    private int pvcThickness;
    private String pvcThicknessTitle;
    private int pvcLenghtNo;
    private int pvcWidthNo;
    private int woodSheetLength;
    private int woodSheetWidth;
    private int sheetCount;
    private int persianCutLenghtNo;
    private int persianCutWidthNo;
    private int grooveLenghtNo;
    private int grooveWidthNo;
    private int patterned;
    private String desc;
}
