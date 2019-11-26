package ir.sarvwood.workshop.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mlsdev.animatedrv.AnimatedRecyclerView;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.activity.OrderActivity;
import ir.sarvwood.workshop.adapter.FillListAdapter;
import ir.sarvwood.workshop.dialog.order.DetailOrderDialog;
import ir.sarvwood.workshop.model.WoodOrderModel;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.SpeechIntentBuilder;
import ir.solmazzm.lib.engine.util.DialogUtil;


public class OrderFragment extends Fragment implements Step, BlockingStep {

    //  private  WoodOrderModel woodOrderModel = new WoodOrderModel();
    private final int REQ_CODE_SPEECH_INPUT = 1234;
    @BindView(R.id.recycler_length)
    protected AnimatedRecyclerView recyclerLength;
    @BindView(R.id.recycler_width)
    protected AnimatedRecyclerView recyclerWidth;
    @BindView(R.id.const_description)
    protected ConstraintLayout constDescription;
    @BindView(R.id.chk_wood_arrow)
    protected AppCompatCheckBox chkWoodArrow;
    @BindView(R.id.tv_wood_arrow_caption)
    protected AppCompatTextView tvWoodArrowCaption;
    @BindView(R.id.tv_sub_title)
    protected AppCompatTextView tvSubTitle;
    @BindView(R.id.et_description)
    protected AppCompatEditText etDescription;
    @BindView(R.id.et_height)
    protected AppCompatEditText etHeight;
    @BindView(R.id.et_width)
    protected AppCompatEditText etWidth;
    @BindView(R.id.linear_custom_dimension)
    protected LinearLayout linearCustomDimension;
    @BindView(R.id.image_record)
    protected AppCompatImageView imageRecord;
    private int customLengthWidth = 0;
    private int stepPosition;
    private int selectWidth;
    private int selectLength;
    private int sheetLen = 0;
    private int sheetWid = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);

        //initialize your UI
        ButterKnife.bind(this, v);
        APP.currentActivity = getActivity();
        imageRecord.setOnClickListener(view -> setDescriptionByMic());
        return v;
    }

    private void setDescriptionByMic() {
        try {
            startActivityForResult(SpeechIntentBuilder.getInstance(getContext()).getSpeechIntent(), REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            APP.customToast(getResources().getString(R.string.speech_not_supported));
        }
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {

        Bundle b1 = getArguments();
        if (b1 != null)
            stepPosition = b1.getInt("positionNumber");
        else Objects.requireNonNull(getActivity()).finish();

        customLengthWidth = 0;

        setVisibility(View.GONE);
        setVisibilityCheckBox(View.GONE);
        setVisibilityCustomDimension(View.GONE);

        etDescription.setInputType(InputType.TYPE_CLASS_TEXT);
        etDescription.setGravity(View.TEXT_ALIGNMENT_TEXT_START);
        etDescription.setMaxLines(300);
        etDescription.setHint("رنگ");
        String[] valueLength = null;
        String[] valueWidth = null;
        switch (stepPosition) {
            case 0:
                tvSubTitle.setText(getString(R.string.text_select_wood_type));
                valueLength = getResources().getStringArray(R.array.wood_type);
                setVisibilityCheckBox(View.VISIBLE);
                break;
            case 3:
            case 7:
            case 8:
                tvSubTitle.setText(R.string.text_select_width_length);
                valueLength = getResources().getStringArray(R.array.width_length);
                valueWidth = getResources().getStringArray(R.array.width_width);
                break;
            case 1:
                tvSubTitle.setText(getString(R.string.text_wood_color));
                setVisibility(View.VISIBLE);
                break;
            case 2:
                tvSubTitle.setText(getString(R.string.text_pvc_color));
                setVisibility(View.VISIBLE);
                break;
            case 4:
                tvSubTitle.setText(getString(R.string.text_pvc_thickness));
                valueLength = getResources().getStringArray(R.array.pvc_thickness);
                break;
            case 5:
                customLengthWidth = 1;
                tvSubTitle.setText(R.string.text_select_width_length);
                valueLength = getResources().getStringArray(R.array.sheet_dimensions);
                break;
            case 6:
                tvSubTitle.setText(R.string.text_paper_count);
                setVisibility();
                etDescription.setHint("تعداد");
                etDescription.setInputType(InputType.TYPE_CLASS_NUMBER);
                etDescription.setGravity(View.TEXT_ALIGNMENT_CENTER);
                break;
        }

        recyclerLength.setVisibility(View.GONE);
        recyclerWidth.setVisibility(View.GONE);

        if (valueLength != null) {
            recyclerLength.setVisibility(View.VISIBLE);
            callAdapterLength(valueLength);
        }
        if (valueWidth != null) {
            recyclerWidth.setVisibility(View.VISIBLE);
            callAdapterWidth(valueWidth);
        }


    }

    //
    private void setVisibility(int vs) {
        etDescription.setVisibility(vs);
        imageRecord.setVisibility(vs);
    }

    private void setVisibilityCheckBox(int vs) {
        chkWoodArrow.setVisibility(vs);
        tvWoodArrowCaption.setVisibility(vs);
    }

    private void setVisibilityCustomDimension(int vs) {
        linearCustomDimension.setVisibility(vs);
    }

    private void setVisibility() {

        etDescription.setVisibility(View.VISIBLE);
        // tvDescription.setVisibility(View.VISIBLE);
        imageRecord.setVisibility(View.GONE);
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        if (selectLength == -1 || selectWidth == -1) {
            APP.customToast(getString(R.string.text_error_select_one));
            return;
        }

        if (stepPosition == 1 || stepPosition == 6) {
            if ("".equals(Objects.requireNonNull(etDescription.getText()).toString())) {
                APP.customToast(getString(R.string.text_need_value));
                return;
            }
        }


        setOrder(selectLength, selectWidth);
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        if (selectLength == -1 || selectWidth == -1) {
            APP.customToast(getString(R.string.text_error_select_one));
            return;
        }

        setOrder(selectLength, selectWidth);
        showOrderDetail();
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

        getCurrentValue();
        callback.goToPrevStep();
    }

    private void showOrderDetail() {

        DetailOrderDialog detailOrderDialog = new DetailOrderDialog(
                getContext(), res -> {
            if (res) {
                addToOrderList(OrderActivity.woodOrderModel);
                getActivity().finish();
            }
            // sendOrderToApi();
        }
                , OrderActivity.woodOrderModel);

        DialogUtil.showDialog(getActivity(), detailOrderDialog, false, true);

    }

    private void addToOrderList(WoodOrderModel woodOrderModel) {
        OrderActivity.woodOrderModelList.add(woodOrderModel);
    }

    private void callAdapterLength(String[] inputValue) {
        selectLength = -1;

        FillListAdapter fillListAdapter = new FillListAdapter(inputValue, (v, position) -> {
            selectLength = position;

            setVisibilityCustomDimension(
                    customLengthWidth == 1 && position == 4 ?
                            View.VISIBLE : View.GONE);


        }, getCurrentValue());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerLength.setLayoutManager(gridLayoutManager);
        recyclerLength.setAdapter(fillListAdapter);
        recyclerLength.scheduleLayoutAnimation();
    }

    private void callAdapterWidth(String[] inputValue) {
        selectWidth = -1;

        FillListAdapter fillListAdapter = new FillListAdapter(inputValue, (v, position) -> {
            selectWidth = position;

        }, getCurrentValue());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerWidth.setLayoutManager(gridLayoutManager);
        recyclerWidth.setAdapter(fillListAdapter);
        recyclerWidth.scheduleLayoutAnimation();
    }

    private int getCurrentValue() {


        switch (stepPosition) {
            case 0:
            case 7:
            case 8:
               // etDescription.setText(OrderActivity.woodOrderModel.getWoodDescription());

                return 0;//OrderActivity.woodOrderModel.getWoodType();
//                OrderActivity.woodOrderModel.setWoodDescription(etDescription.getText().toString());
//                break;
//            case 1:
//                etDescription.setText(OrderActivity.woodOrderModel.getWoodColor());
//                break;
//            case 2:
//                etDescription.setText(OrderActivity.woodOrderModel.getPvcColor());
//                break;
//            case 3:
//                return OrderActivity.woodOrderModel.getDirection();
////                break;
//            case 4:
//                return OrderActivity.woodOrderModel.getThickness();
////                break;
////            case 5:
////                String[] value = getActivity().getResources().getStringArray(R.array.sheet_dimensions);
////                OrderActivity.woodOrderModel.setSize(value[itemSelectId]);
////                break;
//            case 6:
//                etDescription.setText(OrderActivity.woodOrderModel.getPaperCount());
//                break;
//            case 7:
//                return OrderActivity.woodOrderModel.getPersianCutter();
////                break;
//
//                return OrderActivity.woodOrderModel.getGroove();
//                break;
            default:
                return -1;
        }

    }

    private void setOrder(int selectLength, int selectWidth) {


        switch (stepPosition) {
            case 0:
                // woodType = selectLength;
                OrderActivity.woodOrderModel.setWoodType(selectLength);
                OrderActivity.woodOrderModel.setPatterned(chkWoodArrow.isChecked() ? 1 : 0);
                //
                //woodOrderModel.setWoodDescription(Objects.requireNonNull(etDescription.getText()).toString());
                break;
            case 1:
//                woodColor = Objects.requireNonNull(etDescription.getText()).toString();
                OrderActivity.woodOrderModel.setColor(Objects.requireNonNull(etDescription.getText()).toString());
                break;
            case 2:
//                pvcColor = Objects.requireNonNull(etDescription.getText()).toString();
                OrderActivity.woodOrderModel.setPvcColor(Objects.requireNonNull(etDescription.getText()).toString());
                break;
            case 3://جهت پی وی سی
//                String[] inputValue = getResources().getStringArray(R.array.sheet_dimensions);
//                getDimensionSeparate(inputValue[itemSelectId]);
//                OrderActivity.woodOrderModel.setDirection(itemSelectId);
                OrderActivity.woodOrderModel.setPvcLenghtNo(selectLength);
                OrderActivity.woodOrderModel.setPvcWidthNo(selectWidth);
//                pvcLen = selectLength;
//                pvcWid = selectWidth;
                break;
            case 4:
//                pvcThickness = selectLength;
                OrderActivity.woodOrderModel.setPvcThickness(selectLength);
                break;
            case 5:
                // String[] value = APP.currentActivity.getResources().getStringArray(R.array.sheet_dimensions);
                switch (selectLength) {
                    case 0:
                        sheetLen = 366;
                        sheetWid = 183;
                        break;
                    case 1:
                        sheetLen = 280;
                        sheetWid = 122;
                        break;
                    case 2:
                        sheetLen = 240;
                        sheetWid = 122;
                        break;
                    case 3:
                        sheetLen = 210;
                        sheetWid = 183;
                        break;
                    default:
                        sheetLen = Integer.valueOf(Objects.requireNonNull(etHeight.getText()).toString());
                        sheetWid = Integer.valueOf(Objects.requireNonNull(etWidth.getText()).toString());
                        break;
                }

                OrderActivity.woodOrderModel.setWoodSheetLength(sheetLen);
                OrderActivity.woodOrderModel.setWoodSheetWidth(sheetWid);
                break;
            case 6:
//                sheetCount = Integer.valueOf(Objects.requireNonNull(etDescription.getText()).toString());
                OrderActivity.woodOrderModel.setSheetCount(Integer.valueOf(Objects.requireNonNull(etDescription.getText()).toString()));
                break;
            case 7:
                OrderActivity.woodOrderModel.setPersianCutLenghtNo(selectLength);
                OrderActivity.woodOrderModel.setPersianCutWidthNo(selectWidth);
                break;
            case 8:
                OrderActivity.woodOrderModel.setGrooveLenghtNo(selectLength);
                OrderActivity.woodOrderModel.setGrooveWidthNo(selectWidth);
                break;
        }

//         woodOrderModel = WoodOrderModel.builder().
//                woodType(woodType)
//                .color(woodColor)
//                .pvcColor(pvcColor)
//                .pvcThickness(pvcThickness)
//                .pvcLenghtNo(pvcLen)
//                .pvcWidthNo(pvcWid)
//                .woodSheetLength(sheetLen)
//                .woodSheetWidth(sheetWid)
//                .sheetCount(sheetCount)
//                .persianCutLenghtNo(cutPersianLen)
//                .persianCutWidthNo(cutPersianWid)
//                .grooveLenghtNo(grooveLen)
//                .grooveWidthNo(grooveWid)
//                .patterned(1)
//                .desc("")
//                .build();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && null != data) {
            if (requestCode == REQ_CODE_SPEECH_INPUT) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                etDescription.setText(result.get(0).trim());
            }
        }
    }


//    private String[] getDimensionSeparate(String value)
//    {
//       //String[] myValue = value.split("*");
//       return  myValue;
//
//    }

}
