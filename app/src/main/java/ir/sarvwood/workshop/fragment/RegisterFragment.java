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
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.activity.ActivationActivity;
import ir.sarvwood.workshop.activity.LoginActivity;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;
import ir.sarvwood.workshop.webservice.insrtcustomer.InsrtCustomerSimpleBody;
import ir.sarvwood.workshop.webservice.insrtcustomer.InsrtCustomerSimpleController;
import ir.sarvwood.workshop.webservice.insrtcustomer.InsrtCustomerSimpleRerunValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.sendsmsofcnfrmcode.SendSmsOfCnfrmCodeBody;
import ir.sarvwood.workshop.webservice.sendsmsofcnfrmcode.SendSmsOfCnfrmCodeController;
import ir.solmazzm.lib.engine.util.DialogUtil;

public class RegisterFragment extends Fragment implements IInternetController {


    private static final int INSERT_CUSTOMER = 1;
    private static final int SEND_CONFIRM_SMS = 2;
    private static int isEdit;
    @BindView(R.id.tv_warning)
    protected AppCompatTextView tvWarning;
    @BindView(R.id.et_pass)
    AppCompatEditText etPass;
    @BindView(R.id.et_confirm_pass)
    AppCompatEditText etConfirmPass;
    @BindView(R.id.et_full_name)
    AppCompatEditText etFullName;
    @BindView(R.id.et_company)
    AppCompatEditText etCompany;
    @BindView(R.id.et_mobile)
    AppCompatEditText etMobile;
    private InsrtCustomerSimpleRerunValue insrtCustomerSimpleRerunValue;

    public static RegisterFragment newInstance(int isEdit) {

        isEdit = isEdit;
        return new RegisterFragment();
    }

    @OnClick(R.id.btn_register)
    void register() {
        if (!checkValidity()) {
            APP.customToast("پرکردن تمامی مقادیر الزامی می باشد");
            return;
        }

        if (!checkPassword())
            return;

        insertCustomer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        ButterKnife.bind(this, v);
        APP.currentActivity = getActivity();
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        APP.currentActivity = getActivity();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        insrtCustomerSimpleRerunValue = InsrtCustomerSimpleRerunValue.builder().build();
        tvWarning.setVisibility(isEdit == 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(APP.currentActivity).isOnline();
    }

    private void openInternetCheckingDialog(int methodCaller) {
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
                switch (methodCaller) {
                    case INSERT_CUSTOMER:
                        insertCustomer();
                        break;
                    case SEND_CONFIRM_SMS:
                        sendSmsOfCnfrmCode();
                        break;
                }
            }
        });

        DialogUtil.showDialog(APP.currentActivity, dialog, false, true);

    }

    private boolean checkPassword() {
        if (etPass.length() < 6) {
            APP.customToast(getString(R.string.text_password_lenght));
            return false;
        }
        if (Objects.requireNonNull(etPass.getText()).toString().equals(Objects.requireNonNull(etConfirmPass.getText()).toString())) {
            APP.customToast(getString(R.string.text_repeat_password));
            return false;
        }

        return true;
    }

    private boolean checkValidity() {
        return !(
                "".equals(Objects.requireNonNull(etFullName.getText()).toString()) &&
                        "".equals(Objects.requireNonNull(etCompany.getText()).toString()) &&
                        "".equals(Objects.requireNonNull(etMobile.getText()).toString()) &&
                        "".equals(Objects.requireNonNull(etPass.getText()).toString()));
    }

    private void insertCustomer() {

        if (!isOnline()) {
            openInternetCheckingDialog(INSERT_CUSTOMER);
        }

        InsrtCustomerSimpleBody insrtCustomerSimpleBody = InsrtCustomerSimpleBody.builder()
                .fullName(Objects.requireNonNull(etFullName.getText()).toString())
                .companyName(Objects.requireNonNull(etCompany.getText()).toString())
                .mobileNo(Objects.requireNonNull(etMobile.getText()).toString())
                .pass(Objects.requireNonNull(etPass.getText()).toString())
                .build();

        InsrtCustomerSimpleController insrtCustomerSimpleController = new InsrtCustomerSimpleController();
        insrtCustomerSimpleController.start(insrtCustomerSimpleBody, new IResponseListener<SarvApiResponse>() {
            @Override
            public void onSuccess(SarvApiResponse response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    insrtCustomerSimpleRerunValue = (InsrtCustomerSimpleRerunValue) response.getData().get(0);
                    GeneralPreferences.getInstance(APP.currentActivity).putInsrtCustomerSimpleRerunValueResponse(insrtCustomerSimpleRerunValue);
                    sendSmsOfCnfrmCode();
                }
            }

            @Override
            public void onFailure(String error) {
                insrtCustomerSimpleRerunValue = null;
                APP.customToast(error);
            }
        });
    }

    private void sendSmsOfCnfrmCode() {
        if (!isOnline()) {
            openInternetCheckingDialog(SEND_CONFIRM_SMS);
        }

        SendSmsOfCnfrmCodeBody sendSmsOfCnfrmCodeBody = SendSmsOfCnfrmCodeBody.builder()
                .mobileNo(Objects.requireNonNull(etMobile.getText()).toString())
                .build();

        SendSmsOfCnfrmCodeController sendSmsOfCnfrmCodeController = new SendSmsOfCnfrmCodeController();
        sendSmsOfCnfrmCodeController.start(sendSmsOfCnfrmCodeBody, new IResponseListener<SarvApiResponseNoList>() {
            @Override
            public void onSuccess(SarvApiResponseNoList response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    startActivity(new Intent(APP.currentActivity, ActivationActivity.class));
                }
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error);
            }
        });


    }


}
