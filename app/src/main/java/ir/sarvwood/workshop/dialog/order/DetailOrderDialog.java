package ir.sarvwood.workshop.dialog.order;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.model.order.CheckableObject;
import ir.sarvwood.workshop.model.order.WoodModel;
import ir.sarvwood.workshop.utils.APP;

public class DetailOrderDialog extends Dialog {

    private final DetailOrderListener detailOrderListener;
//    @BindView(R.id.tv_wood_type)
//    protected AppCompatTextView tvWoodType;
//    @BindView(R.id.tv_patterned)
//    protected AppCompatTextView tvPatterned;

//    @BindView(R.id.tv_wood_color)
//    protected AppCompatTextView tvWoodColor;

//    @BindView(R.id.tv_pvc_color)
//    protected AppCompatTextView tvPvcColor;

    @BindView(R.id.tv_pvc_direction)
    protected AppCompatTextView tvPvcDirection;

//    @BindView(R.id.tv_pvc_thickness)
//    protected AppCompatTextView tvPvcThickness;

    @BindView(R.id.tv_paper_size)
    protected AppCompatTextView tvPaperSize;

    @BindView(R.id.tv_paper_count)
    protected AppCompatTextView tvPaperCount;

    @BindView(R.id.tv_persian_sheet)
    protected AppCompatTextView tvPersianSheet;

    @BindView(R.id.tv_groove)
    protected AppCompatTextView tvGroove;
    @BindView(R.id.tv_desc)
    protected AppCompatTextView tvDesc;
    private WoodModel woodOrderModel;

    public DetailOrderDialog(@NonNull Context context, DetailOrderListener detailOrderListener, WoodModel woodOrderModel) {

        super(context);
        this.detailOrderListener = detailOrderListener;
        this.woodOrderModel = woodOrderModel;
    }

    @OnClick(R.id.tv_done)
    void done() {
        detailOrderListener.onResponse(1);
        dismiss();
    }

    @OnClick(R.id.tv_correct)
    void correct() {
        detailOrderListener.onResponse(2);
        dismiss();
    }
    @OnClick(R.id.tv_cancel)
    void cancelOrder() {
        detailOrderListener.onResponse(0);
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_detail_order);
        ButterKnife.bind(this);
        loadDetail();
    }

    private void loadDetail() {

        if (woodOrderModel != null) {
//            tvWoodType.setText(woodOrderModel.getWoodType().getName());
//            tvPatterned.setText(woodOrderModel.getPatterned() == 1 ? "راه دار می باشد" : "");
//            tvWoodColor.setText(woodOrderModel.getColor());
//            tvPvcColor.setText(woodOrderModel.getPvcColor());

            tvPvcDirection.setText(getCorrectString(String.format("%s,%s",
                    getCorrectFormat(woodOrderModel.getPvcLengthNo()),
                    getCorrectFormat(woodOrderModel.getPvcWidthNo()))));

            tvPaperSize.setText(String.format("%s * %s",
                    String.valueOf(woodOrderModel.getCutLength()), String.valueOf(woodOrderModel.getCutWidth())));

            tvPaperCount.setText(String.valueOf(woodOrderModel.getSheetCount()));
            tvPersianSheet.setText(getCorrectString(String.format("%s,%s",
                    getCorrectFormat(woodOrderModel.getPersianCutLenghtNo()),
                    getCorrectFormat(woodOrderModel.getPersianCutWidthNo()))));
            tvGroove.setText(getCorrectString(String.format("%s,%s",
                    getCorrectFormat(woodOrderModel.getGrooveLenghtNo()),
                    getCorrectFormat(woodOrderModel.getGrooveWidthNo()))));

            tvDesc.setText(woodOrderModel.getDesc());
        }
    }



    private String getCorrectFormat(CheckableObject checkableObject)
    {
        if ("هیچکدام".equals(checkableObject.getName()))
            return " ";
        return !checkableObject.isChecked()?" ":" "+checkableObject.getName();

    }

    private String getCorrectString(String value)
    {
        if (",".equals(value.trim()))
            return "";
        return value;
    }

}
