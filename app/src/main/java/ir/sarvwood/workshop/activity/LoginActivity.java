package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;
import ir.sarvwood.workshop.utils.PublicFunctions;
import ir.sarvwood.workshop.webservice.apibodies.GetCustomerInfoBody;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoController;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoReturnValue;

public class LoginActivity extends AppCompatActivity implements IInternetController {


    @BindView(R.id.btn_register)
    protected AppCompatButton btnRegister;
    @BindView(R.id.btn_login)
    protected AppCompatButton btnLogin;
    @BindView(R.id.et_username)
    protected AppCompatEditText etUserName;
    @BindView(R.id.et_password)
    protected AppCompatEditText etPassword;
    @BindView(R.id.chk_remember)
    protected AppCompatCheckBox chkRemember;

    private GetCustomerInfoReturnValue getCustomerInfoReturnValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        buttonClickConfig();

    }

    @Override
    protected void onResume() {
        super.onResume();
        APP.currentActivity = LoginActivity.this;
    }

    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(this).isOnline();
    }

    private void buttonClickConfig() {
        btnLogin.setOnClickListener(view -> logIn());
        btnRegister.setOnClickListener(view -> openRegisterFragment());

    }

    private void openRegisterFragment() {
        Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentFlag", 1);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void logIn() {
        if ("".equals(Objects.requireNonNull(etUserName.getText()).toString())) {
            APP.customToast(getString(R.string.text_enter_user_name));
            return;
        }
        if ("".equals(Objects.requireNonNull(etPassword.getText()).toString())) {
            APP.customToast(getString(R.string.text_enter_password));
            return;
        }

        getCustomerInfo();
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
                getCustomerInfo();
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


    private GetCustomerInfoBody getCustomerInfoBody() {

        GetCustomerInfoBody getCustomerInfoBody = GetCustomerInfoBody.builder()
                .username(Objects.requireNonNull(etUserName.getText()).toString())
                .pass(Objects.requireNonNull(etPassword.getText()).toString())
                .applicationVersion(String.valueOf(new PublicFunctions().getAppVersionCode(LoginActivity.this)))
                .deviceModel(Build.MODEL)
                .deviceName(Build.MANUFACTURER)
                .sdkVersion(String.valueOf(Build.VERSION.SDK_INT))
                .build();
        return getCustomerInfoBody;
    }

    private void getCustomerInfo() {
        if (!isOnline()) {
            openInternetCheckingDialog();
        }

        GetCustomerInfoController getCustomerInfoController = new GetCustomerInfoController();
        getCustomerInfoController.start(getCustomerInfoBody(), new IResponseListener<SarvApiResponse<GetCustomerInfoReturnValue>>() {

            @Override
            public void onSuccess(SarvApiResponse response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    getCustomerInfoReturnValue =(GetCustomerInfoReturnValue) response.getData().get(0);

                    saveInSharePreference();
                    if (chkRemember.isChecked()) {
                        GeneralPreferences.getInstance(LoginActivity.this).putString(BuildConfig.userName, Objects.requireNonNull(etUserName.getText()).toString());
                        GeneralPreferences.getInstance(LoginActivity.this).putString(BuildConfig.userPass, Objects.requireNonNull(etPassword.getText()).toString());
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    APP.customToast(response.getMessage());
                }
                LoginActivity.this.finish();
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error);
                LoginActivity.this.finish();
            }
        });
    }


    public void saveInSharePreference()
    {
        GeneralPreferences.getInstance(LoginActivity.this).putCustomerId(getCustomerInfoReturnValue.getCustomerId());
        GeneralPreferences.getInstance(LoginActivity.this).putToken(getCustomerInfoReturnValue.getAccessToken());
    }
}
