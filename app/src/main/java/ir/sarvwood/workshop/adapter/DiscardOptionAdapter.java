package ir.sarvwood.workshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.dialog.discardoption.RecyclerViewClickListenerDiscard;
import ir.sarvwood.workshop.webservice.discardoption.DiscardOptionReturnValue;

public class DiscardOptionAdapter extends RecyclerView.Adapter< DiscardOptionAdapter.ViewHolder > {

    private List<DiscardOptionReturnValue> items;
    private RecyclerViewClickListenerDiscard listener;
    private int rowPosition ;

    @NonNull
    @Override
    public DiscardOptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ){
        View view = LayoutInflater.from( parent.getContext() ).inflate(
                R.layout.template_row_discard_option, parent, false );

        final DiscardOptionAdapter.ViewHolder mViewHolder = new DiscardOptionAdapter.ViewHolder( view );
        view.setOnClickListener( view1 -> listener.onItemClick(  mViewHolder.getAdapterPosition() ) );

        return mViewHolder;
    }
    public DiscardOptionAdapter(List<DiscardOptionReturnValue> items, RecyclerViewClickListenerDiscard listener ){
        this.items = items;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder( @NonNull DiscardOptionAdapter.ViewHolder holder, int position ){

        holder.btnTitle.setText( items.get(position).getTitle());
//        holder.tvDiscardId.setText( String.valueOf(items.get(position).getId()));
//        rowPosition = position;
    }

    @Override
    public int getItemCount( ){
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatButton btnTitle;
        private AppCompatTextView tvDiscardId;


        private ViewHolder( final View itemView ){
            super( itemView );

            btnTitle = itemView.findViewById( R.id.button_discard );
            btnTitle.setOnClickListener( v -> {
                listener.onItemClick(  getAdapterPosition() );
            } );
        }
    }


}
