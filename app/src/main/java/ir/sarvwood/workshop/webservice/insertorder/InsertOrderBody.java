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
    private int woodType;
    private String woodColor;
    private String pvcColor;
    private int pvcThickness;
    private String woodBrand;
    private String woodCode;
    private int woodSheetLength;
    private int woodSheetWidth;
    private String desc;
    private int patterned;
    private List<WoodOrderModel> items;
}
