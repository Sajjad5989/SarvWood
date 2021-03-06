package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.activity.ContainerActivity;
import ir.sarvwood.workshop.activity.OrderActivity;
import ir.sarvwood.workshop.activity.OrderHeaderActivity;
import ir.sarvwood.workshop.adapter.MyOrderListAdapter;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.model.order.ConvertToHeaderWoodModel;
import ir.sarvwood.workshop.model.order.ConvertToWoodModelList;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;
import ir.sarvwood.workshop.webservice.myorders.GetMyOrderBody;
import ir.sarvwood.workshop.webservice.myorders.GetMyOrdersController;
import ir.sarvwood.workshop.webservice.myorders.MyOrderReturnValue;
import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsBody;
import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsController;
import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsItemReturnValue;
import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;

public class MyOrderListFragment extends Fragment implements IInternetController {


    @BindView(R.id.recycler_my_order)
    protected AnimatedRecyclerView animatedRecyclerView;
    @BindView(R.id.image_not_found)
    protected AppCompatImageView imageNotFound;
    private List<MyOrderReturnValue> myOrderReturnValueList;
    private int userId;
    private String token;
    private List<GetOrderDetailsItemReturnValue> returnValueList;
    private int orderState = 0;
    private GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue> myHeaderList;

    public static MyOrderListFragment newInstance() {
        return new MyOrderListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fargment_myorder_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyOrders();
    }

    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(getActivity()).isOnline();
    }

    private void openInternetCheckingDialog() {
        ConnectionInternetDialog dialog = new ConnectionInternetDialog(getActivity(), new InternetConnectionListener() {
            @Override
            public void onInternet() {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }

            @Override
            public void onFinish() {
                APP.killApp(getActivity());
            }

            @Override
            public void OnRetry() {
                getMyOrderBody();
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg_no_padding));
        dialog.show();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width = (int) ((width) * 0.8);
        dialog.getWindow().setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    private GetMyOrderBody getMyOrderBody() {

        userId = GeneralPreferences.getInstance(getActivity()).getCustomerId();
        token = GeneralPreferences.getInstance(getActivity()).getToken();

        GetMyOrderBody getMyOrderBody = new GetMyOrderBody();
        getMyOrderBody.setCustomerId(userId);
        return getMyOrderBody;
    }

    private void getMyOrders() {

        if (!isOnline()) {
            openInternetCheckingDialog();
        }

        GetMyOrderBody getMyOrderBody = getMyOrderBody();
        GetMyOrdersController getMyOrdersController = new GetMyOrdersController();
        getMyOrdersController.start(userId, token, getMyOrderBody, new IResponseListener<SarvApiResponse<MyOrderReturnValue>>() {
            @Override
            public void onSuccess(SarvApiResponse response) {
                if (response != null) {
                    if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                        myOrderReturnValueList = response.getData();
                    } else {
                        APP.customToast(response.getMessage(), getActivity());
                        myOrderReturnValueList = null;
                    }
                    showMyOrders();
                } else {
                    myOrderReturnValueList = null;
                    showMyOrders();
                }
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error, getActivity());
                myOrderReturnValueList = null;
                showMyOrders();
            }
        });
    }

    private void showMyOrders() {

        if (myOrderReturnValueList == null)
            imageNotFound.setVisibility(View.VISIBLE);
        else {
            imageNotFound.setVisibility(View.GONE);
            MyOrderListAdapter myOrderListAdapter = new
                    MyOrderListAdapter(myOrderReturnValueList, (v, position) ->
                    getOrderDetail(myOrderReturnValueList.get(position).getOrderId()));

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            animatedRecyclerView.setLayoutManager(gridLayoutManager);
            animatedRecyclerView.setAdapter(myOrderListAdapter);
            animatedRecyclerView.scheduleLayoutAnimation();
        }
    }

    private void getOrderDetail(int orderId) {
        GetOrderDetailsBody getOrderDetailsBody = GetOrderDetailsBody.builder().orderId(orderId).build();
        GetOrderDetailsController getOrderDetailsController = new GetOrderDetailsController();
        getOrderDetailsController.start(userId, token, getOrderDetailsBody,
                new IResponseListener<SarvApiResponse<GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue>>>() {
                    @Override
                    public void onSuccess(SarvApiResponse<GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue>> response) {
                        if (response.getCode() == 0 && "success".equals(response.getStatus())) {

                            myHeaderList = response.getData().get(0);
                            OrderHeaderActivity.headerWoodModel
                                    = new ConvertToHeaderWoodModel(myHeaderList, getActivity()).
                                    getWoodHeaderOrderModelList();

                            returnValueList = response.getData().get(0).getItems();
                            orderState = response.getData().get(0).getState();
                            OrderActivity.woodOrderModelList = new ArrayList<>();
                            OrderActivity.woodOrderModelList =
                                    new ConvertToWoodModelList(returnValueList, getActivity()).getWoodOrderModelList();


                            openOrderItems();
                        } else {
                            APP.customToast(response.getMessage(), getActivity());
                            returnValueList = null;
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error, getActivity());
                        returnValueList = null;
                    }
                });
    }

    private void openOrderItems() {
        Intent intent = new Intent(getActivity(), ContainerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentFlag", 6);
        bundle.putInt("orderStatus", orderState);
        intent.putExtras(bundle);
        startActivity(intent);

    }


//    private void ConvertToHeaderWoodModel()
//    {
//        OrderHeaderActivity.headerWoodModel.setBrand(myHeaderList.getWoodBrand());
//        OrderHeaderActivity.headerWoodModel.setColor(myHeaderList.getWoodColor());
//        OrderHeaderActivity.headerWoodModel.setCode(myHeaderList.getWoodCode());
//        OrderHeaderActivity.headerWoodModel.setPvcColor(myHeaderList.getPvcColor());
//        OrderHeaderActivity.headerWoodModel.setPatterned(myHeaderList.getPatterned());
////        OrderHeaderActivity.headerWoodModel.set
//
//
//    }
}
