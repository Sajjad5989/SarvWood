package ir.sarvwood.workshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.RecyclerViewClickListener;
import ir.sarvwood.workshop.model.WoodOrderModel;
import ir.sarvwood.workshop.utils.WoodOrderModelUtils;

public class PreOrderAdapter extends RecyclerView.Adapter<PreOrderAdapter.ViewHolder> {


    private WoodOrderModelUtils woodOrderModelUtils = new WoodOrderModelUtils();
    private List<WoodOrderModel> items;
    private RecyclerViewClickListener listener;
    private Context context;

    public PreOrderAdapter(List<WoodOrderModel> items, RecyclerViewClickListener listener) {
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
        holder.tvPreOrderTitle.setText("پیش سفارش شماره " + (position + 1));

        String desc = getDesc(position);


        holder.tvPreOrderDesc.setText(desc);
    }

    private String getDesc(int currentPosition) {

        String res = "نوع چوب:«" + woodOrderModelUtils.getNameByPosition(context, 0, items.get(currentPosition).getWoodType(),
                0) + " « -";

        res = res + (items.get(currentPosition).getPatterned() == 1 ? " [راه دار می باشد] - " : "");
        res = res + " رنگ چوب:«" + items.get(currentPosition).getColor() + " » -";

        String pvcColor = items.get(currentPosition).getPvcColor();
        res = res + ("".equals(pvcColor) ? "" : " رنگ پی وی سی:« " + pvcColor + " » -");

        int pvcLen = items.get(currentPosition).getPvcLenghtNo();
        int pvcWid = items.get(currentPosition).getPvcWidthNo();
        if (pvcLen > 0 || pvcWid > 0)
            res = res + " جهت پی وی سی:« " + woodOrderModelUtils.getNameByPosition(context, 3, pvcLen, pvcWid) + " » -";


        int pvcThick = items.get(currentPosition).getPvcThickness();
        if (pvcThick > 0)
            res = res + " ضخامت پی وی سی:« " + woodOrderModelUtils.getNameByPosition(context, 4, pvcThick,
                    0) + " » -";

        int sheetLen = items.get(currentPosition).getWoodSheetLength();
        int sheetWid = items.get(currentPosition).getWoodSheetWidth();
        if (sheetLen > 0 || sheetWid > 0)
            res = res + " ابعاد:« " + String.format("%d * %d", sheetLen, sheetWid) + " » -";


        int sheetCount = items.get(currentPosition).getSheetCount();
        if (sheetCount > 0)
            res = res + "تعداد:« " + sheetCount + " ورق" + " » -";

        int persianCutLen = items.get(currentPosition).getPersianCutLenghtNo();
        int persianCutWid = items.get(currentPosition).getPersianCutWidthNo();
        if (persianCutLen > 0 || persianCutWid > 0)
            res = res + "فارسی بر:« " + woodOrderModelUtils.getNameByPosition(context, 7, persianCutLen, persianCutWid) + " » -";


        int grooveLen = items.get(currentPosition).getGrooveLenghtNo();
        int grooveWid = items.get(currentPosition).getGrooveWidthNo();
        if (grooveLen > 0 || grooveWid > 0)
            res = res + "شیار:« " + woodOrderModelUtils.getNameByPosition(context, 8, grooveLen, grooveWid) + " » -";

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
