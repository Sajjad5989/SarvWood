package ir.sarvwood.workshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.RecyclerViewClickListener;
import ir.sarvwood.workshop.webservice.myorders.MyOrderReturnValue;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

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
        holder.imageOrderStatus.setImageResource(R.drawable.ic_smile);

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


