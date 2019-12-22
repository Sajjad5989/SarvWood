package ir.sarvwood.workshop.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.net.Uri;
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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.dialog.update.UpdateDialog;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;
import ir.sarvwood.workshop.utils.PublicFunctions;
import ir.sarvwood.workshop.webservice.baseinfo.BaseInfoController;
import ir.sarvwood.workshop.webservice.baseinfo.BaseInfoReturnValue;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoBody;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoController;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.savedeviceinfo.SaveDeviceInfoBody;
import ir.sarvwood.workshop.webservice.savedeviceinfo.SaveDeviceInfoController;
import ir.solmazzm.lib.engine.util.DialogUtil;

public class SplashActivity extends AppCompatActivity implements IInternetController {

    private GetCustomerInfoReturnValue getCustomerInfoReturnValue;
    private BaseInfoReturnValue baseInfoReturnValue;
    private PublicFunctions publicFunctions = new PublicFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPersian();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        AppCompatTextView tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(String.valueOf(new PublicFunctions().getAppVersionCode(SplashActivity.this)));
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
                APP.killApp(SplashActivity.this);
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
                .applicationVersion(publicFunctions.getAppVersionCode(SplashActivity.this))
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
                    APP.customToast(response.getMessage(),SplashActivity.this);
                    GeneralPreferences.getInstance(SplashActivity.this).deleteAllInfo();
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
                }

            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error,SplashActivity.this);
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

                            baseInfoReturnValue = response.getData().get(0);
                            String appVersion = String.valueOf(publicFunctions.getAppVersionCode(SplashActivity.this));
                            if (!appVersion.equals(baseInfoReturnValue.getAndroidGlobalAppVer())) {
                                if (baseInfoReturnValue.getAndroidGlobalAppForceUpdate() == 1) {
                                    updateWarning(baseInfoReturnValue.getAndroidAppDlLink(), baseInfoReturnValue.getAndroidGlobalAppForceUpdate());
                                }
                            }

                            GeneralPreferences.getInstance(SplashActivity.this).putBaseInfo(baseInfoReturnValue);
                            saveDeviceInfo();
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error,SplashActivity.this);
                        APP.killApp(SplashActivity.this);
                    }
                });
    }

    public void saveInSharePreference() {
        GeneralPreferences.getInstance(SplashActivity.this).putListCustomerInfoResponse(getCustomerInfoReturnValue);
        GeneralPreferences.getInstance(SplashActivity.this).putCustomerId(getCustomerInfoReturnValue.getCustomerId());
        GeneralPreferences.getInstance(SplashActivity.this).putToken(getCustomerInfoReturnValue.getAccessToken());
    }

    private void startDownload(String downloadLink) {
        DownloadManager mManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request mRqRequest = new DownloadManager.Request(
                Uri.parse(downloadLink));
        Objects.requireNonNull(mManager).enqueue(mRqRequest);
    }

    private void updateWarning(String updateLink, int updateIsForce) {
        String message = getUpdateMessage(updateIsForce);

        UpdateDialog updateDialog = new UpdateDialog(this, done -> {
            if (done)
                startDownload(updateLink);
            else {
                if (updateIsForce == 1)
                    APP.killApp(SplashActivity.this);
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }
            }
        }, message);

       DialogUtil.showDialog(SplashActivity.this, updateDialog, false, true);

//        Objects.requireNonNull(updateDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        updateDialog.getWindow().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.dialog_bg));
//        updateDialog.setCancelable(false);
//        updateDialog.show();
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        width = (int) ((width) * 0.9);
//        updateDialog.getWindow().setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT);

    }

    private String getUpdateMessage(int updateIsForce) {
        return updateIsForce == 1 ?
                getString(R.string.text_obligation_update) :
                getString(R.string.text_optional_update);
    }

    private void saveDeviceInfo() {
        SaveDeviceInfoBody saveDeviceInfoBody = SaveDeviceInfoBody.builder().
                customerId(getCustomerInfoReturnValue.getCustomerId()).
                applicationVersion(publicFunctions.getAppVersionCode(SplashActivity.this)).
                sdkVersion(String.valueOf(Build.VERSION.SDK_INT)).
                deviceName(Build.MANUFACTURER).
                deviceModel(Build.MODEL).
                updteAppTime("0").
                updteAppYear("0").
                updteAppMonth("0").
                updteAppDay("0").
                build();

        SaveDeviceInfoController saveDeviceInfoController = new SaveDeviceInfoController();
        saveDeviceInfoController.start(getCustomerInfoReturnValue.getCustomerId(), getCustomerInfoReturnValue.getAccessToken()
                , saveDeviceInfoBody, new IResponseListener<SarvApiResponseNoList>() {
                    @Override
                    public void onSuccess(SarvApiResponseNoList response) {
                        if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            SplashActivity.this.finish();
                        } else {
                            APP.customToast(response.getMessage(),SplashActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error,SplashActivity.this);
                    }
                });
    }


}
