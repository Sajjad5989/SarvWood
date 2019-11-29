package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.activity.OrderActivity;
import ir.sarvwood.workshop.adapter.OrderItemsAdapter;
import ir.sarvwood.workshop.adapter.PreOrderAdapter;
import ir.sarvwood.workshop.dialog.savedorder.SendOrderDialog;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.webservice.insertorder.InsertOrderBody;
import ir.sarvwood.workshop.webservice.insertorder.InsertOrderBodyReturnValue;
import ir.sarvwood.workshop.webservice.insertorder.InsertOrderController;
import ir.sarvwood.workshop.webservice.myorders.GetOrderDetailsItemReturnValueList;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.solmazzm.lib.engine.util.DialogUtil;

public class PreOrderFragment extends Fragment {


    private static final int ORDER_ITEMS = 1;
    private static final int PRE_ORDER_ITEMS = 2;

    private static GetOrderDetailsItemReturnValueList currentOrderDetailItems;
    private static int currentType;
    @BindView(R.id.recycler_pre_order_list)
    protected AnimatedRecyclerView recyclerView;
    private int userId;
    private String token;

    public static PreOrderFragment newInstance(GetOrderDetailsItemReturnValueList orderDetailItems, int type) {

        currentType = type;
        currentOrderDetailItems = orderDetailItems;

        return new PreOrderFragment();
    }


    @OnClick(R.id.btn_send_order)
    void sendOrderToApi() {
        InsertOrderController insertOrderController = new InsertOrderController();

        InsertOrderBody insertOrderBody = getBodyValue();

        insertOrderController.start(userId, token, insertOrderBody, new IResponseListener<SarvApiResponse<InsertOrderBodyReturnValue>>() {
            @Override
            public void onSuccess(SarvApiResponse response) {
                if ( response.getCode() == 0 && "success".equals(response.getStatus())) {
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


    @OnClick(R.id.btn_new_order)
    void openOrderList() {
        startActivity(new Intent(APP.currentActivity, OrderActivity.class));
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
        PreOrderAdapter preOrderAdapter = new PreOrderAdapter(OrderActivity.woodOrderModelList, (v, position) -> itemClick(position));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(preOrderAdapter);
        recyclerView.scheduleLayoutAnimation();
    }

    private void fillOrderList() {
        OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(currentOrderDetailItems, (v, position) ->
                itemClick(position));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(orderItemsAdapter);
        recyclerView.scheduleLayoutAnimation();
    }


    private void itemClick(int position) {

        SendOrderDialog sendOrderDialog = new SendOrderDialog(APP.currentActivity, res -> {
            if (res)
                editOrder();
        }, OrderActivity.woodOrderModelList.get(position));
        DialogUtil.showDialog(getActivity(), sendOrderDialog, false, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        APP.currentActivity = getActivity();
        callOrderOrPreOrder();
    }

    private void editOrder() {
    }

    private InsertOrderBody getBodyValue() {

        userId = GeneralPreferences.getInstance(APP.currentActivity).getCustomerId();
        token = GeneralPreferences.getInstance(APP.currentActivity).getToken();

        InsertOrderBody insertOrderBody = InsertOrderBody.builder()
                .customerId(userId)
                .desc("")
                .items(OrderActivity.woodOrderModelList)
                .build();
        return insertOrderBody;
    }

}
