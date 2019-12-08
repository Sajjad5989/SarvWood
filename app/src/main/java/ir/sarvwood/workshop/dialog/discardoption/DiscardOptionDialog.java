package ir.sarvwood.workshop.dialog.discardoption;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.adapter.DiscardOptionAdapter;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.webservice.discardoption.DiscardOptionController;
import ir.sarvwood.workshop.webservice.discardoption.DiscardOptionReturnValue;
import ir.sarvwood.workshop.webservice.discardorder.DiscardOrderBody;
import ir.sarvwood.workshop.webservice.discardorder.DiscardOrderController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;

public class DiscardOptionDialog extends Dialog {

    @BindView(R.id.recycle_option)
    protected AnimatedRecyclerView recyclerView;
    private List<DiscardOptionReturnValue> valueList;
    private int userId;
    private String token;
    private int orderId = 33;
    private RecyclerViewClickListenerDiscard listenerDiscard;

    public DiscardOptionDialog(@NonNull Context context, RecyclerViewClickListenerDiscard listenerDiscard) {
        super(context);
        this.listenerDiscard = listenerDiscard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_discard_option);
        ButterKnife.bind(this);
        APP.currentActivity = this.getOwnerActivity();
        getIds();
        getDiscardOptionList();

    }

    private void getDiscardOption() {

        DiscardOptionAdapter discardOptionAdapter = new DiscardOptionAdapter(valueList, new RecyclerViewClickListenerDiscard() {
            @Override
            public void onItemClick(int id) {
                listenerDiscard.onItemClick(id);
                dismiss();
//                DiscardOptionDialog.this.discardOrder(valueList.get(id).getId());
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(discardOptionAdapter);
        recyclerView.scheduleLayoutAnimation();
    }

    private void getIds() {
        userId = GeneralPreferences.getInstance(APP.currentActivity).getInt(BuildConfig.userId);
        token = GeneralPreferences.getInstance(APP.currentActivity).getString(BuildConfig.accessToken);
    }

    private void getDiscardOptionList() {
        DiscardOptionController discardOptionController = new DiscardOptionController();
        discardOptionController.start(userId, token, new IResponseListener<SarvApiResponse<DiscardOptionReturnValue>>() {
            @Override
            public void onSuccess(SarvApiResponse<DiscardOptionReturnValue> response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    valueList = response.getData();
                    getDiscardOption();
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
//        DiscardOrderBody discardOrderBody = DiscardOrderBody.builder()
//                .customerId(userId)
//                .orderId(orderId)//نتیفیکیشن این آی دی بهم میده
//                .discardOptionId(id)
//                .build();
//
//        DiscardOrderController discardOrderController = new DiscardOrderController();
//        discardOrderController.start(userId, token, discardOrderBody, new IResponseListener<SarvApiResponseNoList>() {
//            @Override
//            public void onSuccess(SarvApiResponseNoList response) {
//                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
//                    APP.customToast("سفارش شما لغو گردید");
//                    dismiss();
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
