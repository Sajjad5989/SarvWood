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
import ir.sarvwood.workshop.model.order.CheckableObject;
import ir.sarvwood.workshop.model.order.WoodModel;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {


    private List<WoodModel> items;
    private RecyclerViewClickListener listener;

    public OrderItemsAdapter(List<WoodModel> items, RecyclerViewClickListener listener) {
        this.items = items;
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

        String count = items.get(position).getSheetCount() +
                " برش " + " [ " + items.get(position).getCutLength() + " * " + items.get(position).getCutWidth() + " ]";
        holder.tvCount.setText(count);

        holder.tvPvcDirection.setText(getCorrectFormat(items.get(position).getPvcLengthNo()) +
                getCorrectFormat(items.get(position).getPvcWidthNo()));

        holder.tvPersianDirection.setText(getCorrectFormat(items.get(position).getPersianCutLenghtNo()) + getCorrectFormat(items.get(position).getPersianCutWidthNo()));

        holder.tvGrooveDirection.setText(getCorrectFormat(items.get(position).getGrooveLenghtNo()) + getCorrectFormat(items.get(position).getGrooveWidthNo()));


        holder.tvOrderTitle.setText(String.valueOf(position + 1));
//        String desc = getDesc(position);
//        holder.tvPreOrderDesc.setText(Html.fromHtml(desc));
    }

    private String getDesc(int currentPosition) {

        String res = "<br>" + "چوب: "
                + "[" + items.get(currentPosition).getWoodSheetLength() + "/" + items.get(currentPosition).getWoodSheetWidth() + "] "
                + items.get(currentPosition).getWoodType().getName() + " "
                + items.get(currentPosition).getColor() + (items.get(currentPosition).getPatterned() == 1 ? " [راه دار] " : "");

        res = res + "<\br>";

        res = res + "<br>" + "پی وی سی: " + items.get(currentPosition).getPvcThickness().getName() +
                getCorrectFormat(items.get(currentPosition).getPvcLengthNo()) + getCorrectFormat(items.get(currentPosition).getPvcWidthNo()) +
                items.get(currentPosition).getPvcColor();
        res = res + "<\br>";

        res = res + "<br>" + "تعداد: " + items.get(currentPosition).getSheetCount();
        res = res + "<\br>";

        res = res + "<br>" + "فارسی بُر: " +
                getCorrectFormat(items.get(currentPosition).getPersianCutLenghtNo()) + getCorrectFormat(items.get(currentPosition).getPersianCutWidthNo());
        res = res + "<\br>";

        res = res + "<br>" + "شیار: " +
                getCorrectFormat(items.get(currentPosition).getGrooveLenghtNo()) + getCorrectFormat(items.get(currentPosition).getGrooveWidthNo());
        res = res + "<\br>";

        return res;

    }

    private String getCorrectFormat(CheckableObject checkableObject) {

        if ("هیچکدام".equals(checkableObject.getName()))
            return " ";
        return !checkableObject.isChecked() ? " " : " [ " + checkableObject.getName() + " ]";

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvOrderTitle;
        private AppCompatTextView tvCount;
        private AppCompatTextView tvPvcDirection;
        private AppCompatTextView tvPersianDirection;
        private AppCompatTextView tvGrooveDirection;


        private ViewHolder(final View itemView) {
            super(itemView);

            tvOrderTitle = itemView.findViewById(R.id.tv_pre_order_title);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvPvcDirection = itemView.findViewById(R.id.tv_pvc_direction);
            tvPersianDirection = itemView.findViewById(R.id.tv_persian_direction);
            tvGrooveDirection = itemView.findViewById(R.id.tv_groove_direction);

        }
    }


}
