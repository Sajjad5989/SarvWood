package ir.sarvwood.workshop.model.order;

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
public class WoodModel implements Serializable {

    private int id;
    private int orderId;

    private CheckableObject woodType;
    private String color;
    private String pvcColor;
    private CheckableObject pvcThickness;
    private CheckableObject pvcLengthNo;
    private CheckableObject pvcWidthNo;
    private int woodSheetLength;
    private int woodSheetWidth;
    private CheckableObject woodSheetList;
    private CheckableObject persianCutLenghtNo;
    private CheckableObject persianCutWidthNo;
    private CheckableObject grooveLenghtNo;
    private CheckableObject grooveWidthNo;
    private int sheetCount;
    private int patterned;
    private String desc;

}
