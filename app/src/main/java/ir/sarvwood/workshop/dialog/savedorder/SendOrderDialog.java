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
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.WoodOrderModelUtils;
import ir.sarvwood.workshop.webservice.myorders.MyOrderReturnValue;

public class SendOrderDialog extends Dialog {

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
    private WoodOrderModel woodOrderModel;

    public SendOrderDialog(@NonNull Context context, DetailOrderListener detailOrderListener, WoodOrderModel woodOrderModel) {

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

        if (woodOrderModel == null)
            return;

        WoodOrderModelUtils woodOrderModelUtils = new WoodOrderModelUtils();

//        tvWoodType.setText(woodOrderModelUtils.getNameByPosition(context, 0, woodOrderModel.getWoodType(),0));
//        tvPatterned.setText(myOrderReturnValue.getPatterned()==1?"راه دار می باشد":"");
//        tvWoodColor.setText(woodOrderModel.getColor());
//        tvPvcColor.setText(woodOrderModel.getPvcColor());
//        tvPvcDirection.setText(woodOrderModelUtils.getNameByPosition(context, 3, woodOrderModel.getPvcLenghtNo(),woodOrderModel.getPvcWidthNo()));
//        tvPvcThickness.setText(woodOrderModelUtils.getNameByPosition(context, 4, woodOrderModel.getPvcThickness(),0));
//        tvPaperSize.setText(String.format("%d * %d", woodOrderModel.getWoodSheetLength(), woodOrderModel.getWoodSheetWidth()));
//        tvPaperCount.setText(String.valueOf(woodOrderModel.getSheetCount()));
//        tvPersianSheet.setText(woodOrderModelUtils.getNameByPosition(context, 7, woodOrderModel.getPersianCutLenghtNo(),woodOrderModel.getPersianCutWidthNo()));
//        tvGroove.setText(woodOrderModelUtils.getNameByPosition(context, 8, woodOrderModel.getGrooveLenghtNo(),woodOrderModel.getGrooveWidthNo()));


    }
}
