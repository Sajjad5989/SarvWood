package ir.sarvwood.workshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.RecyclerViewClickListener;
import ir.sarvwood.workshop.webservice.myorders.MyOrderReturnValue;

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.ViewHolder> {


    private List<MyOrderReturnValue> items;
    private RecyclerViewClickListener listener;

    public MyOrderListAdapter(List<MyOrderReturnValue> items, RecyclerViewClickListener listener) {
        this.items = items;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyOrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.template_order_list, parent, false);

        final MyOrderListAdapter.ViewHolder mViewHolder = new MyOrderListAdapter.ViewHolder(view);
        view.setOnClickListener(view1 -> listener.onItemClick(view1, mViewHolder.getAdapterPosition()));
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderListAdapter.ViewHolder holder, int position) {
        holder.tvTrackingCode.setText(items.get(position).getTrackingCode());
        holder.tvMyOrderTime.setText(items.get(position).getInsrtTimeSimple());
        holder.tvOrderStatus.setText(items.get(position).getStateTitle());


        int state = items.get(position).getState();
        switch (state) {
            case 1:
                holder.imageOrderStatus.setImageResource(R.drawable.ic_sabtshode);
                break;
            case 2:
                holder.imageOrderStatus.setImageResource(R.drawable.ic_baresi);
                break;
            case 3:
                holder.imageOrderStatus.setImageResource(R.drawable.ic_taeedsefaresh);
                break;
            case 4:
                holder.imageOrderStatus.setImageResource(R.drawable.ic_darhaleanjam);
                break;
            case 5:
                holder.imageOrderStatus.setImageResource(R.drawable.ic_amade);
                break;
            case 6:
                holder.imageOrderStatus.setImageResource(R.drawable.ic_ersalshode);
                break;
            case 7:
                holder.imageOrderStatus.setImageResource(R.drawable.ic_tahvildad);
                break;
            case 8:
                holder.imageOrderStatus.setImageResource(R.drawable.ic_cancel);
                break;
            default:
                holder.imageOrderStatus.setImageResource(R.drawable.ic_default);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvMyOrderTime;
        private AppCompatTextView tvTrackingCode;
        private AppCompatTextView tvOrderStatus;
        private AppCompatImageView imageOrderStatus;


        private ViewHolder(final View itemView) {
            super(itemView);
            tvMyOrderTime = itemView.findViewById(R.id.tv_my_order_time);
            tvTrackingCode = itemView.findViewById(R.id.tv_tracking_code);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            imageOrderStatus = itemView.findViewById(R.id.image_order_status);
            // listener.onItemClick(itemView, getAdapterPosition());
        }
    }
}


