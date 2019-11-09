package ir.sarvwood.workshop.fragment;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.animatedrv.AnimatedRecyclerView;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
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
import ir.sarvwood.workshop.utils.APP;
import ir.solmazzm.lib.engine.util.DialogUtil;


public class OrderFragment extends Fragment implements Step, BlockingStep {


    @BindView(R.id.recycler_selected_values)
    protected AnimatedRecyclerView selectionValueRecyclerView;
    @BindView(R.id.const_description)
    protected ConstraintLayout constDescription;
    @BindView(R.id.tv_description)
    protected AppCompatTextView tvDescription;
    @BindView(R.id.et_description)
    protected AppCompatEditText etDescription;
    @BindView(R.id.image_record)
    protected AppCompatImageView imageRecord;

    private int stepPosition;
    private int selectItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);

        //initialize your UI
        ButterKnife.bind(this, v);
        return v;
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

        etDescription.setInputType(InputType.TYPE_CLASS_TEXT);
        String[] value = null;
        switch (stepPosition) {
            case 0:
                value = getResources().getStringArray(R.array.wood_type);
                setVisibility(View.VISIBLE);
                break;
            case 3:
            case 7:
            case 8:
                value = getResources().getStringArray(R.array.direction);
                break;
            case 1:
                setVisibility(View.VISIBLE);
                tvDescription.setText(getString(R.string.text_wood_color));
                break;
            case 2:
                setVisibility(View.VISIBLE);
                tvDescription.setText(getString(R.string.text_pvc_color));
                break;
            case 4:
                value = getResources().getStringArray(R.array.pvc_thickness);
                break;
            case 5:
                value = getResources().getStringArray(R.array.sheet_dimensions);
                break;
            case 6:
                setVisibility();
                etDescription.setInputType(InputType.TYPE_CLASS_NUMBER);
                tvDescription.setText(R.string.text_paper_count);
                break;
        }

        if (value != null) {
            callAdapterForRecycler(value);
        }

    }

    private void setVisibility(int vs) {

        etDescription.setVisibility(vs);
        tvDescription.setVisibility(vs);
        imageRecord.setVisibility(vs);
    }

    private void setVisibility() {

        etDescription.setVisibility(View.VISIBLE);
        tvDescription.setVisibility(View.VISIBLE);
        imageRecord.setVisibility(View.GONE);
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        if (selectItem == -1) {
            APP.customToast(getString(R.string.text_error_select_one));
            return;
        }
        setOrder(selectItem);
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        if (selectItem == -1) {
            APP.customToast(getString(R.string.text_error_select_one));
            return;
        }

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
            if (res)
                sendOrderToApi();
        }
                , OrderActivity.woodOrderModel);

        DialogUtil.showDialog(getActivity(), detailOrderDialog, false, true);

    }

    private void sendOrderToApi() {

    }

    private void callAdapterForRecycler(String[] inputValue) {
        selectItem = -1;

        FillListAdapter fillListAdapter = new FillListAdapter(inputValue, (v, position) -> {
            selectItem = position;

        }, getCurrentValue());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        selectionValueRecyclerView.setLayoutManager(gridLayoutManager);
        selectionValueRecyclerView.setAdapter(fillListAdapter);
        selectionValueRecyclerView.scheduleLayoutAnimation();
    }

    private Object getCurrentValue(){

        switch (stepPosition) {
            case 0:
                etDescription.setText(OrderActivity.woodOrderModel.getWoodDescription());

              return   OrderActivity.woodOrderModel.getWoodType();
//                OrderActivity.woodOrderModel.setWoodDescription(etDescription.getText().toString());
//                break;
            case 1:
                etDescription.setText(OrderActivity.woodOrderModel.getWoodColor());
                break;
            case 2:
                etDescription.setText(OrderActivity.woodOrderModel.getPvcColor());
                break;
            case 3:
                return OrderActivity.woodOrderModel.getDirection();
//                break;
            case 4:
                return OrderActivity.woodOrderModel.getThickness();
//                break;
//            case 5:
//                String[] value = getActivity().getResources().getStringArray(R.array.sheet_dimensions);
//                OrderActivity.woodOrderModel.setSize(value[itemSelectId]);
//                break;
            case 6:
                etDescription.setText(OrderActivity.woodOrderModel.getPaperCount());
                break;
            case 7:
                return OrderActivity.woodOrderModel.getPersianCutter();
//                break;
            case 8:
                return OrderActivity.woodOrderModel.getGroove();
//                break;
                default: return null;
        }
        return null;
    }


    private void setOrder(int itemSelectId) {

        switch (stepPosition) {
            case 0:
                OrderActivity.woodOrderModel.setWoodType(itemSelectId);
                OrderActivity.woodOrderModel.setWoodDescription(etDescription.getText().toString());
                break;
            case 1:
                OrderActivity.woodOrderModel.setWoodColor(etDescription.getText().toString());
                break;
            case 2:
                OrderActivity.woodOrderModel.setPvcColor(etDescription.getText().toString());
                break;
            case 3:
                OrderActivity.woodOrderModel.setDirection(itemSelectId);
                break;
            case 4:
                OrderActivity.woodOrderModel.setThickness(itemSelectId);
                break;
            case 5:
                String[] value = getActivity().getResources().getStringArray(R.array.sheet_dimensions);
                OrderActivity.woodOrderModel.setSize(value[itemSelectId]);
                break;
            case 6:
                OrderActivity.woodOrderModel.setPaperCount(Integer.valueOf(etDescription.getText().toString()));
                break;
            case 7:
                OrderActivity.woodOrderModel.setPersianCutter(itemSelectId);
                break;
            case 8:
                OrderActivity.woodOrderModel.setGroove(itemSelectId);
                break;
        }
    }

}
