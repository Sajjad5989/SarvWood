package ir.sarvwood.workshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.IListActionListener;
import ir.sarvwood.workshop.interfaces.RecyclerViewClickListener;
import ir.sarvwood.workshop.model.order.CheckableObject;
import ir.sarvwood.workshop.model.order.WoodModel;

public class PreOrderAdapter extends RecyclerView.Adapter<PreOrderAdapter.ViewHolder> {


    private List<WoodModel> items;
//    private RecyclerViewClickListener listener;
    private IListActionListener listener;
    private Context context;

    public PreOrderAdapter(List<WoodModel> items, IListActionListener listener) {
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
        //view.setOnClickListener(view1 -> listener.onItemClick(view1, mViewHolder.getAdapterPosition()));
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreOrderAdapter.ViewHolder holder, int position) {

        holder.tvOrderTitle.setText(String.valueOf(position + 1));
        String count = items.get(position).getSheetCount() +
                " برش " + " [ " + items.get(position).getCutLength() + " * " + items.get(position).getCutWidth() + " ]";
        holder.tvCount.setText(count);
        holder.tvPvcDirection.setText(String.format("%s%s", getCorrectFormat(items.get(position).getPvcLengthNo()), getCorrectFormat(items.get(position).getPvcWidthNo())));
        holder.tvPersianDirection.setText(String.format("%s%s", getCorrectFormat(items.get(position).getPersianCutLenghtNo()), getCorrectFormat(items.get(position).getPersianCutWidthNo())));
        holder.tvGrooveDirection.setText(String.format("%s%s", getCorrectFormat(items.get(position).getGrooveLenghtNo()), getCorrectFormat(items.get(position).getGrooveWidthNo())));

        holder.imageEdit.setOnClickListener(view -> listener.onEdit(position));
        holder.imageDelete.setOnClickListener(view -> listener.onDelete(position));
    }

    private String getDesc(int currentPosition) {


//        String res = "<br>" //+ "چوب: "
//                + "[" + items.get(currentPosition).getWoodSheetLength() + "/" + items.get(currentPosition).getWoodSheetWidth() + "] ";
        // + items.get(currentPosition).getWoodType().getName()+" "
        // + items.get(currentPosition).getColor() + (items.get(currentPosition).getPatterned() == 1 ? " [راه دار] " : "");

//        res = res + "<\br>";


        String res = "<br>" + "تعداد: " + items.get(currentPosition).getSheetCount() +
                " برش " + " [ " + items.get(currentPosition).getCutLength() + " * " + items.get(currentPosition).getCutWidth() + " ]";
        res = res + "<\br>";

        res = res + "<br>" + "جهت پی وی سی: " + //items.get(currentPosition).getPvcThickness().getName() +
                getCorrectFormat(items.get(currentPosition).getPvcLengthNo()) +
                getCorrectFormat(items.get(currentPosition).getPvcWidthNo());// +
        // items.get(currentPosition).getPvcColor();
        res = res + "<\br>";

        res = res + "<br>" + "جهت فارسی بُر: " +
                getCorrectFormat(items.get(currentPosition).getPersianCutLenghtNo()) + getCorrectFormat(items.get(currentPosition).getPersianCutWidthNo());
        res = res + "<\br>";

        res = res + "<br>" + " جهت شیار: " +
                getCorrectFormat(items.get(currentPosition).getGrooveLenghtNo()) + getCorrectFormat(items.get(currentPosition).getGrooveWidthNo());
        res = res + "<\br>";

        return res;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String getCorrectFormat(CheckableObject checkableObject) {
        if ("هیچکدام".equals(checkableObject.getName()))
            return " ";

        return !checkableObject.isChecked() ? " " : " [ " + checkableObject.getName() + " ]";
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvOrderTitle;
        private AppCompatTextView tvCount;
        private AppCompatTextView tvPvcDirection;
        private AppCompatTextView tvPersianDirection;
        private AppCompatTextView tvGrooveDirection;
        private AppCompatImageView imageEdit;
        private AppCompatImageView imageDelete;


        private ViewHolder(final View itemView) {
            super(itemView);

            tvOrderTitle = itemView.findViewById(R.id.tv_pre_order_title);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvPvcDirection = itemView.findViewById(R.id.tv_pvc_direction);
            tvPersianDirection = itemView.findViewById(R.id.tv_persian_direction);
            tvGrooveDirection = itemView.findViewById(R.id.tv_groove_direction);
            imageEdit = itemView.findViewById(R.id.image_edit_row);
            imageDelete = itemView.findViewById(R.id.image_delete_row);
        }
    }


}
