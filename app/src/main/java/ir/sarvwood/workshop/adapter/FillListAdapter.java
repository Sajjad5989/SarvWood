package ir.sarvwood.workshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.RecyclerViewClickListener;

public class FillListAdapter extends RecyclerView.Adapter<FillListAdapter.ViewHolder> {


    private String[] items;
    private Object currentValue;
    private RecyclerViewClickListener listener;
    private int lastSelectedPosition = -1;

    public FillListAdapter(String[] items, RecyclerViewClickListener listener, Object currentValue) {
        this.items = items;
        this.listener = listener;
        this.currentValue = currentValue;
    }


    @NonNull
    @Override
    public FillListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.template_input_row, parent, false);

        final FillListAdapter.ViewHolder mViewHolder = new FillListAdapter.ViewHolder(view);
        //view.setOnClickListener(view1 -> listener.onItemClick(view1, mViewHolder.getAdapterPosition()));
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FillListAdapter.ViewHolder holder, int position) {

        holder.tvName.setText(items[position]);

//        holder.rbValues.setChecked(currentValue.equals(String.valueOf(position)));

        holder.rbValues.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName;
        private AppCompatRadioButton rbValues;


        private ViewHolder(final View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            rbValues = itemView.findViewById(R.id.rb_vales);

            rbValues.setOnClickListener(v -> {

                lastSelectedPosition = getAdapterPosition();
                listener.onItemClick(itemView, getAdapterPosition());
                notifyDataSetChanged();
            });
        }
    }


}
