package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.dialog.discardoption.DiscardOptionDialog;
import ir.sarvwood.workshop.dialog.discardoption.RecyclerViewClickListenerDiscard;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.interfaces.IDefault;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.interfaces.IRtl;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;
import ir.sarvwood.workshop.webservice.discardorder.DiscardOrderBody;
import ir.sarvwood.workshop.webservice.discardorder.DiscardOrderController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.setsupplier.SetSheetSupplierController;
import ir.sarvwood.workshop.webservice.setsupplier.SheetSupplierBody;
import ir.solmazzm.lib.engine.util.DialogUtil;

public class SupplierActivity extends AppCompatActivity implements IInternetController, IRtl, IDefault {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    private int orderId;
    private int userId;
    private String token;

     private static final int SET_SUPPLIER_WORKSHOP = 1;
     private static final int SET_SUPPLIER_OWEN = 2;
    @OnClick(R.id.btn_workshop_sarv)
    void useWorkShopWood() {

        if (!isOnline()) {
            openInternetCheckingDialog(SET_SUPPLIER_WORKSHOP);
        }

        setSupplier(SET_SUPPLIER_WORKSHOP);
    }

    @OnClick(R.id.btn_own_sarv)
    void useCustomerOwnWood() {
        if (!isOnline()) {
            openInternetCheckingDialog(SET_SUPPLIER_OWEN);
        }

        setSupplier(SET_SUPPLIER_OWEN);
    }

    @OnClick(R.id.btn_cancel_sarv)
    void cancelOrder() {

        DiscardOptionDialog discardOptionDialog = new DiscardOptionDialog(
                Objects.requireNonNull(SupplierActivity.this), new RecyclerViewClickListenerDiscard() {
            @Override
            public void onItemClick(int id) {
                if (id > 0)
                    discardOrder(id);
            }
        }
        );
        DialogUtil.showDialog(SupplierActivity.this, discardOptionDialog, false, true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_supplier);
        ButterKnife.bind(this);

        OnActivityDefaultSetting();
        prepareToolbar();
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getIds();
        getOrderId();
    }


    private void prepareToolbar() {
        toolbar.setTitle(R.string.text_order_status);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void OnActivityDefaultSetting() {
        OnPageRight();
    }

    @Override
    public void OnPageRight() {
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        APP.setPersianUi();
    }

    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(this).isOnline();
    }

    private void openInternetCheckingDialog(int methodCall) {
        ConnectionInternetDialog dialog = new ConnectionInternetDialog(this, new InternetConnectionListener() {
            @Override
            public void onInternet() {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }

            @Override
            public void onFinish() {
                APP.killApp(SupplierActivity.this);
            }

            @Override
            public void OnRetry() {
                  setSupplier(methodCall);

            }
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg_no_padding));
        dialog.show();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width = (int) ((width) * 0.8);
        dialog.getWindow().setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    private void setSupplier(int supplySheet) {



        SheetSupplierBody supplierBody = SheetSupplierBody.
                builder()
                .customerId(userId)
                .orderId(orderId)
                .sheetSupplier(supplySheet)
                .build();

        SetSheetSupplierController setSheetSupplierController = new SetSheetSupplierController();
        setSheetSupplierController.start(userId, token, supplierBody,
                new IResponseListener<SarvApiResponseNoList>() {
                    @Override
                    public void onSuccess(SarvApiResponseNoList response) {
                        if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                            APP.customToast(getString(R.string.text_successful), SupplierActivity.this);
                            SupplierActivity.this.finish();
                        } else {
                            APP.customToast(response.getMessage(), SupplierActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error, SupplierActivity.this);
                    }
                });
    }

    private void getIds() {
        userId = GeneralPreferences.getInstance(SupplierActivity.this).getCustomerId();
        token = GeneralPreferences.getInstance(SupplierActivity.this).getToken();
    }

    private void getOrderId() {

        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        if (bundle != null) {
            orderId = bundle.getInt("orderId");

        } else this.finish();
    }

    private void discardOrder(int id) {
        DiscardOrderBody discardOrderBody = DiscardOrderBody.builder()
                .customerId(userId)
                .orderId(orderId)//نتیفیکیشن این آی دی بهم میده
                .discardOptionId(id)
                .build();

        DiscardOrderController discardOrderController = new DiscardOrderController();
        discardOrderController.start(userId, token, discardOrderBody, new IResponseListener<SarvApiResponseNoList>() {
            @Override
            public void onSuccess(SarvApiResponseNoList response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    APP.customToast("سفارش شما لغو گردید", SupplierActivity.this);
                    startActivity(new Intent(SupplierActivity.this, MainActivity.class));
                } else {
                    APP.customToast(response.getMessage(), SupplierActivity.this);
                }
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error, SupplierActivity.this);
            }
        });
    }

}
