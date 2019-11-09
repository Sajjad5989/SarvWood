package ir.sarvwood.workshop.utils;

import android.content.Context;

import ir.sarvwood.workshop.R;

public class WoodOrderModelUtils {


    public String getNameByPosition(Context context, int step, int position)
    {
        String[] value;

        switch (step)
        {
            case 0:
                value = context.getResources().getStringArray(R.array.wood_type);
                return value[position];

            case 3:
                value = context.getResources().getStringArray(R.array.direction);
                return value[position];
            case 4:
                value = context.getResources().getStringArray(R.array.pvc_thickness);
                return value[position];
            case 5:
                value = context.getResources().getStringArray(R.array.sheet_dimensions);
                return value[position];
            case 7:
                value = context.getResources().getStringArray(R.array.direction);
                return value[position];
            case 8:
                value = context.getResources().getStringArray(R.array.direction);
                return value[position];
            default:
                return "";
        }
    }
}
