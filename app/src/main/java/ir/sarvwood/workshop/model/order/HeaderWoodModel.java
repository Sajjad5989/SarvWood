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
public class HeaderWoodModel implements Serializable {

    private int id;
    private int orderId;

    private CheckableObject woodType;
    private String color;
    private String brand;
    private String code;
    private String pvcColor;
    private CheckableObject pvcThickness;
    private int woodSheetLength;
    private int woodSheetWidth;
    private CheckableObject woodSheetList;
    private int patterned;


}
