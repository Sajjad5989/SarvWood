package ir.sarvwood.workshop.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
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
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;
import ir.sarvwood.workshop.utils.PublicFunctions;
import ir.sarvwood.workshop.webservice.authenticationofcnfrmcode.AuthenticationOfCnfrmCodeBody;
import ir.sarvwood.workshop.webservice.authenticationofcnfrmcode.AuthenticationOfCnfrmCodeController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.sendsmsofcnfrmcode.SendSmsOfCnfrmCodeBody;
import ir.sarvwood.workshop.webservice.sendsmsofcnfrmcode.SendSmsOfCnfrmCodeController;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class ActivationActivity extends AppCompatActivity implements IRtl, IDefault, IInternetController {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.tv_seconds)
    protected AppCompatTextView tvSecond;
    @BindView(R.id.tv_sms_again)
    protected AppCompatTextView tvSmsAgain;
    @BindView(R.id.et_activation_code)
    protected AppCompatEditText etActivationCode;
    private ProgressDialog progress;
    private int seconds = 20;
    private boolean startRun = true;

    @OnClick(R.id.btn_enter)
    void checkActivationCode() {
        if ("".equals(Objects.requireNonNull(etActivationCode.getText()).toString())) {
            APP.customToast("کد وارد نشده است", ActivationActivity.this);
            return;
        }
        if (etActivationCode.getText().length() < 6) {
            APP.customToast("کد وارد نشده صحیح نمی باشد", ActivationActivity.this);
            return;
        }

        showProgress();
        authenticateConfirmSms();
    }

    private void showProgress() {
        progress = new ProgressDialog(ActivationActivity.this);
        String message = getString(R.string.text_please_wait);
        SpannableString spannableString = new SpannableString(message);

        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(ActivationActivity.this.getAssets(),
                "fonts/iran_sans.ttf"));
        spannableString.setSpan(typefaceSpan, 0, message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        progress.setMessage(spannableString);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activation);
        ButterKnife.bind(this);

        OnActivityDefaultSetting();
        prepareToolbar();
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        tvSmsAgain.setOnClickListener(view -> sendSmsOfCnfrmCode());
        CountDownTimer();
    }


    private void sendSmsOfCnfrmCode() {
        if (!isOnline()) {
            openInternetCheckingDialog();
        }

        String mobile = GeneralPreferences.getInstance(ActivationActivity.this).getString("mobile");
        if ("".equals(mobile)) {
            APP.customToast("شماره موبایل یافت نشد", ActivationActivity.this);
            return;
        }

        CountDownTimer();
        startRun = true;

        tvSmsAgain.setVisibility(View.GONE);

        SendSmsOfCnfrmCodeBody sendSmsOfCnfrmCodeBody = SendSmsOfCnfrmCodeBody.builder()
                .mobileNo(mobile)
                .build();

        SendSmsOfCnfrmCodeController sendSmsOfCnfrmCodeController = new SendSmsOfCnfrmCodeController();
        sendSmsOfCnfrmCodeController.start(sendSmsOfCnfrmCodeBody, new IResponseListener<SarvApiResponseNoList>() {
            @Override
            public void onSuccess(SarvApiResponseNoList response) {
                if (response.getCode() != 0 || !"success".equals(response.getStatus())) {
                    APP.customToast(response.getMessage(), ActivationActivity.this);
                }
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error, ActivationActivity.this);
            }
        });
    }


    private void prepareToolbar() {
        toolbar.setTitle(R.string.text_activation_code);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                APP.killApp(ActivationActivity.this);
            }

            @Override
            public void OnRetry() {
                authenticateConfirmSms();
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
                        tvSmsAgain.setVisibility(View.VISIBLE);
                        startRun = false;
                        this.onFinish();
                    }
                }
                handler.postDelayed(this, 1000);
            }

            public void onFinish() {
                seconds = 120;
                tvSecond.setText("120");

            }
        });
    }

    private void authenticateConfirmSms() {
        if (!isOnline()) {
            openInternetCheckingDialog();
        }

        String mobileNo = GeneralPreferences.getInstance(ActivationActivity.this).getString("mobile");
        AuthenticationOfCnfrmCodeBody authenticationOfCnfrmCodeBody = AuthenticationOfCnfrmCodeBody.builder()
                .cofirmationCode(etActivationCode.getText().toString())
                .mobileNo(mobileNo)
                .applicationVersion(new PublicFunctions().getAppVersionCode(ActivationActivity.this))
                .deviceModel(Build.MODEL)
                .deviceName(Build.MANUFACTURER)
                .sdkVersion(String.valueOf(Build.VERSION.SDK_INT))
                .build();

        AuthenticationOfCnfrmCodeController authenticationOfCnfrmCodeController = new AuthenticationOfCnfrmCodeController();
        authenticationOfCnfrmCodeController.start(authenticationOfCnfrmCodeBody, new IResponseListener<SarvApiResponseNoList>() {
            @Override
            public void onSuccess(SarvApiResponseNoList response) {

                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    APP.customToast(getString(R.string.text_successful_activation), ActivationActivity.this);

                    startActivity(new Intent(ActivationActivity.this, LoginActivity.class));
                    progress.hide();
                    ActivationActivity.this.finish();
                }
                APP.customToast(response.getMessage(), ActivationActivity.this);
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error, ActivationActivity.this);
            }
        });
    }

}
