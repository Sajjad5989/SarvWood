package ir.sarvwood.workshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.RecyclerViewClickListener;
import ir.sarvwood.workshop.webservice.myorders.GetOrderDetailsItemReturnValueList;
import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsItemReturnValue;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {


    private List<GetOrderDetailsItemReturnValue> items;
    private RecyclerViewClickListener listener;

    public OrderItemsAdapter(GetOrderDetailsItemReturnValueList items, RecyclerViewClickListener listener) {
        this.items = items.getGetOrderDetailsItemReturnValues();
        this.listener = listener;
    }


    @NonNull
    @Override
    public OrderItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.template_pre_order_row, parent, false);

        final OrderItemsAdapter.ViewHolder mViewHolder = new OrderItemsAdapter.ViewHolder(view);
        view.setOnClickListener(view1 -> listener.onItemClick(view1, mViewHolder.getAdapterPosition()));
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemsAdapter.ViewHolder holder, int position) {

        holder.tvPreOrderTitle.setText(String.valueOf(position + 1));
        String desc = getDesc(position);
        holder.tvPreOrderDesc.setText(desc);
    }

    private String getDesc(int currentPosition) {

        String res = ""; //"نوع چوب:«"+
//                 items.get(currentPosition).getWoodType();


        res = res + (items.get(currentPosition).getPatterned() == 1 ? " [راه دار می باشد] " : "");
        res = res + "..::.." + items.get(currentPosition).getColor() + " » -";
        return res;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvPreOrderTitle;
        private AppCompatTextView tvPreOrderDesc;


        private ViewHolder(final View itemView) {
            super(itemView);

            tvPreOrderTitle = itemView.findViewById(R.id.tv_pre_order_title);
            tvPreOrderDesc = itemView.findViewById(R.id.tv_pre_order_desc);

        }
    }


}
