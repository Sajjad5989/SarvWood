package ir.sarvwood.workshop.model.order;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsItemReturnValue;

public class ConvertToWoodModelList {

    //    private HeaderWoodModel headerWoodModel= new HeaderWoodModel();
    public static List<WoodModel> woodOrderModelList;// = new ArrayList<>();
    private static OrderListCreation orderListCreation;
    private static List<CheckableObject> woodTypeList;
    private static List<CheckableObject> pvcThicknessList;
    private static List<CheckableObject> pvcLengthNoList;
    private static List<CheckableObject> pvcWidthNoList;
    private static List<CheckableObject> woodSheetList;
    private static List<CheckableObject> persianCutLengthNo;
    private static List<CheckableObject> persianCutWidthNo;
    private static List<CheckableObject> grooveLengthNo;
    private static List<CheckableObject> grooveWidthNo;
    private List<GetOrderDetailsItemReturnValue> valueList;
    private Context context;
    private WoodModel woodModel = new WoodModel();

    public ConvertToWoodModelList(List<GetOrderDetailsItemReturnValue> valueList, Context context) {
        this.valueList = valueList;
        this.context = context;
        createObjects();
    }

    private void createObjects() {
        orderListCreation = new OrderListCreation(context);

        pvcLengthNoList = orderListCreation.getPvcLengthNo(woodModel.getPvcLengthNo());
        pvcWidthNoList = orderListCreation.getPvcWidthNo(woodModel.getPvcWidthNo());

        persianCutLengthNo = orderListCreation.getPersianCutLenghtNo(woodModel.getPersianCutLenghtNo());
        persianCutWidthNo = orderListCreation.getPersianCutWidthNo(woodModel.getPersianCutWidthNo());

        grooveLengthNo = orderListCreation.getGrooveLengthNo(woodModel.getGrooveLenghtNo());
        grooveWidthNo = orderListCreation.getGrooveWidthNo(woodModel.getGrooveWidthNo());

//        woodSheetList = orderListCreation.getWoodSheetList(headerWoodModel.getWoodSheetList());
//        woodTypeList = orderListCreation.getWoodTypeList(headerWoodModel.getWoodType());
//        pvcThicknessList = orderListCreation.getPvcThickness(headerWoodModel.getPvcThickness());

    }

    public List<WoodModel> getWoodOrderModelList() {
        woodOrderModelList = new ArrayList<>();
        convert();
        return woodOrderModelList;
    }

    private void convert() {
        GetOrderDetailsItemReturnValue value;
        List<GetOrderDetailsItemReturnValue> list = valueList;

        for (int i = 0; i < list.size(); i++) {
            value = list.get(i);
            setObject(value);
        }
    }

    private void setObject(GetOrderDetailsItemReturnValue value) {
        woodModel.setId(value.getId());
        woodModel.setOrderId(value.getOrderId());

        int pvcLen = value.getPvcLenghtNo();
        pvcLengthNoList.get(pvcLen).setChecked(true);
        pvcLengthNoList.get(pvcLen).setIndex(value.getPvcLenghtNo());
        woodModel.setPvcLengthNo(pvcLengthNoList.get(pvcLen));

        int pvcWid = value.getPvcWidthNo();
        pvcWidthNoList.get(pvcWid).setChecked(true);
        pvcWidthNoList.get(pvcWid).setIndex(pvcWid);
        woodModel.setPvcWidthNo(pvcWidthNoList.get(pvcWid));

        int cutLen = value.getPersianCutLenghtNo();
        persianCutLengthNo.get(cutLen).setChecked(true);
        persianCutLengthNo.get(cutLen).setIndex(cutLen);
        woodModel.setPersianCutLenghtNo(persianCutLengthNo.get(cutLen));

        int cutWid = value.getPersianCutWidthNo();
        persianCutWidthNo.get(cutWid).setChecked(true);
        persianCutWidthNo.get(cutWid).setIndex(cutWid);
        woodModel.setPersianCutWidthNo(persianCutWidthNo.get(cutWid));

        int grooveLen = value.getGrooveLenghtNo();
        grooveLengthNo.get(grooveLen).setChecked(true);
        grooveLengthNo.get(grooveLen).setIndex(grooveLen);
        woodModel.setGrooveLenghtNo(grooveLengthNo.get(grooveLen));

        int grooveWid = value.getGrooveWidthNo();
        grooveWidthNo.get(grooveWid).setChecked(true);
        grooveWidthNo.get(grooveWid).setIndex(grooveWid);
        woodModel.setGrooveWidthNo(grooveWidthNo.get(grooveWid));

        woodModel.setPieceLength(value.getPieceLength());
        woodModel.setPieceWidth(value.getPieceWidth());
        woodModel.setPieceCount(value.getPieceCount());

        woodModel.setDesc(value.getDesc());

        woodModel.setId(value.getId());
        woodModel.setOrderId(value.getOrderId());

        woodOrderModelList.add(woodModel);

        woodModel = new WoodModel();

    }

}
