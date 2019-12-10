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
import java.util.List;
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
import ir.sarvwood.workshop.adapter.RadioAdapter;
import ir.sarvwood.workshop.dialog.order.DetailOrderDialog;
import ir.sarvwood.workshop.model.order.CheckableObject;
import ir.sarvwood.workshop.model.order.WoodModel;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.SpeechIntentBuilder;
import ir.solmazzm.lib.engine.util.DialogUtil;


public class OrderFragment extends Fragment implements Step, BlockingStep {

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
    private int stepPosition;
    private RadioAdapter lengthAdapter;
    private RadioAdapter widthAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);

        //initialize your UI
        ButterKnife.bind(this, v);
        imageRecord.setOnClickListener(view -> setDescriptionByMic());
        return v;
    }

    private void setDescriptionByMic() {
        try {
            startActivityForResult(SpeechIntentBuilder.getInstance(getContext()).getSpeechIntent(), REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            APP.customToast(getResources().getString(R.string.speech_not_supported),getActivity());
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

        setVisibility(View.GONE);
        setVisibilityCheckBox(View.GONE);
        setVisibilityCustomDimension(View.GONE);

        etDescription.setInputType(InputType.TYPE_CLASS_TEXT);
        etDescription.setGravity(View.TEXT_ALIGNMENT_CENTER);
        etDescription.setHint("رنگ");
        etDescription.setText("");

        switch (stepPosition) {
            case 0:
                tvSubTitle.setText(getString(R.string.text_select_wood_type));
                setVisibilityCheckBox(View.VISIBLE);
                chkWoodArrow.setChecked(OrderActivity.woodModel.getPatterned() == 1);
                break;
            case 3:
            case 7:
            case 8:
                tvSubTitle.setText(R.string.text_select_width_length);
                break;
            case 1:
                tvSubTitle.setText(getString(R.string.text_wood_color));
                if (OrderActivity.woodModel != null)
                    etDescription.setText(OrderActivity.woodModel.getColor());
                setVisibility(View.VISIBLE);
                break;
            case 2:
                tvSubTitle.setText(getString(R.string.text_pvc_color));
                if (OrderActivity.woodModel != null)
                    etDescription.setText(OrderActivity.woodModel.getPvcColor());
                setVisibility(View.VISIBLE);
                break;
            case 4:
                tvSubTitle.setText(getString(R.string.text_pvc_thickness));
                break;
            case 5:
                tvSubTitle.setText(R.string.text_select_width_length);
                if (OrderActivity.woodModel.getWoodSheetList() != null) {
                    if (OrderActivity.woodModel.getWoodSheetList().getIndex() == 4) {
                        linearCustomDimension.setVisibility(View.VISIBLE);
                        etHeight.setText(String.valueOf(OrderActivity.woodModel.getWoodSheetLength()));
                        etWidth.setText(String.valueOf(OrderActivity.woodModel.getWoodSheetWidth()));
                    }
                }
                break;
            case 6:
                tvSubTitle.setText(R.string.text_paper_count);
                setVisibility();
                if (OrderActivity.woodModel != null)
                    etDescription.setText(String.valueOf(OrderActivity.woodModel.getSheetCount()));
                etDescription.setHint("تعداد");
                etDescription.setInputType(InputType.TYPE_CLASS_NUMBER);
                etDescription.setGravity(View.TEXT_ALIGNMENT_CENTER);
                break;
        }

        recyclerLength.setVisibility(View.GONE);
        recyclerWidth.setVisibility(View.GONE);

        callAdapterLength();
        callAdapterWidth();


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
        setOrder();

        if (stepPosition == 0) {
            if (OrderActivity.woodModel.getWoodType() == null) {
                APP.customToast(getString(R.string.text_select_one),getActivity());
                return;
            }
        } else if (stepPosition == 1) {
            if ("".equals(OrderActivity.woodModel.getColor())) {
                APP.customToast(getString(R.string.text_need_value),getActivity());
                return;
            }
        } else if (stepPosition == 3) {
            if (OrderActivity.woodModel.getPvcLengthNo() == null || OrderActivity.woodModel.getPvcWidthNo() == null) {
                APP.customToast(getString(R.string.text_choose_sirection),getActivity());
                return;
            }
        } else if (stepPosition == 4) {
            if (OrderActivity.woodModel.getPvcThickness() == null) {
                APP.customToast(getString(R.string.text_select_one),getActivity());
                return;
            }
        } else if (stepPosition == 5) {
            if (OrderActivity.woodModel.getWoodSheetList() != null) {
                if (OrderActivity.woodModel.getWoodSheetList().getIndex() == 4) {
                    linearCustomDimension.setVisibility(View.VISIBLE);

                    int sheetLen = Integer.valueOf(Objects.requireNonNull(etHeight.getText()).toString());
                    int sheetWid = Integer.valueOf(Objects.requireNonNull(etWidth.getText()).toString());

                    OrderActivity.woodModel.setWoodSheetLength(sheetLen);
                    OrderActivity.woodModel.setWoodSheetWidth(sheetWid);
                }
            } else {
                APP.customToast(getString(R.string.text_select_one),getActivity());
                return;
            }
        } else if (stepPosition == 6) {
            if (OrderActivity.woodModel.getSheetCount() == 0 || "".equals(String.valueOf(OrderActivity.woodModel.getSheetCount()))) {
                APP.customToast(getString(R.string.text_need_all_parameter),getActivity());
                return;
            }
        } else if (stepPosition == 7) {
            if (OrderActivity.woodModel.getPersianCutLenghtNo() == null ||
                    OrderActivity.woodModel.getPersianCutWidthNo() == null) {
                APP.customToast(getString(R.string.text_select_one),getActivity());
                return;
            }
        } else if (stepPosition == 8) {
            if (OrderActivity.woodModel.getGrooveLenghtNo() == null || OrderActivity.woodModel.getGrooveWidthNo() == null) {
                APP.customToast(getString(R.string.text_select_one),getActivity());
                return;
            }
        }

        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
//        if (selectLength == -1 || selectWidth == -1) {
//            APP.customToast(getString(R.string.text_error_select_one));
//            return;
//        }

        setOrder();
        showOrderDetail();
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    private void showOrderDetail() {
        DetailOrderDialog detailOrderDialog = new DetailOrderDialog(
                Objects.requireNonNull(getActivity()), res -> {
            if (res == 1) {
                addToOrderList();
                getActivity().finish();
            } else if (res == 0) {
                OrderActivity.woodModel = new WoodModel();
                getActivity().finish();
            }
        }, OrderActivity.woodModel);

        DialogUtil.showDialog(getActivity(), detailOrderDialog, false, true);

    }

    private void addToOrderList() {
        if (OrderActivity.listRowIdx > -1)
            OrderActivity.woodOrderModelList.set(OrderActivity.listRowIdx, OrderActivity.woodModel);
        else
            OrderActivity.woodOrderModelList.add(OrderActivity.woodModel);
        OrderActivity.woodModel = new WoodModel();
        OrderActivity.listRowIdx = -1;
    }

    private void callAdapterLength() {
        List<CheckableObject> list = getListByPosition();

        if (list != null)
            recyclerLength.setVisibility(View.VISIBLE);

        lengthAdapter = new RadioAdapter(list, (v, position) -> {
            onItemClick(position);
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerLength.setLayoutManager(gridLayoutManager);
        recyclerLength.setAdapter(lengthAdapter);
        recyclerLength.scheduleLayoutAnimation();
    }

    private void callAdapterWidth() {
        List<CheckableObject> list = getSecondListByPosition();

        if (list != null)
            recyclerWidth.setVisibility(View.VISIBLE);

        widthAdapter = new RadioAdapter(list, (v, position) -> {
            onItemWidthClick(position);
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerWidth.setLayoutManager(gridLayoutManager);
        recyclerWidth.setAdapter(widthAdapter);
        recyclerWidth.scheduleLayoutAnimation();
    }

    private List<CheckableObject> getListByPosition() {
        if (stepPosition == 0) {
            return OrderActivity.woodTypeList;
        } else if (stepPosition == 3)
            return OrderActivity.pvcLengthNoList;
        else if (stepPosition == 4)
            return OrderActivity.pvcThicknessList;
        else if (stepPosition == 5)
            return OrderActivity.woodSheetList;
        else if (stepPosition == 7)
            return OrderActivity.persianCutLengthNo;
        else if (stepPosition == 8)
            return OrderActivity.grooveLengthNo;
        else return new ArrayList<>();
    }

    private List<CheckableObject> getSecondListByPosition() {
        if (stepPosition == 3)
            return OrderActivity.pvcWidthNoList;
        else if (stepPosition == 7)
            return OrderActivity.persianCutWidthNo;
        else if (stepPosition == 8)
            return OrderActivity.grooveWidthNo;
        else return new ArrayList<>();
    }

    private void onItemClick(int position) {
        if (stepPosition == 0)
            changeWoodTypeListValue(position);
        else if (stepPosition == 3)
            changePvcLengthListValue(position);
        else if (stepPosition == 4)
            changePvcThicknessListValue(position);
        else if (stepPosition == 5)
            changeWoodSheetListValue(position);
        else if (stepPosition == 7)
            changePersianCutterLengthListValue(position);
        else if (stepPosition == 8)
            changeGrooveLengthListValue(position);
    }

    private void onItemWidthClick(int position) {
        if (stepPosition == 3)
            changePvcWidthListValue(position);
        else if (stepPosition == 7)
            changePersianCutterWidthListValue(position);
        else if (stepPosition == 8)
            changeGrooveWidthListValue(position);
    }

    private void changeWoodTypeListValue(int position) {
        for (CheckableObject co : OrderActivity.woodTypeList) {
            co.setChecked(false);
        }
        OrderActivity.woodTypeList.get(position).setChecked(true);
        OrderActivity.woodTypeList.get(position).setIndex(position);
        OrderActivity.woodModel.setWoodType(OrderActivity.woodTypeList.get(position));

        lengthAdapter.notifyDataSetChanged();
    }

    private void changePvcThicknessListValue(int position) {
        for (CheckableObject co : OrderActivity.pvcThicknessList) {
            co.setChecked(false);
        }
        OrderActivity.pvcThicknessList.get(position).setChecked(true);
        OrderActivity.pvcThicknessList.get(position).setIndex(position);
        OrderActivity.woodModel.setPvcThickness(OrderActivity.pvcThicknessList.get(position));

        lengthAdapter.notifyDataSetChanged();
    }

    private void changePvcLengthListValue(int position) {
        for (CheckableObject co : OrderActivity.pvcLengthNoList) {
            co.setChecked(false);
        }
        OrderActivity.pvcLengthNoList.get(position).setChecked(true);
        OrderActivity.pvcLengthNoList.get(position).setIndex(position);
        OrderActivity.woodModel.setPvcLengthNo(OrderActivity.pvcLengthNoList.get(position));
        lengthAdapter.notifyDataSetChanged();
    }

    private void changePvcWidthListValue(int position) {
        for (CheckableObject co : OrderActivity.pvcWidthNoList) {
            co.setChecked(false);
        }
        OrderActivity.pvcWidthNoList.get(position).setChecked(true);
        OrderActivity.pvcWidthNoList.get(position).setIndex(position);
        OrderActivity.woodModel.setPvcWidthNo(OrderActivity.pvcWidthNoList.get(position));
        widthAdapter.notifyDataSetChanged();
    }

    private void changePersianCutterLengthListValue(int position) {
        for (CheckableObject co : OrderActivity.persianCutLengthNo) {
            co.setChecked(false);
        }
        OrderActivity.persianCutLengthNo.get(position).setChecked(true);
        OrderActivity.persianCutLengthNo.get(position).setIndex(position);
        OrderActivity.woodModel.setPersianCutLenghtNo(OrderActivity.persianCutLengthNo.get(position));
        lengthAdapter.notifyDataSetChanged();
    }

    private void changePersianCutterWidthListValue(int position) {
        for (CheckableObject co : OrderActivity.persianCutWidthNo) {
            co.setChecked(false);
        }
        OrderActivity.persianCutWidthNo.get(position).setChecked(true);
        OrderActivity.persianCutWidthNo.get(position).setIndex(position);
        OrderActivity.woodModel.setPersianCutWidthNo(OrderActivity.persianCutWidthNo.get(position));
        widthAdapter.notifyDataSetChanged();
    }

    private void changeGrooveLengthListValue(int position) {
        for (CheckableObject co : OrderActivity.grooveLengthNo) {
            co.setChecked(false);
        }
        OrderActivity.grooveLengthNo.get(position).setChecked(true);
        OrderActivity.grooveLengthNo.get(position).setIndex(position);
        OrderActivity.woodModel.setGrooveLenghtNo(OrderActivity.grooveLengthNo.get(position));
        lengthAdapter.notifyDataSetChanged();
    }

    private void changeGrooveWidthListValue(int position) {
        for (CheckableObject co : OrderActivity.grooveWidthNo) {
            co.setChecked(false);
        }
        OrderActivity.grooveWidthNo.get(position).setChecked(true);
        OrderActivity.grooveWidthNo.get(position).setIndex(position);
        OrderActivity.woodModel.setGrooveWidthNo(OrderActivity.grooveWidthNo.get(position));
        widthAdapter.notifyDataSetChanged();
    }

    private void changeWoodSheetListValue(int position) {
        for (CheckableObject co : OrderActivity.woodSheetList) {
            co.setChecked(false);
        }
        OrderActivity.woodSheetList.get(position).setChecked(true);
        OrderActivity.woodSheetList.get(position).setIndex(position);

        OrderActivity.woodModel.setWoodSheetList(OrderActivity.woodSheetList.get(position));

        int sheetLen = 0;
        int sheetWid = 0;

        switch (position) {
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
                linearCustomDimension.setVisibility(View.VISIBLE);
                break;
        }

        OrderActivity.woodModel.setWoodSheetLength(sheetLen);
        OrderActivity.woodModel.setWoodSheetWidth(sheetWid);

        lengthAdapter.notifyDataSetChanged();
    }


    private void setOrder() {

        switch (stepPosition) {
            case 0:
                OrderActivity.woodModel.setPatterned(chkWoodArrow.isChecked() ? 1 : 0);
                break;
            case 1:
                OrderActivity.woodModel.setColor(Objects.requireNonNull(etDescription.getText()).toString());
                break;
            case 2:
                OrderActivity.woodModel.setPvcColor(Objects.requireNonNull(etDescription.getText()).toString());
                break;
            case 6:
                OrderActivity.woodModel.setSheetCount(Integer.valueOf(Objects.requireNonNull(etDescription.getText()).toString()));
                break;
        }
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

}
