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
import ir.sarvwood.workshop.model.order.CheckableObject;

public class RadioAdapter extends RecyclerView.Adapter< RadioAdapter.ViewHolder > {

    private List< CheckableObject > items;
    private RecyclerViewClickListener listener;

    public RadioAdapter( List< CheckableObject > items, RecyclerViewClickListener listener ){
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RadioAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ){
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.new_template_input_row, parent, false );
        final RadioAdapter.ViewHolder mViewHolder = new RadioAdapter.ViewHolder( view );
        view.setOnClickListener( view1 -> listener.onItemClick( view1, mViewHolder.getAdapterPosition() ) );
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder( @NonNull RadioAdapter.ViewHolder holder, int position ){
        holder.rbValues.setText( items.get( position ).getName() );
        holder.rbValues.setChecked( items.get( position ).isChecked() );
    }

    @Override
    public int getItemCount( ){
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatRadioButton rbValues;

        private ViewHolder( final View itemView ){
            super( itemView );
            rbValues = itemView.findViewById( R.id.rb_vales );
            rbValues.setOnClickListener( v -> listener.onItemClick( itemView, getAdapterPosition() ) );
        }
    }

}
