package ir.sarvwood.workshop.webservice.updateorder;

import java.io.Serializable;
import java.util.List;

import ir.sarvwood.workshop.model.WoodOrderModel;
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
public class UpdateOrderBody{

    private int orderId;
    private int customerId;

    private int woodType;
    private String woodColor;
    private String pvcColor;
    private int pvcThickness;
    private String woodBrand;
    private String woodCode;
    private int woodSheetLength;
    private int woodSheetWidth;
    private int patterned;

    private String desc;
    private List<WoodOrderModel> items;




}
