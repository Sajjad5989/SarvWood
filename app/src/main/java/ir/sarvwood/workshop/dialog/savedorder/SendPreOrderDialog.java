package ir.sarvwood.workshop.dialog.savedorder;

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
import ir.sarvwood.workshop.dialog.order.DetailOrderListener;
import ir.sarvwood.workshop.model.order.CheckableObject;
import ir.sarvwood.workshop.model.order.WoodModel;
import ir.sarvwood.workshop.utils.APP;

public class SendPreOrderDialog extends Dialog {

    private final DetailOrderListener detailOrderListener;
//    @BindView(R.id.tv_wood_type)
//    protected AppCompatTextView tvWoodType;
//    @BindView(R.id.tv_patterned)
//    protected AppCompatTextView tvPatterned;
//
//    @BindView(R.id.tv_wood_color)
//    protected AppCompatTextView tvWoodColor;
//
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
    private Context context;
    private WoodModel woodModel;

    public SendPreOrderDialog(@NonNull Context context, DetailOrderListener detailOrderListener, WoodModel woodOrderModel) {

        super(context);
        this.context = context;
        this.detailOrderListener = detailOrderListener;
        woodModel = woodOrderModel;
    }

    @OnClick(R.id.tv_cancel)
    void done() {
        detailOrderListener.onResponse(2);
        dismiss();
    }

    @OnClick(R.id.tv_correct)
    void correct() {
        detailOrderListener.onResponse(1);
        dismiss();
    }
    @OnClick(R.id.tv_delete)
    void delete() {
        detailOrderListener.onResponse(0);
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_send_order);
        ButterKnife.bind(this);
        loadDetail();
    }

    private void loadDetail() {

        if (woodModel != null) {
//            tvWoodType.setText( woodModel.getWoodType().getName());
//            tvPatterned.setText(woodModel.getPatterned() == 1 ? "راه دار می باشد" : "");
//            tvWoodColor.setText(woodModel.getColor());
//            tvPvcColor.setText(woodModel.getPvcColor());
            tvPvcDirection.setText(getCorrectString(
                    String.format("%s,%s", getCorrectFormat(woodModel.getPvcLengthNo()), getCorrectFormat(woodModel.getPvcWidthNo()))
            ));
//            tvPvcThickness.setText( getCorrectFormat(woodModel.getPvcThickness()));
            tvPaperSize.setText(String.format("%s * %s", woodModel.getWoodSheetLength(), woodModel.getWoodSheetWidth()));
            tvPaperCount.setText(String.valueOf(woodModel.getSheetCount()));
            tvPersianSheet.setText(
                    getCorrectString(
                    String.format("%s,%s", getCorrectFormat(woodModel.getPersianCutLenghtNo()), getCorrectFormat(woodModel.getPersianCutWidthNo()))));
            tvGroove.setText(
                    getCorrectString(
                    String.format("%s,%s", getCorrectFormat(woodModel.getGrooveLenghtNo()), getCorrectFormat(woodModel.getGrooveWidthNo()))));
        }

    }

    private String getCorrectFormat(CheckableObject checkableObject)
    {
        if ("هیچکدام".equals(checkableObject.getName()))
            return " ";
        return !checkableObject.isChecked()?" ":" [ "+checkableObject.getName()+" ]";

    }

    private String getCorrectString(String value)
    {
        if (",".equals(value.trim()))
            return "";
        return value;
    }
}
