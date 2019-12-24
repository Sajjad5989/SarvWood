package ir.sarvwood.workshop.model.order;

import android.content.Context;

import java.util.List;

import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsItemReturnValue;
import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsReturnValue;

public class ConvertToHeaderWoodModel {

    private static OrderListCreation orderListCreation;
    private static List<CheckableObject> woodTypeList;
    private static List<CheckableObject> pvcThicknessList;
    private static List<CheckableObject> woodSheetList;
    private GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue> valueList;

    private Context context;
    private HeaderWoodModel headerWoodModel = new HeaderWoodModel();

    public ConvertToHeaderWoodModel(GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue> valueList, Context context) {
        this.valueList = valueList;
        this.context = context;
        createObjects();
    }

    private void createObjects() {
        orderListCreation = new OrderListCreation(context);

        woodSheetList = orderListCreation.getWoodSheetList(headerWoodModel.getWoodSheetList());
        woodTypeList = orderListCreation.getWoodTypeList(headerWoodModel.getWoodType());
        pvcThicknessList = orderListCreation.getPvcThickness(headerWoodModel.getPvcThickness());



    }

    public HeaderWoodModel getWoodHeaderOrderModelList() {
        headerWoodModel = new HeaderWoodModel();
        convert();
        return headerWoodModel;
    }

    private void convert() {
        headerWoodModel.setBrand(valueList.getWoodBrand());
        headerWoodModel.setColor(valueList.getWoodColor());
        headerWoodModel.setCode(valueList.getWoodCode());
        headerWoodModel.setPvcColor(valueList.getPvcColor());
        headerWoodModel.setPatterned(valueList.getPatterned());

        int woodTypeIndex= valueList.getWoodType()-1;
        woodTypeList.get(woodTypeIndex).setChecked(true);
        woodTypeList.get(woodTypeIndex).setIndex(woodTypeIndex);
        headerWoodModel.setWoodType(woodTypeList.get(woodTypeIndex));


        pvcThicknessList.get(valueList.getPvcThickness()).setChecked(true);
        pvcThicknessList.get(valueList.getPvcThickness()).setIndex(valueList.getPvcThickness());
        headerWoodModel.setPvcThickness(pvcThicknessList.get(valueList.getPvcThickness()));


        int height = valueList.getWoodSheetLength();
        int width = valueList.getWoodSheetWidth();
        /*
         <item>366 * 183 </item>
        <item>280 * 122</item>
        <item>240 * 122</item>
        <item>210 * 183</item>
        */
        int type;
        if (height == 366 && width == 183)
            type = 0;
        else if (height == 280 && width == 122)
            type = 1;
        else if (height == 240 && width == 122)
            type = 2;
        else if (height == 210 && width == 183)
            type = 3;
        else type = 4;

        headerWoodModel.setWoodSheetLength(height);
        headerWoodModel.setWoodSheetWidth(width);

        woodSheetList.get(type).setChecked(true);
        woodSheetList.get(type).setIndex(type);
        headerWoodModel.setWoodSheetList(woodSheetList.get(type));

    }

    private void setObject(GetOrderDetailsItemReturnValue value) {
        /*
        woodModel.setId(value.getId());
        woodModel.setOrderId(value.getOrderId());

        pvcLengthNoList.get(value.getPvcLenghtNo()).setChecked(true);
        woodModel.setPvcLengthNo(pvcLengthNoList.get(value.getPvcLenghtNo()));

        pvcWidthNoList.get(value.getPvcWidthNo()).setChecked(true);
        woodModel.setPvcWidthNo(pvcWidthNoList.get(value.getPvcWidthNo()));

        persianCutLengthNo.get(value.getPersianCutLenghtNo()).setChecked(true);
        woodModel.setPersianCutLenghtNo(persianCutLengthNo.get(value.getPersianCutLenghtNo()));

        persianCutWidthNo.get(value.getPersianCutWidthNo()).setChecked(true);
        woodModel.setPersianCutWidthNo(persianCutWidthNo.get(value.getPersianCutWidthNo()));

        grooveLengthNo.get(value.getGrooveLenghtNo()).setChecked(true);
        woodModel.setGrooveLenghtNo(grooveLengthNo.get(value.getGrooveLenghtNo()));

        grooveWidthNo.get(value.getGrooveWidthNo()).setChecked(true);
        woodModel.setGrooveWidthNo(grooveWidthNo.get(value.getGrooveWidthNo()));

        woodModel.setPieceLength(value.getPieceLength());
        woodModel.setPieceWidth(value.getPieceWidth());
        woodModel.setPieceCount(value.getPieceCount());

        woodModel.setId(value.getId());
        woodModel.setOrderId(value.getOrderId());

        woodOrderModelList.add(woodModel);

        woodModel= new WoodModel();
*/
    }

}
