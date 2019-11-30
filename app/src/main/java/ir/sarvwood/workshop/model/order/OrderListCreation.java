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

    private List< CheckableObject > getList(String[] stringArray){
        List< CheckableObject > checkableObjectList = new ArrayList<>();
        for ( String s : stringArray ) {
            checkableObjectList.add( CheckableObject.builder().name( s ).checked( false ).build() );
        }

        return checkableObjectList;
    }
}
