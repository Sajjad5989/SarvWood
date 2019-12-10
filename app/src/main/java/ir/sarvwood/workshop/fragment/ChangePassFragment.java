package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
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
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.solmazzm.lib.engine.util.DialogUtil;

public class ChangePassFragment extends Fragment implements IInternetController {


    @BindView(R.id.et_old_pass)
    protected AppCompatEditText etOldPass;

    @BindView(R.id.et_new_pass)
    protected AppCompatEditText etNewPass;

    @BindView(R.id.et_new_pass_repeat)
    protected AppCompatEditText etNewPassRepeat;

    @BindView(R.id.image_view_old_pass)
    protected AppCompatImageButton imageViewOldPass;
    @BindView(R.id.image_view_repeat_pass)
    protected AppCompatImageButton imageViewRepeatPass;
    @BindView(R.id.image_view_new_pass)
    protected AppCompatImageButton imageViewNewPass;

    @OnClick(R.id.btn_change)
    void callChangePass() {
        if (!checkValidity())
            return;


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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        btnClickConfig();
    }

    private void btnClickConfig()
    {
       imageViewOldPass.setOnClickListener(view -> showOldPass());
       imageViewNewPass.setOnClickListener(view -> showNewPass());
       imageViewRepeatPass.setOnClickListener(view -> showRepeatPass());
    }

    private int showHideOld;
    private int showHideNewPass;
    private int showHideRepeat;
    private void showOldPass()
    {
        imageViewOldPass.setOnClickListener(v -> {

            if (showHideOld == 0) {
                etOldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageViewOldPass.setImageResource(R.drawable.eye);
                showHideOld = 1;
            } else if (showHideOld == 1) {
                etOldPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewOldPass.setImageResource(R.drawable.eye_off);
                showHideOld = 0;
            }
        });
    }

    private void showNewPass()
    {
        imageViewNewPass.setOnClickListener(v -> {

            if (showHideNewPass == 0) {
                etNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageViewNewPass.setImageResource(R.drawable.eye);
                showHideNewPass = 1;
            } else if (showHideNewPass == 1) {
                etNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewNewPass.setImageResource(R.drawable.eye_off);
                showHideNewPass = 0;
            }
        });
    }

    private void showRepeatPass()
    {
        imageViewRepeatPass.setOnClickListener(v -> {

            if (showHideRepeat == 0) {
                etNewPassRepeat.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageViewRepeatPass.setImageResource(R.drawable.eye);
                showHideRepeat = 1;
            } else if (showHideRepeat == 1) {
                etNewPassRepeat.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewRepeatPass.setImageResource(R.drawable.eye_off);
                showHideRepeat = 0;
            }
        });
    }
    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(getActivity()).isOnline();
    }

    private void openInternetCheckingDialog() {
        ConnectionInternetDialog dialog = new ConnectionInternetDialog(getActivity(), new InternetConnectionListener() {
            @Override
            public void onInternet() {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }

            @Override
            public void onFinish() {
                APP.killApp(getActivity());
            }

            @Override
            public void OnRetry() {
                changePass();
            }
        });

        DialogUtil.showDialog(getActivity(), dialog, false, true);

    }

    private boolean checkValidity() {
        if ("".equals(etOldPass.getText().toString()) || "".equals(etNewPass.getText().toString()) ||
                "".equals(etNewPassRepeat.getText().toString())) {
            APP.customToast(getString(R.string.text_need_all_parameter),getActivity());
            return false;
        }
        if (!etNewPassRepeat.getText().toString().equals(etNewPass.getText().toString())) {
            APP.customToast(getString(R.string.text_repeat_password),getActivity());
            return false;
        }

        return true;
    }

    private void changePass() {

        if (!isOnline()) {
            openInternetCheckingDialog();
        }

        int userId = GeneralPreferences.getInstance(getActivity()).getInt(BuildConfig.userId);
        String token = GeneralPreferences.getInstance(getActivity()).getString(BuildConfig.accessToken);

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
                            APP.customToast(getString(R.string.text_successful),getActivity());
                            GeneralPreferences.getInstance(getActivity()).deleteAllInfo();
                            APP.killApp(getActivity());
                        } else {
                            APP.customToast(response.getMessage(),getActivity());
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error,getActivity());
                    }
                });
    }

}
