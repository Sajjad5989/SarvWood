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
import butterknife.OnClick;
import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.adapter.DiscardOptionAdapter;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.webservice.discardoption.DiscardOptionController;
import ir.sarvwood.workshop.webservice.discardoption.DiscardOptionReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;

public class DiscardOptionDialog extends Dialog {

    @BindView(R.id.recycle_option)
    protected AnimatedRecyclerView recyclerView;
    private List<DiscardOptionReturnValue> valueList;
    private int userId;
    private String token;
    private RecyclerViewClickListenerDiscard listenerDiscard;

    public DiscardOptionDialog(@NonNull Context context, RecyclerViewClickListenerDiscard listenerDiscard) {
        super(context);
        this.listenerDiscard = listenerDiscard;
    }

    @OnClick(R.id.btn_return)
    void returnToOwner() {
        listenerDiscard.onItemClick(-1);
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_discard_option);
        ButterKnife.bind(this);
        getIds();
        getDiscardOptionList();

    }

    private void getDiscardOption() {

        DiscardOptionAdapter discardOptionAdapter = new DiscardOptionAdapter(valueList, id -> {
            listenerDiscard.onItemClick(valueList.get(id).getId());
            dismiss();
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(discardOptionAdapter);
        recyclerView.scheduleLayoutAnimation();
    }

    private void getIds() {
        userId = GeneralPreferences.getInstance(getContext()).getInt(BuildConfig.userId);
        token = GeneralPreferences.getInstance(getContext()).getString(BuildConfig.accessToken);
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
                    APP.customToast(response.getMessage(),getOwnerActivity());
                }
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error,getOwnerActivity());
            }
        });
    }


}
