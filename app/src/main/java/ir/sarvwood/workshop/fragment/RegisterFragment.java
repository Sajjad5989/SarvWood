package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
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
import ir.sarvwood.workshop.BuildConfig;
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
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class RegisterFragment extends Fragment implements IInternetController {


    private static final int INSERT_CUSTOMER = 1;
    private static final int SEND_CONFIRM_SMS = 2;
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
    private ProgressDialog progress;

    public static RegisterFragment newInstance() {

        return new RegisterFragment();
    }

    private void showProgress() {
        progress = new ProgressDialog(getActivity());
        String message = getString(R.string.text_please_wait);
        SpannableString spannableString = new SpannableString(message);

        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(getActivity().getAssets(),
                "fonts/iran_sans.ttf"));
        spannableString.setSpan(typefaceSpan, 0, message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        progress.setMessage(spannableString);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    @OnClick(R.id.btn_register)
    void register() {
        if (!checkValidity()) {
            APP.customToast(getString(R.string.text_need_all_parameter),getActivity());
            return;
        }

        if (!checkPassword())
            return;

        insertCustomer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_register, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        etFullName.requestFocus();

        insrtCustomerSimpleRerunValue = InsrtCustomerSimpleRerunValue.builder().build();
    }

    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(getActivity()).isOnline();
    }

    private void openInternetCheckingDialog(int methodCaller) {
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

        DialogUtil.showDialog(getActivity(), dialog, false, true);

    }

    private boolean checkPassword() {
        if (etPass.length() < 6) {
            APP.customToast(getString(R.string.text_password_lenght),getActivity());
            return false;
        }

        if (!Objects.requireNonNull(etPass.getText()).toString().equals(Objects.requireNonNull(etConfirmPass.getText()).toString())) {
            APP.customToast(getString(R.string.text_repeat_password),getActivity());
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

        showProgress();

        InsrtCustomerSimpleBody insrtCustomerSimpleBody = InsrtCustomerSimpleBody.builder()
                .fullName(Objects.requireNonNull(etFullName.getText()).toString())
                .companyName(Objects.requireNonNull(etCompany.getText()).toString())
                .mobileNo(Objects.requireNonNull(etMobile.getText()).toString())
                .pass(Objects.requireNonNull(etPass.getText()).toString())
                .build();

        InsrtCustomerSimpleController insrtCustomerSimpleController = new InsrtCustomerSimpleController();
        insrtCustomerSimpleController.start(insrtCustomerSimpleBody,
                new IResponseListener<SarvApiResponse<InsrtCustomerSimpleRerunValue>>() {
            @Override
            public void onSuccess(SarvApiResponse<InsrtCustomerSimpleRerunValue> response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    insrtCustomerSimpleRerunValue =  response.getData().get(0);
                    GeneralPreferences.getInstance(getActivity()).putInsrtCustomerSimpleRerunValueResponse(insrtCustomerSimpleRerunValue);
                    sendSmsOfCnfrmCode();
                }
                else {
                    progress.hide();
                    APP.customToast(response.getMessage(),getActivity());}
            }

            @Override
            public void onFailure(String error) {
                insrtCustomerSimpleRerunValue = null;
                progress.hide();
                APP.customToast(error,getActivity());
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
                    GeneralPreferences.getInstance(getActivity()).putString("mobile", etMobile.getText().toString());
                    progress.hide();
                    startActivity(new Intent(getActivity(), ActivationActivity.class));
                }
                else
                {
                    progress.hide();
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
