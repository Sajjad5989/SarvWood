package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.activity.OrderActivity;
import ir.sarvwood.workshop.adapter.OrderItemsAdapter;
import ir.sarvwood.workshop.adapter.PreOrderAdapter;
import ir.sarvwood.workshop.dialog.savedorder.SendPreOrderDialog;
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
import ir.solmazzm.lib.engine.util.DialogUtil;

public class PreOrderFragment extends Fragment {


    private static final int ORDER_ITEMS = 1;
    private static final int PRE_ORDER_ITEMS = 2;

    private static int currentType;
    @BindView(R.id.recycler_pre_order_list)
    protected AnimatedRecyclerView recyclerView;
    @BindView(R.id.tv_no_row)
    protected AppCompatTextView tvNoData;
    @BindView(R.id.tv_insert_row)
    protected AppCompatTextView tvInsertRow;


    private int userId;
    private String token;

    public static PreOrderFragment newInstance(int type) {

        currentType = type;
        return new PreOrderFragment();
    }


    @OnClick(R.id.btn_send_order)
    void sendOrderToApi() {

        if (OrderActivity.woodOrderModelList.size() != 0) {

            userId = GeneralPreferences.getInstance(APP.currentActivity).getCustomerId();
            token = GeneralPreferences.getInstance(APP.currentActivity).getToken();

            WoodModel wModel = OrderActivity.woodOrderModelList.get(0);
            int orderId = wModel.getOrderId();
            if (orderId > 0)
                updateOrder(orderId);
            else
                insertOrder();
        }
    }


    @OnClick(R.id.btn_new_order)
    void openOrderList() {

//        DiscardOptionDialog discardOptionDialog = new DiscardOptionDialog(
//                Objects.requireNonNull(getActivity()), new RecyclerViewClickListenerDiscard() {
//            @Override
//            public void onItemClick(int id) {
//                discardOrder(id);
//            }
//        }
//        );
//        DialogUtil.showDialog(getActivity(), discardOptionDialog, false, true);

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


    }

    private void callOrderOrPreOrder() {
        if (currentType == ORDER_ITEMS)//لیست اقلام دریافتی سفارش از سایت که قبلا ذخیره شده است --
        {
            fillOrderList();
        } else if (currentType == PRE_ORDER_ITEMS) {
            fillPreOrderList();
        }

    }

    private void fillPreOrderList() {

        tvNoData.setVisibility(OrderActivity.woodOrderModelList.size() == 0 ? View.VISIBLE : View.GONE);
        tvInsertRow.setVisibility(OrderActivity.woodOrderModelList.size() == 0 ? View.VISIBLE : View.GONE);

        PreOrderAdapter preOrderAdapter = new PreOrderAdapter(OrderActivity.woodOrderModelList, (v, position) ->
                itemClick(position));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(preOrderAdapter);
        recyclerView.scheduleLayoutAnimation();
    }

    private void fillOrderList() {
        tvNoData.setVisibility(OrderActivity.woodOrderModelList.size() == 0 ? View.VISIBLE : View.GONE);
        tvInsertRow.setVisibility(OrderActivity.woodOrderModelList.size() == 0 ? View.VISIBLE : View.GONE);

        OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(OrderActivity.woodOrderModelList, (v, position) ->
                itemClick(position));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(orderItemsAdapter);
        recyclerView.scheduleLayoutAnimation();
    }

    private void itemClick(int position) {
        SendPreOrderDialog sendPreOrderDialog = new SendPreOrderDialog(getActivity(), res -> {
            if (res == 1)
                editOrder(position);
            else if (res == 0) {
                OrderActivity.woodOrderModelList.remove(position);
                callOrderOrPreOrder();
            }
        }, OrderActivity.woodOrderModelList.get(position));
        DialogUtil.showDialog(getActivity(), sendPreOrderDialog, false, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        APP.currentActivity = getActivity();
        callOrderOrPreOrder();
    }

    private void editOrder(int position) {
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
                .desc("")
                .items(convertToListOderModel())
                .build();
    }

    private List<WoodOrderModel> convertToListOderModel() {
        WoodModel wModel = new WoodModel();
        List<WoodOrderModel> myList = new ArrayList<>();
        WoodOrderModel woodOrderModel = new WoodOrderModel();
        for (int i = 0; i < OrderActivity.woodOrderModelList.size(); i++) {

            wModel = OrderActivity.woodOrderModelList.get(i);
            woodOrderModel.setWoodType(wModel.getWoodType().getIndex() + 1);

            woodOrderModel.setColor(wModel.getColor());
            woodOrderModel.setPvcColor(wModel.getPvcColor());

            woodOrderModel.setPvcThickness(wModel.getPvcThickness().getIndex());

            woodOrderModel.setPvcLenghtNo(wModel.getPvcLengthNo().getIndex());
            woodOrderModel.setPvcWidthNo(wModel.getPvcWidthNo().getIndex());

            woodOrderModel.setWoodSheetLength(wModel.getWoodSheetLength());
            woodOrderModel.setWoodSheetWidth(wModel.getWoodSheetWidth());

            woodOrderModel.setSheetCount(wModel.getSheetCount());

            woodOrderModel.setPersianCutLenghtNo(wModel.getPersianCutLenghtNo().getIndex());
            woodOrderModel.setPersianCutWidthNo(wModel.getPersianCutWidthNo().getIndex());

            woodOrderModel.setGrooveLenghtNo(wModel.getGrooveLenghtNo().getIndex());
            woodOrderModel.setGrooveWidthNo(wModel.getGrooveWidthNo().getIndex());

            woodOrderModel.setPatterned(wModel.getPatterned());
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
                    APP.currentActivity = getActivity();
                    APP.customToast(getString(R.string.text_successful));
                    OrderActivity.woodOrderModelList = new ArrayList<>();
                    APP.currentActivity.finish();
                } else {
                    APP.customToast(response.getMessage());
                }
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error);
            }
        });
    }

    private UpdateOrderBody getUpdateOrderBody(int orderId) {
        return UpdateOrderBody.builder()
                .customerId(userId)
                .orderId(orderId)
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
                    GeneralPreferences.getInstance(APP.currentActivity).putToken(data.getAccessToken());
                    APP.customToast(getString(R.string.text_successful));
                    OrderActivity.woodOrderModelList = new ArrayList<>();
                    APP.currentActivity.finish();
                } else {
                    APP.customToast(response.getMessage());
                }
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error);
            }
        });
    }


//    private void discardOrder(int id) {
//
//        DiscardOrderBody discardOrderBody = DiscardOrderBody.builder()
//                .customerId(userId)
//                .orderId(34)//نتیفیکیشن این آی دی بهم میده
//                .discardOptionId(id)
//                .build();
//
//        DiscardOrderController discardOrderController = new DiscardOrderController();
//        discardOrderController.start(userId, token, discardOrderBody, new IResponseListener<SarvApiResponseNoList>() {
//            @Override
//            public void onSuccess(SarvApiResponseNoList response) {
//                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
//                    APP.customToast("سفارش شما لغو گردید");
////                    getActivity().finish();
//                } else {
//                    APP.customToast(response.getMessage());
//
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                APP.customToast(error);
//            }
//        });
//    }

}
