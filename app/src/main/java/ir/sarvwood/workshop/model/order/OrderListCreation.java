package ir.sarvwood.workshop.model.order;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ir.sarvwood.workshop.R;

public class OrderListCreation {
    private Context context;

    public OrderListCreation( Context context ){
        this.context = context;
    }

    public List< CheckableObject > getWoodTypeList(CheckableObject obj ){
        return getList(context.getResources().getStringArray( R.array.wood_type ),obj );
    }

    public List< CheckableObject > getWidthLengthList(CheckableObject obj  ){
        return getList(context.getResources().getStringArray( R.array.sheet_dimensions ),obj );
    }

    public List< CheckableObject > getPvcThickness(CheckableObject obj  ){
        return getList(context.getResources().getStringArray( R.array.pvc_thickness ),obj );
    }

    public List< CheckableObject > getPvcLengthNo(CheckableObject obj  ){
        return getList(context.getResources().getStringArray( R.array.width_length ),obj );
    }
    public List< CheckableObject > getPvcWidthNo(CheckableObject obj  ){
        return getList(context.getResources().getStringArray( R.array.width_width ),obj );
    }

    public List< CheckableObject > getWoodSheetLength( CheckableObject obj ){
        return getList(context.getResources().getStringArray( R.array.width_length ),obj );
    }
    public List< CheckableObject > getWoodSheetWidth(CheckableObject obj  ){
        return getList(context.getResources().getStringArray( R.array.width_width ),obj );
    }
    public List< CheckableObject > getPersianCutLenghtNo( CheckableObject obj ){
        return getList(context.getResources().getStringArray( R.array.width_length ),obj );
    }
    public List< CheckableObject > getPersianCutWidthNo(CheckableObject obj  ){
        return getList(context.getResources().getStringArray( R.array.width_width ),obj );
    }

    public List< CheckableObject > getGrooveLengthNo( CheckableObject obj ){
        return getList(context.getResources().getStringArray( R.array.width_length ),obj );
    }
    public List< CheckableObject > getGrooveWidthNo(CheckableObject obj  ){
        return getList(context.getResources().getStringArray( R.array.width_width ),obj );
    }

    public List< CheckableObject > getWoodSheetList( CheckableObject obj ){
        return getList(context.getResources().getStringArray( R.array.sheet_dimensions ),obj );
    }



    private List< CheckableObject > getList(String[] stringArray,CheckableObject object){
        List< CheckableObject > checkableObjectList = new ArrayList<>();
        for (int i=0;i<stringArray.length;i++)
       // for ( String s : stringArray )
       {
            if (object!=null)
                checkableObjectList.add( CheckableObject.builder().name( stringArray[i] ).checked( object.getName().equals( stringArray[i]) ).index(i).build() );
            else
                checkableObjectList.add( CheckableObject.builder().name(  stringArray[i] ).checked( false ).build() );
        }

        return checkableObjectList;
    }




}
