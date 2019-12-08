package ir.sarvwood.workshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.RecyclerViewClickListener;

public class FillListAdapter extends RecyclerView.Adapter< FillListAdapter.ViewHolder > {


    private String[] items;
    private int currentValue;
    private RecyclerViewClickListener listener;
    private int lastSelectedPosition = -1;

    public FillListAdapter( String[] items, RecyclerViewClickListener listener, int currentValue ){
        this.items = items;
        this.listener = listener;
        this.currentValue = currentValue;
    }


    @NonNull
    @Override
    public FillListAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ){
        View view = LayoutInflater.from( parent.getContext() ).inflate(
                R.layout.new_template_input_row, parent, false );

        final FillListAdapter.ViewHolder mViewHolder = new FillListAdapter.ViewHolder( view );
        view.setOnClickListener( view1 -> listener.onItemClick( view1, mViewHolder.getAdapterPosition() ) );

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder( @NonNull FillListAdapter.ViewHolder holder, int position ){

        holder.rbValues.setText( items[ position ] );
        holder.rbValues.setChecked( lastSelectedPosition == position );
    }

    @Override
    public int getItemCount( ){
        return items.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatRadioButton rbValues;


        private ViewHolder( final View itemView ){
            super( itemView );
            rbValues = itemView.findViewById( R.id.rb_vales );
            rbValues.setOnClickListener( v -> {

                lastSelectedPosition = getAdapterPosition();
                listener.onItemClick( itemView, getAdapterPosition() );
                notifyDataSetChanged();
            } );
        }
    }


}
