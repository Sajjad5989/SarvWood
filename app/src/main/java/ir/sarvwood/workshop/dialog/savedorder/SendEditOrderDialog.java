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

public class SendEditOrderDialog extends Dialog {

    private final DetailOrderListener detailOrderListener;
    @BindView(R.id.tv_wood_type)
    protected AppCompatTextView tvWoodType;
    @BindView(R.id.tv_patterned)
    protected AppCompatTextView tvPatterned;

    @BindView(R.id.tv_wood_color)
    protected AppCompatTextView tvWoodColor;

    @BindView(R.id.tv_pvc_color)
    protected AppCompatTextView tvPvcColor;

    @BindView(R.id.tv_pvc_direction)
    protected AppCompatTextView tvPvcDirection;

    @BindView(R.id.tv_pvc_thickness)
    protected AppCompatTextView tvPvcThickness;

    @BindView(R.id.tv_paper_size)
    protected AppCompatTextView tvPaperSize;

    @BindView(R.id.tv_paper_count)
    protected AppCompatTextView tvPaperCount;

    @BindView(R.id.tv_persian_sheet)
    protected AppCompatTextView tvPersianSheet;

    @BindView(R.id.tv_groove)
    protected AppCompatTextView tvGroove;
    private WoodModel itemReturnValueList;

    public SendEditOrderDialog(@NonNull Context context, DetailOrderListener detailOrderListener,
                               WoodModel itemReturnValueList) {

        super(context);
        this.detailOrderListener = detailOrderListener;
        this.itemReturnValueList = itemReturnValueList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_send_order);
        ButterKnife.bind(this);
        loadDetail();
    }

    private void loadDetail() {

        if (itemReturnValueList != null) {

            tvWoodType.setText(itemReturnValueList.getWoodType().getName());
            tvPatterned.setText(itemReturnValueList.getPatterned() == 1 ? "راه دار می باشد" : "");
            tvWoodColor.setText(itemReturnValueList.getColor());
            tvPvcColor.setText(itemReturnValueList.getPvcColor());

            tvPvcDirection.setText(getCorrectString(String.format("%s,%s",
                    getCorrectFormat(itemReturnValueList.getPvcLengthNo()),
                    getCorrectFormat(itemReturnValueList.getPvcWidthNo()))));


            tvPvcThickness.setText(itemReturnValueList.getPvcThickness().getName());

            tvPaperSize.setText(String.format("%s * %s", (itemReturnValueList.getWoodSheetLength()),
                    (itemReturnValueList.getWoodSheetWidth())));
            tvPaperCount.setText(String.valueOf(itemReturnValueList.getSheetCount()));

            tvPersianSheet.setText(getCorrectString(String.format("%s,%s", getCorrectFormat(itemReturnValueList.getPersianCutLenghtNo()),
                    getCorrectFormat(itemReturnValueList.getPersianCutWidthNo()))));
            tvGroove.setText(String.format("%s,%s",getCorrectFormat(itemReturnValueList.getGrooveLenghtNo()
            ), getCorrectFormat(itemReturnValueList.getGrooveWidthNo())));
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
        if (",".equals(value))
            return "";
        return value;
    }
}
