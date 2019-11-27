package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoBody;
import ir.sarvwood.workshop.webservice.baseinfo.BaseInfoController;
import ir.sarvwood.workshop.webservice.baseinfo.BaseInfoReturnValue;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoController;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;

public class SplashActivity extends AppCompatActivity implements IInternetController {

    private GetCustomerInfoReturnValue getCustomerInfoReturnValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPersian();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        doActionAfterSplash();
    }

    private void setPersian() {
        String languageToLoad = "fa";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        new Geocoder(SplashActivity.this, new Locale("fa"));
    }

    private void doActionAfterSplash() {

        final Handler handler = new Handler();
        handler.postDelayed(this::letsGo, 3000);
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


    private void letsGo() {

        boolean showedSlider = GeneralPreferences.getInstance(this).getBoolean(getString(R.string.text_preference_slider_is_show));

        if (showedSlider) {
            String userName = GeneralPreferences.getInstance(SplashActivity.this).getString(BuildConfig.userName);
            String userPass = GeneralPreferences.getInstance(SplashActivity.this).getString(BuildConfig.userPass);

            if (userName == null || userPass == null) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            } else {
                getCustomerInfo(userName, userPass);
            }
        } else {
            startActivity(new Intent(SplashActivity.this, IntroSliderActivity.class));
        }
        SplashActivity.this.finish();
    }


    private GetCustomerInfoBody getCustomerInfoBody(String userName, String userPass) {

        GetCustomerInfoBody getCustomerInfoBody = GetCustomerInfoBody.builder()
                .username(userName)
                .pass(userPass)
                .applicationVersion(String.valueOf(new PublicFunctions().getAppVersionCode(SplashActivity.this)))
                .deviceModel(Build.MODEL)
                .deviceName(Build.MANUFACTURER)
                .sdkVersion(String.valueOf(Build.VERSION.SDK_INT))
                .build();
        return getCustomerInfoBody;
    }

    private void getCustomerInfo(String userName, String userPass) {
        if (!isOnline()) {
            openInternetCheckingDialog();
        }

        GetCustomerInfoController getCustomerInfoController = new GetCustomerInfoController();
        getCustomerInfoController.start(getCustomerInfoBody(userName, userPass), new IResponseListener<SarvApiResponse<GetCustomerInfoReturnValue>>() {

            @Override
            public void onSuccess(SarvApiResponse response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    getCustomerInfoReturnValue = (GetCustomerInfoReturnValue) response.getData().get(0);
                    saveInSharePreference();
                    getBaseInfo();
                } else {
                    APP.customToast(response.getMessage());
                }
                SplashActivity.this.finish();
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error);
                SplashActivity.this.finish();
            }
        });
    }

    private void getBaseInfo() {
        BaseInfoController baseInfoController = new BaseInfoController();
        baseInfoController.start(getCustomerInfoReturnValue.getCustomerId(), getCustomerInfoReturnValue.getAccessToken(),
                new IResponseListener<SarvApiResponse<BaseInfoReturnValue>>() {
                    @Override
                    public void onSuccess(SarvApiResponse<BaseInfoReturnValue> response) {
                        if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                            GeneralPreferences.getInstance(SplashActivity.this).putBaseInfo(response.getData().get(0));
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error);
                        APP.killApp();
                    }
                });
    }

    public void saveInSharePreference() {
        GeneralPreferences.getInstance(SplashActivity.this).putListCustomerInfoResponse(getCustomerInfoReturnValue);
        GeneralPreferences.getInstance(SplashActivity.this).putCustomerId(getCustomerInfoReturnValue.getCustomerId());
        GeneralPreferences.getInstance(SplashActivity.this).putToken(getCustomerInfoReturnValue.getAccessToken());
    }

}
