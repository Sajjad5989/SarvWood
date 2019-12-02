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
import ir.sarvwood.workshop.model.WoodOrderModel;
import ir.sarvwood.workshop.model.order.WoodModel;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.WoodOrderModelUtils;
import ir.sarvwood.workshop.webservice.myorders.MyOrderReturnValue;

public class SendPreOrderDialog extends Dialog {

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
    private Context context;
    private WoodModel woodOrderModel;

    public SendPreOrderDialog(@NonNull Context context, DetailOrderListener detailOrderListener, WoodModel woodOrderModel) {

        super(context);
        this.context = context;
        this.detailOrderListener = detailOrderListener;
        this.woodOrderModel = woodOrderModel;
    }

    @OnClick(R.id.tv_cancel)
    void done() {
        detailOrderListener.onResponse(false);
        dismiss();
    }

    @OnClick(R.id.tv_correct)
    void correct() {
        detailOrderListener.onResponse(true);
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_send_order);
        APP.currentActivity = getOwnerActivity();
        ButterKnife.bind(this);
        loadDetail();
    }

    private void loadDetail() {

        if (woodOrderModel != null) {
            tvWoodType.setText( woodOrderModel.getWoodType().getName());
            tvPatterned.setText(woodOrderModel.getPatterned() == 1 ? "راه دار می باشد" : "");
            tvWoodColor.setText(woodOrderModel.getColor());
            tvPvcColor.setText(woodOrderModel.getPvcColor());
            tvPvcDirection.setText(String.format("%s,%s", woodOrderModel.getPvcLengthNo().getName(), woodOrderModel.getPvcWidthNo().getName()));
            tvPvcThickness.setText( woodOrderModel.getPvcThickness().getName());
            tvPaperSize.setText(String.format("%s * %s", woodOrderModel.getWoodSheetLength(), woodOrderModel.getWoodSheetWidth()));
            tvPaperCount.setText(String.valueOf(woodOrderModel.getSheetCount()));
            tvPersianSheet.setText(String.format("%s,%s", woodOrderModel.getPersianCutLenghtNo().getName(), woodOrderModel.getPersianCutWidthNo().getName()));
            tvGroove.setText(String.format("%s,%s", woodOrderModel.getGrooveLenghtNo().getName(), woodOrderModel.getGrooveWidthNo().getName()));
        }

    }
}
