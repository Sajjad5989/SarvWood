package ir.sarvwood.workshop.model.order;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsItemReturnValue;

public class ConvertToWoodModelList {

    private List<GetOrderDetailsItemReturnValue> valueList;
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

    private Context context;
    private WoodModel woodModel= new WoodModel();
    public static List<WoodModel> woodOrderModelList;// = new ArrayList<>();

    public ConvertToWoodModelList(List<GetOrderDetailsItemReturnValue> valueList, Context context) {
        this.valueList = valueList;
        this.context = context;
        createObjects();

    }
    private void createObjects()
    {
        orderListCreation = new OrderListCreation(context);

        woodTypeList = orderListCreation.getWoodTypeList(woodModel.getWoodType());

        pvcThicknessList = orderListCreation.getPvcThickness(woodModel.getPvcThickness());

        pvcLengthNoList = orderListCreation.getPvcLengthNo(woodModel.getPvcLengthNo());
        pvcWidthNoList = orderListCreation.getPvcWidthNo(woodModel.getPvcWidthNo());

        persianCutLengthNo = orderListCreation.getPersianCutLenghtNo(woodModel.getPersianCutLenghtNo());
        persianCutWidthNo = orderListCreation.getPersianCutWidthNo(woodModel.getPersianCutWidthNo());

        grooveLengthNo = orderListCreation.getGrooveLengthNo(woodModel.getGrooveLenghtNo());
        grooveWidthNo = orderListCreation.getGrooveWidthNo(woodModel.getGrooveWidthNo());

        woodSheetList = orderListCreation.getWoodSheetList(woodModel.getWoodSheetList());

    }

    public List<WoodModel> getWoodOrderModelList()
    {
        woodOrderModelList = new ArrayList<>();
        convert();
        return woodOrderModelList;
    }

    private void convert()
    {
        GetOrderDetailsItemReturnValue value;
        List<GetOrderDetailsItemReturnValue> list = valueList;

        for (int i=0;i<list.size();i++)
        {
            value =  list.get(i);
            setObject(value);
        }
    }

    private void setObject(GetOrderDetailsItemReturnValue value)
    {
        woodModel.setId(value.getId());
        woodModel.setOrderId(value.getOrderId());

        woodTypeList.get(value.getWoodType()-1).setChecked(true);
        woodModel.setWoodType(woodTypeList.get(value.getWoodType()-1));

        pvcThicknessList.get(value.getPvcThickness()).setChecked(true);
        woodModel.setPvcThickness(pvcThicknessList.get(value.getPvcThickness()));

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

        int len = value.getWoodSheetLength();
        int wid = value.getWoodSheetWidth();
        /*
        <item>366 * 183 </item>
        <item>280 * 122</item>
        <item>240 * 122</item>
        <item>210 * 183</item>
        */
        int pos = 4;
        if ((String.valueOf(len)+" * "+String.valueOf(wid)).equals("366 * 183"))
            pos = 0;
        else if ((String.valueOf(len)+" * "+String.valueOf(wid)).equals("280 * 122"))
            pos = 1;
        else if ((String.valueOf(len)+" * "+String.valueOf(wid)).equals("240 * 122"))
            pos = 2;
        else if ((String.valueOf(len)+" * "+String.valueOf(wid)).equals("210 * 183"))
            pos = 3;
        woodSheetList.get(pos).setChecked(true);
        woodSheetList.get(pos).setIndex(pos);
        woodModel.setWoodSheetList(woodSheetList.get(pos));



        woodModel.setWoodSheetLength(value.getWoodSheetLength());
        woodModel.setWoodSheetWidth(value.getWoodSheetWidth());

        woodModel.setId(value.getId());
        woodModel.setOrderId(value.getOrderId());
        woodModel.setColor(value.getColor());
        woodModel.setPvcColor(value.getPvcColor());
        woodModel.setSheetCount(value.getSheetCount());
        woodModel.setPatterned(value.getPatterned());

        woodOrderModelList.add(woodModel);

        woodModel= new WoodModel();

    }

}
