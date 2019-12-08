package ir.sarvwood.workshop.adapter;

import android.content.Context;
import android.text.Html;
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
import ir.sarvwood.workshop.utils.WoodOrderModelUtils;

public class PreOrderAdapter extends RecyclerView.Adapter<PreOrderAdapter.ViewHolder> {


    private List<WoodModel> items;
    private RecyclerViewClickListener listener;
    private Context context;

    public PreOrderAdapter(List<WoodModel> items, RecyclerViewClickListener listener) {
        this.items = items;
        this.listener = listener;
    }


    @NonNull
    @Override
    public PreOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.template_pre_order_row, parent, false);

        context = parent.getContext();
        final PreOrderAdapter.ViewHolder mViewHolder = new PreOrderAdapter.ViewHolder(view);
        view.setOnClickListener(view1 -> listener.onItemClick(view1, mViewHolder.getAdapterPosition()));
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreOrderAdapter.ViewHolder holder, int position) {
        holder.tvPreOrderTitle.setText(String.valueOf(position + 1));

        String desc = getDesc(position);
        holder.tvPreOrderDesc.setText(Html.fromHtml(desc));
    }

    private String getDesc(int currentPosition) {


        String res = "<br>" + "چوب: "
                + "[" + items.get(currentPosition).getWoodSheetLength() + "/" + items.get(currentPosition).getWoodSheetWidth() + "] "
                + items.get(currentPosition).getWoodType().getName()+" "
                + items.get(currentPosition).getColor() + (items.get(currentPosition).getPatterned() == 1 ? " [راه دار] " : "");

        res = res + "<\br>";

        res = res + "<br>" + "پی وی سی: " + items.get(currentPosition).getPvcThickness().getName() +
                 getCorrectFormat(items.get(currentPosition).getPvcLengthNo()) +
                 getCorrectFormat(items.get(currentPosition).getPvcWidthNo())  +
                items.get(currentPosition).getPvcColor();
        res = res + "<\br>";

        res = res + "<br>" + "تعداد: " + items.get(currentPosition).getSheetCount();
        res = res + "<\br>";

        res = res + "<br>" + "فارسی بُر: " +
                 getCorrectFormat(items.get(currentPosition).getPersianCutLenghtNo()) + getCorrectFormat(items.get(currentPosition).getPersianCutWidthNo()) ;
        res = res + "<\br>";

        res = res + "<br>" + "شیار: " +
                 getCorrectFormat(items.get(currentPosition).getGrooveLenghtNo())+ getCorrectFormat(items.get(currentPosition).getGrooveWidthNo()) ;
        res = res + "<\br>";

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

    private String getCorrectFormat(CheckableObject checkableObject)
    {
        if ("هیچکدام".equals(checkableObject.getName()))
            return " ";

        return !checkableObject.isChecked()?" ":" [ "+checkableObject.getName()+" ]";
    }



}
