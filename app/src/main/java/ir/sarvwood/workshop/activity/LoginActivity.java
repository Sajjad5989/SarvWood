package ir.sarvwood.workshop.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
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
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

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
    @BindView(R.id.image_view_pass)
    protected AppCompatImageButton imageViewPass;

    private GetCustomerInfoReturnValue getCustomerInfoReturnValue;
    private PublicFunctions publicFunctions = new PublicFunctions();
    private BaseInfoReturnValue baseInfoReturnValue;
    private int showHide = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        buttonClickConfig();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(this).isOnline();
    }

    private void buttonClickConfig() {
        btnLogin.setOnClickListener(view -> logIn());
        btnRegister.setOnClickListener(view -> openRegisterFragment());
        imageViewPass.setOnClickListener(v -> {

            if (showHide == 0) {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageViewPass.setImageResource(R.drawable.eye);
                showHide = 1;
            } else if (showHide == 1) {
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewPass.setImageResource(R.drawable.eye_off);
                showHide = 0;
            }
        });
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
            APP.customToast(getString(R.string.text_enter_user_name), LoginActivity.this);
            return;
        }
        if ("".equals(Objects.requireNonNull(etPassword.getText()).toString())) {
            APP.customToast(getString(R.string.text_enter_password), LoginActivity.this);
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
                APP.killApp(LoginActivity.this);
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
                .applicationVersion(publicFunctions.getAppVersionCode(LoginActivity.this))
                .deviceModel(Build.MODEL)
                .deviceName(Build.MANUFACTURER)
                .sdkVersion(String.valueOf(Build.VERSION.SDK_INT))
                .build();
        return getCustomerInfoBody;
    }


    private ProgressDialog progress;
    private void showProgress() {
        progress = new ProgressDialog(LoginActivity.this);
        String message = getString(R.string.text_please_wait);
        SpannableString spannableString =  new SpannableString(message);

        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(LoginActivity.this.getAssets(),
                "fonts/iran_sans.ttf"));
        spannableString.setSpan(typefaceSpan, 0, message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        progress.setMessage(spannableString);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    private void getCustomerInfo() {
        if (!isOnline()) {
            openInternetCheckingDialog();
        }

        showProgress();

        GetCustomerInfoController getCustomerInfoController = new GetCustomerInfoController();
        getCustomerInfoController.start(getCustomerInfoBody(), new IResponseListener<SarvApiResponse<GetCustomerInfoReturnValue>>() {

            @Override
            public void onSuccess(SarvApiResponse response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    getCustomerInfoReturnValue = (GetCustomerInfoReturnValue) response.getData().get(0);

                    saveInSharePreference();
                    if (chkRemember.isChecked()) {
                        GeneralPreferences.getInstance(LoginActivity.this).putString(BuildConfig.userName, Objects.requireNonNull(etUserName.getText()).toString());
                        GeneralPreferences.getInstance(LoginActivity.this).putString(BuildConfig.userPass, Objects.requireNonNull(etPassword.getText()).toString());
                    }
                    getBaseInfo();

                } else {
                    APP.customToast(response.getMessage(), LoginActivity.this);
                    progress.hide();
                }

            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error, LoginActivity.this);
                LoginActivity.this.finish();
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
                            String appVersion = String.valueOf(publicFunctions.getAppVersionCode(LoginActivity.this));
                            if (!appVersion.equals(baseInfoReturnValue.getAndroidGlobalAppVer())) {
                                if (baseInfoReturnValue.getAndroidGlobalAppForceUpdate() == 1) {
                                    progress.hide();
                                    updateWarning(baseInfoReturnValue.getAndroidAppDlLink(), baseInfoReturnValue.getAndroidGlobalAppForceUpdate());
                                }
                            }
                            GeneralPreferences.getInstance(LoginActivity.this).putBaseInfo(baseInfoReturnValue);
                            saveDeviceInfo();
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error, LoginActivity.this);
                        APP.killApp(LoginActivity.this);
                    }
                });
    }


    public void saveInSharePreference() {

        GeneralPreferences.getInstance(LoginActivity.this).putListCustomerInfoResponse(getCustomerInfoReturnValue);
        GeneralPreferences.getInstance(LoginActivity.this).putCustomerId(getCustomerInfoReturnValue.getCustomerId());
        GeneralPreferences.getInstance(LoginActivity.this).putToken(getCustomerInfoReturnValue.getAccessToken());
    }


    private void startDownload(String downloadLink) {
        DownloadManager mManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request mRqRequest = new DownloadManager.Request(
                Uri.parse(downloadLink));
        Objects.requireNonNull(mManager).enqueue(mRqRequest);
    }

    private void updateWarning(String updateLink, int updateIsForce) {
        UpdateDialog updateDialog = new UpdateDialog(this, done -> {
            if (done)
                startDownload(updateLink);
            else {
                if (updateIsForce == 1)
                    APP.killApp(LoginActivity.this);
                else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                }
            }
        }, getUpdateMessage(updateIsForce));

        DialogUtil.showDialog(LoginActivity.this, updateDialog, false, true);

    }

    private String getUpdateMessage(int updateIsForce) {
        return updateIsForce == 1 ?
                getString(R.string.text_obligation_update) :
                getString(R.string.text_optional_update);
    }

    private void saveDeviceInfo() {
        SaveDeviceInfoBody saveDeviceInfoBody = SaveDeviceInfoBody.builder().
                customerId(getCustomerInfoReturnValue.getCustomerId()).
                applicationVersion(publicFunctions.getAppVersionCode(LoginActivity.this)).
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
                            progress.hide();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                        } else {
                            progress.hide();
                            APP.customToast(response.getMessage(), LoginActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        progress.hide();
                        APP.customToast(error, LoginActivity.this);
                    }
                });
    }

}
