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

    public List< CheckableObject > getWoodTypeList( ){
        return getList(context.getResources().getStringArray( R.array.wood_type ) );
    }

//    public List< CheckableObject > getWidthLengthList( ){
//        return getList(context.getResources().getStringArray( R.array.wood_type ) );
//    }


    public List< CheckableObject > getPvcThickness( ){
        return getList(context.getResources().getStringArray( R.array.pvc_thickness ) );
    }

    public List< CheckableObject > getPvcLengthNo( ){
        return getList(context.getResources().getStringArray( R.array.width_length ) );
    }
    public List< CheckableObject > getPvcWidthNo( ){
        return getList(context.getResources().getStringArray( R.array.width_width ) );
    }

    public List< CheckableObject > getWoodSheetLength( ){
        return getList(context.getResources().getStringArray( R.array.width_length ) );
    }
    public List< CheckableObject > getWoodSheetWidth( ){
        return getList(context.getResources().getStringArray( R.array.width_width ) );
    }
    public List< CheckableObject > getPersianCutLenghtNo( ){
        return getList(context.getResources().getStringArray( R.array.width_length ) );
    }
    public List< CheckableObject > getPersianCutWidthNo( ){
        return getList(context.getResources().getStringArray( R.array.width_width ) );
    }

    public List< CheckableObject > getGrooveLengthNo( ){
        return getList(context.getResources().getStringArray( R.array.width_length ) );
    }
    public List< CheckableObject > getGrooveWidthNo( ){
        return getList(context.getResources().getStringArray( R.array.width_width ) );
    }

    public List< CheckableObject > getWoodSheetList( ){
        return getList(context.getResources().getStringArray( R.array.sheet_dimensions ) );
    }



    private List< CheckableObject > getList(String[] stringArray){
        List< CheckableObject > checkableObjectList = new ArrayList<>();
        for ( String s : stringArray ) {
            checkableObjectList.add( CheckableObject.builder().name( s ).checked( false ).build() );
        }

        return checkableObjectList;
    }
}
