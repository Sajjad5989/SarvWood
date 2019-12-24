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
import ir.sarvwood.workshop.model.order.CheckableObject;
import ir.sarvwood.workshop.model.order.WoodModel;

public class PreOrderAdapter extends RecyclerView.Adapter<PreOrderAdapter.ViewHolder> {


    private List<WoodModel> items;
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
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreOrderAdapter.ViewHolder holder, int position) {

        holder.tvOrderTitle.setText(String.valueOf(position + 1));
        String count = items.get(position).getPieceCount() +
                " برش " + " [ " + items.get(position).getPieceLength() + " * " + items.get(position).getPieceWidth() + " ]";
        holder.tvCount.setText(count);
        holder.tvPvcDirection.setText(String.format("%s%s", getCorrectFormat(items.get(position).getPvcLengthNo()), getCorrectFormat(items.get(position).getPvcWidthNo())));
        holder.tvPersianDirection.setText(String.format("%s%s", getCorrectFormat(items.get(position).getPersianCutLenghtNo()), getCorrectFormat(items.get(position).getPersianCutWidthNo())));
        holder.tvGrooveDirection.setText(String.format("%s%s", getCorrectFormat(items.get(position).getGrooveLenghtNo()), getCorrectFormat(items.get(position).getGrooveWidthNo())));
        holder.tvDesc.setText(items.get(position).getDesc());
        holder.imageEdit.setOnClickListener(view -> listener.onEdit(position));
        holder.imageDelete.setOnClickListener(view -> listener.onDelete(position));
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
        private AppCompatTextView tvDesc;
        private AppCompatImageView imageEdit;
        private AppCompatImageView imageDelete;


        private ViewHolder(final View itemView) {
            super(itemView);

            tvOrderTitle = itemView.findViewById(R.id.tv_pre_order_title);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvPvcDirection = itemView.findViewById(R.id.tv_pvc_direction);
            tvPersianDirection = itemView.findViewById(R.id.tv_persian_direction);
            tvGrooveDirection = itemView.findViewById(R.id.tv_groove_direction);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            imageEdit = itemView.findViewById(R.id.image_edit_row);
            imageDelete = itemView.findViewById(R.id.image_delete_row);
        }
    }


}
