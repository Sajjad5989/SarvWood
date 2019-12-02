package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;
import ir.sarvwood.workshop.webservice.changepassword.ChangeCustomerPasswordBody;
import ir.sarvwood.workshop.webservice.changepassword.ChangeCustomerPasswordController;
import ir.sarvwood.workshop.webservice.myorders.GetOrderDetailsItemReturnValueList;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.solmazzm.lib.engine.util.DialogUtil;

public class ChangePassFragment extends Fragment implements IInternetController {


    @BindView(R.id.et_old_pass)
    protected AppCompatEditText etOldPass;

    @BindView(R.id.et_new_pass)
    protected AppCompatEditText etNewPass;

    @BindView(R.id.et_new_pass_repeat)
    protected AppCompatEditText etNewPassRepeat;

    @OnClick(R.id.btn_change)
    void callChangePass() {
        if (!checkValidity())
            return;

        if (!isOnline()) {
            openInternetCheckingDialog();
        }

        changePass();

    }


    public static ChangePassFragment newInstance() {

        return new ChangePassFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_pass, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        APP.currentActivity = getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

    }

    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(APP.currentActivity).isOnline();
    }

    private void openInternetCheckingDialog() {
        ConnectionInternetDialog dialog = new ConnectionInternetDialog(APP.currentActivity, new InternetConnectionListener() {
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
            }
        });

        DialogUtil.showDialog(APP.currentActivity, dialog, false, true);

    }

    private boolean checkValidity() {
        if ("".equals(etOldPass.getText().toString()) || "".equals(etNewPass.getText().toString()) ||
                "".equals(etNewPassRepeat.getText().toString())) {
            APP.customToast(getString(R.string.text_need_all_parameter));
            return false;
        }
        if (!etOldPass.getText().toString().equals(etNewPass.getText().toString())) {
            APP.customToast(getString(R.string.text_repeat_password));
            return false;
        }

        return true;
    }

    private void changePass() {
        int userId = GeneralPreferences.getInstance(APP.currentActivity).getInt(BuildConfig.userId);
        String token = GeneralPreferences.getInstance(APP.currentActivity).getString(BuildConfig.accessToken);

        ChangeCustomerPasswordBody changeCustomerPasswordBody = ChangeCustomerPasswordBody.builder()
                .customerId(userId)
                .oldPass(Objects.requireNonNull(etOldPass.getText()).toString())
                .newPass(Objects.requireNonNull(etNewPass.getText()).toString())
                .build();

        ChangeCustomerPasswordController changeCustomerPasswordController = new ChangeCustomerPasswordController();
        changeCustomerPasswordController.start(userId, token, changeCustomerPasswordBody,
                new IResponseListener<SarvApiResponseNoList>() {
                    @Override
                    public void onSuccess(SarvApiResponseNoList response) {
                        if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                            GeneralPreferences.getInstance(APP.currentActivity).remove(BuildConfig.userName);
                            GeneralPreferences.getInstance(APP.currentActivity).remove(BuildConfig.userPass);
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

}
