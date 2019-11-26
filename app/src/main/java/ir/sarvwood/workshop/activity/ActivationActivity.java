package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Display;
import android.view.View;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.interfaces.IDefault;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.interfaces.IRtl;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;
import ir.sarvwood.workshop.webservice.authenticationofcnfrmcode.AuthenticationOfCnfrmCodeBody;
import ir.sarvwood.workshop.webservice.authenticationofcnfrmcode.AuthenticationOfCnfrmCodeController;
import ir.sarvwood.workshop.webservice.sendsmsofcnfrmcode.SendSmsOfCnfrmCodeController;

public class ActivationActivity extends AppCompatActivity implements IRtl, IDefault, IInternetController {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.tv_seconds)
    protected AppCompatTextView tvSecond;
    @BindView(R.id.et_activation_code)
    protected AppCompatEditText etActivationCode;

    @OnClick(R.id.btn_enter)
    void checkActivationCode()
    {


    }


    private int seconds = 59;
    private boolean startRun = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activation);
        ButterKnife.bind(this);

        OnActivityDefaultSetting();
        prepareToolbar();
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        CountDownTimer();
    }

    private void prepareToolbar() {
        toolbar.setTitle(R.string.text_activation_code);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        APP.currentActivity = ActivationActivity.this;
        APP.setPersianUi();
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
    public boolean isOnline() {
        return OnlineCheck.getInstance(this).isOnline();
    }

    private void openInternetCheckingDialog() {
        ConnectionInternetDialog dialog = new ConnectionInternetDialog(this, new InternetConnectionListener() {
            @Override
            public void onInternet() {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }

            @Override
            public void onFinish() {
                APP.killApp();
            }

            @Override
            public void OnRetry() {
                //تابع مربوطه ر صدا بزنم
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

    private void CountDownTimer() {

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String time = String.format("%02d", seconds);
                tvSecond.setText(time);

                if (startRun) {
                    seconds--;
                    if (seconds <= 0) {
                          finish();
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void authenticateConfirmSms()
    {
        AuthenticationOfCnfrmCodeBody authenticationOfCnfrmCodeBody = AuthenticationOfCnfrmCodeBody.builder()
                .cofirmationCode("")
                .applicationVersion("")
                .deviceModel("")
                .deviceName("")
                .mobileNo("")
                .sdkVersion("")
                .build();

        AuthenticationOfCnfrmCodeController authenticationOfCnfrmCodeController = new AuthenticationOfCnfrmCodeController();
        authenticationOfCnfrmCodeController.start(authenticationOfCnfrmCodeBody, new IResponseListener() {
            @Override
            public void onSuccess(Object response) {

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

}
