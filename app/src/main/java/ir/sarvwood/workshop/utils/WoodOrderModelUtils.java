package ir.sarvwood.workshop.utils;

import android.content.Context;

import ir.sarvwood.workshop.R;

public class WoodOrderModelUtils {


    public String getNameByPosition(Context context, int step, int positionLen, int positionWid) {
        String[] value;

        switch (step) {
            case 0:
                value = context.getResources().getStringArray(R.array.wood_type);
                return value[positionLen];
            case 4:
                value = context.getResources().getStringArray(R.array.pvc_thickness);
                return value[positionLen];
//            case 5:
//                value = context.getResources().getStringArray(R.array.sheet_dimensions);
//                return value[position];
            case 3:
            case 7:
            case 8:
                return getLength(positionLen) + ("".equals(getWidth(positionWid)) ? "" : ", " + getWidth(positionWid));
            default:
                return "";
        }
    }

    private String getLength(int len) {
        return len == 1 ? "1 طول" : len == 2 ? "2 طول" : "";
    }

    private String getWidth(int wid) {
        return wid == 1 ? "1 عرض" : wid == 2 ? "2 عرض" : "";
    }

}
