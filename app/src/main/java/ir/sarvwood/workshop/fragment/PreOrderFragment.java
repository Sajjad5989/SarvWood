package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.activity.OrderActivity;
import ir.sarvwood.workshop.activity.OrderHeaderActivity;
import ir.sarvwood.workshop.adapter.PreOrderAdapter;
import ir.sarvwood.workshop.interfaces.IListActionListener;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.model.WoodOrderModel;
import ir.sarvwood.workshop.model.order.WoodModel;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.webservice.insertorder.InsertOrderBody;
import ir.sarvwood.workshop.webservice.insertorder.InsertOrderBodyReturnValue;
import ir.sarvwood.workshop.webservice.insertorder.InsertOrderController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.updateorder.UpdateOrderBody;
import ir.sarvwood.workshop.webservice.updateorder.UpdateOrderController;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class PreOrderFragment extends Fragment {

    private static final int PRE_ORDER_ITEMS = 2;

    private static int currentType;
    private static int currentOrderStatus;
    @BindView(R.id.recycler_pre_order_list)
    protected AnimatedRecyclerView recyclerView;

    @BindView(R.id.tv_title)
    protected AppCompatTextView tvTitle;

    @BindView(R.id.tv_brand)
    protected AppCompatTextView tvBrand;

    @BindView(R.id.tv_code)
    protected AppCompatTextView tvCode;

    @BindView(R.id.tv_size)
    protected AppCompatTextView tvSize;

    @BindView(R.id.tv_pvc)
    protected AppCompatTextView tvPvc;

    @BindView(R.id.image_edit_header)
    protected AppCompatImageView imageEditHeader;

    private int userId;
    private String token;
    private ProgressDialog progress;

    public static PreOrderFragment newInstance(int type, int orderStatus) {

        currentType = type;
        currentOrderStatus = orderStatus;
        return new PreOrderFragment();
    }

    @OnClick(R.id.btn_send_order)
    void sendOrderToApi() {

        if (currentOrderStatus > 1) {
            APP.customToast(getString(R.string.text_can_edit), getActivity());
            return;
        }

        if (OrderActivity.woodOrderModelList.size() != 0) {
            showProgress();
            userId = GeneralPreferences.getInstance(getActivity()).getCustomerId();
            token = GeneralPreferences.getInstance(getActivity()).getToken();

            WoodModel wModel = OrderActivity.woodOrderModelList.get(0);
            int orderId = wModel.getOrderId();
            if (orderId > 0)
                updateOrder(orderId);
            else
                insertOrder();
        } else
            APP.customToast("اقلام برش ثبت نشده است", getActivity());
    }

    private void showProgress() {
        progress = new ProgressDialog(getActivity());
        String message = getString(R.string.text_please_wait);
        SpannableString spannableString = new SpannableString(message);

        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(getActivity().getAssets(),
                "fonts/iran_sans.ttf"));
        spannableString.setSpan(typefaceSpan, 0, message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        progress.setMessage(spannableString);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    @OnClick(R.id.btn_new_order)
    void openOrderList() {
        if (currentOrderStatus > 1) {
            APP.customToast(getString(R.string.text_can_edit), getActivity());
            return;
        }

        OrderActivity.listRowIdx = -1;
        startActivity(new Intent(getActivity(), OrderActivity.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pre_order, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageEditHeader.setOnClickListener(view1 -> openDetails());

    }

    private void openDetails() {
        if (currentOrderStatus > 1) {
            APP.customToast(getString(R.string.text_can_edit), getActivity());
            return;
        }

        Intent intent = new Intent(getActivity(), OrderHeaderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("headerWoodModel", OrderHeaderActivity.headerWoodModel);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void callOrderOrPreOrder() {

        fillPreOrderList();
    }

    private void fillPreOrderList() {
        if (currentType == PRE_ORDER_ITEMS) {
            setOrderHeader();
        }

        PreOrderAdapter preOrderAdapter = new PreOrderAdapter(OrderActivity.woodOrderModelList,
                new IListActionListener() {
                    @Override
                    public void onDelete(int position) {


                        if (currentOrderStatus > 1) {
                            APP.customToast(getString(R.string.text_can_edit), getActivity());
                            return;
                        }

                        OrderActivity.woodOrderModelList.remove(position);
                        callOrderOrPreOrder();
                    }

                    @Override
                    public void onEdit(int position) {
                        editOrder(position);
                    }
                });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(preOrderAdapter);
        recyclerView.scheduleLayoutAnimation();
    }

    private void setOrderHeader() {
        String headerOrder =
                OrderHeaderActivity.headerWoodModel.getWoodType().getName() + "،"
                        + (OrderHeaderActivity.headerWoodModel.getPatterned() == 1 ? " راه دار " + " ،" : "")
                        + OrderHeaderActivity.headerWoodModel.getColor();

        tvTitle.setText(Html.fromHtml(headerOrder));
        tvBrand.setText(OrderHeaderActivity.headerWoodModel.getBrand());
        tvSize.setText(String.format("%s * %s", OrderHeaderActivity.headerWoodModel.getWoodSheetLength(), OrderHeaderActivity.headerWoodModel.getWoodSheetWidth()));
        tvCode.setText(OrderHeaderActivity.headerWoodModel.getCode());
        tvPvc.setText(String.format("%s - %s", OrderHeaderActivity.headerWoodModel.getPvcThickness().getName(), OrderHeaderActivity.headerWoodModel.getPvcColor())
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        callOrderOrPreOrder();
        setOrderHeader();
    }

    private void editOrder(int position) {

        if (currentOrderStatus > 1) {
            APP.customToast(getString(R.string.text_can_edit), getActivity());
            return;
        }
        OrderActivity.listRowIdx = position;
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("woodModel", OrderActivity.woodOrderModelList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private InsertOrderBody getBodyValue() {

        return InsertOrderBody
                .builder()
                .customerId(userId)
                .woodType(OrderHeaderActivity.headerWoodModel.getWoodType().getIndex() + 1)
                .woodColor(OrderHeaderActivity.headerWoodModel.getColor())
                .woodCode(OrderHeaderActivity.headerWoodModel.getCode())
                .woodBrand(OrderHeaderActivity.headerWoodModel.getBrand())
                .pvcColor(OrderHeaderActivity.headerWoodModel.getPvcColor())
                .pvcThickness(OrderHeaderActivity.headerWoodModel.getPvcThickness().getIndex())
                .woodSheetLength(OrderHeaderActivity.headerWoodModel.getWoodSheetLength())
                .woodSheetWidth(OrderHeaderActivity.headerWoodModel.getWoodSheetWidth())
                .patterned(OrderHeaderActivity.headerWoodModel.getPatterned())
                .desc("")
                .items(convertToListOderModel())
                .build();
    }

    private List<WoodOrderModel> convertToListOderModel() {
        WoodModel wModel;
        List<WoodOrderModel> myList = new ArrayList<>();
        WoodOrderModel woodOrderModel = new WoodOrderModel();
        for (int i = 0; i < OrderActivity.woodOrderModelList.size(); i++) {

            wModel = OrderActivity.woodOrderModelList.get(i);


            woodOrderModel.setPieceLength(wModel.getPieceLength());
            woodOrderModel.setPieceWidth(wModel.getPieceWidth());
            woodOrderModel.setPieceCount(wModel.getPieceCount());

            woodOrderModel.setPvcLenghtNo(wModel.getPvcLengthNo().getIndex());
            woodOrderModel.setPvcWidthNo(wModel.getPvcWidthNo().getIndex());

            woodOrderModel.setPersianCutLenghtNo(wModel.getPersianCutLenghtNo().getIndex());
            woodOrderModel.setPersianCutWidthNo(wModel.getPersianCutWidthNo().getIndex());

            woodOrderModel.setGrooveLenghtNo(wModel.getGrooveLenghtNo().getIndex());
            woodOrderModel.setGrooveWidthNo(wModel.getGrooveWidthNo().getIndex());

            woodOrderModel.setDesc(wModel.getDesc());


            myList.add(woodOrderModel);
            woodOrderModel = new WoodOrderModel();
        }
        return myList;
    }

    private void updateOrder(int orderId) {
        UpdateOrderController updateOrderController = new UpdateOrderController();
        updateOrderController.start(userId, token, getUpdateOrderBody(orderId), new IResponseListener<SarvApiResponseNoList>() {
            @Override
            public void onSuccess(SarvApiResponseNoList response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    APP.customToast(getString(R.string.text_successful), getActivity());
                    OrderActivity.woodOrderModelList = new ArrayList<>();
                    progress.hide();
                    getActivity().finish();
                } else {
                    APP.customToast(response.getMessage(), getActivity());
                    progress.hide();
                }
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error, getActivity());
                progress.hide();
            }
        });
    }

    private UpdateOrderBody getUpdateOrderBody(int orderId) {
        return UpdateOrderBody.builder()
                .customerId(userId)
                .orderId(orderId)
                .woodType(OrderHeaderActivity.headerWoodModel.getWoodType().getIndex() + 1)
                .woodColor(OrderHeaderActivity.headerWoodModel.getColor())
                .woodCode(OrderHeaderActivity.headerWoodModel.getCode())
                .woodBrand(OrderHeaderActivity.headerWoodModel.getBrand())
                .pvcColor(OrderHeaderActivity.headerWoodModel.getPvcColor())
                .pvcThickness(OrderHeaderActivity.headerWoodModel.getPvcThickness().getIndex())
                .woodSheetLength(OrderHeaderActivity.headerWoodModel.getWoodSheetLength())
                .woodSheetWidth(OrderHeaderActivity.headerWoodModel.getWoodSheetWidth())
                .patterned(OrderHeaderActivity.headerWoodModel.getPatterned())
                .desc("")
                .items(convertToListOderModel())
                .build();
    }

    private void insertOrder() {

        InsertOrderController insertOrderController = new InsertOrderController();
        InsertOrderBody insertOrderBody = getBodyValue();
        insertOrderController.start(userId, token, insertOrderBody, new IResponseListener<SarvApiResponse<InsertOrderBodyReturnValue>>() {
            @Override
            public void onSuccess(SarvApiResponse response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    InsertOrderBodyReturnValue data = (InsertOrderBodyReturnValue) response.getData().get(0);
                    GeneralPreferences.getInstance(getActivity()).putToken(data.getAccessToken());
                    APP.customToast(getString(R.string.text_successful), getActivity());
                    OrderActivity.woodOrderModelList = new ArrayList<>();
                    progress.hide();
                    getActivity().finish();
                } else {
                    APP.customToast(response.getMessage(), getActivity());
                    progress.hide();
                }
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error, getActivity());
                progress.hide();
            }
        });
    }

}
