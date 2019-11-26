package ir.sarvwood.workshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.RecyclerViewClickListener;
import ir.sarvwood.workshop.model.StringRadio;

public class NewFillListAdapter extends RecyclerView.Adapter< NewFillListAdapter.ViewHolder > {


    private List< StringRadio > items;
    private int currentValue;
    private RecyclerViewClickListener listener;
    private int lastSelectedPosition = -1;

    public NewFillListAdapter( List< StringRadio > items, RecyclerViewClickListener listener, int currentValue ){
        this.items = items;
        this.listener = listener;
        this.currentValue = currentValue;
    }


    @NonNull
    @Override
    public NewFillListAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ){
        View view = LayoutInflater.from( parent.getContext() ).inflate(
                R.layout.new_template_input_row, parent, false );

        final NewFillListAdapter.ViewHolder mViewHolder = new NewFillListAdapter.ViewHolder( view );
        view.setOnClickListener( view1 -> listener.onItemClick( view1, mViewHolder.getAdapterPosition() ) );

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder( @NonNull NewFillListAdapter.ViewHolder holder, int position ){

        //    holder.tvName.setText(items[position]);

//        holder.rbValues.setChecked(currentValue.equals(String.valueOf(position)));
        holder.rbValues.setText( items.get( position ).getTitle() );
        //holder.rbValues.setChecked(currentValue==position);

        if ( currentValue >= 0 ){
            holder.rbValues.setChecked(currentValue == position);
        }
        else{
            holder.rbValues.setChecked(lastSelectedPosition == position);
        }
    }

    @Override
    public int getItemCount( ){
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //        private AppCompatTextView tvName;
        private AppCompatRadioButton rbValues;


        private ViewHolder( final View itemView ){
            super( itemView );
            //  tvName = itemView.findViewById(R.id.tv_name);
            rbValues = itemView.findViewById( R.id.rb_vales );
            rbValues.setOnClickListener( v -> {

                lastSelectedPosition = getAdapterPosition();
                listener.onItemClick( itemView, getAdapterPosition() );
                notifyDataSetChanged();
            } );
        }
    }


}
