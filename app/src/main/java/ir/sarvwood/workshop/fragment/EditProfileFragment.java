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
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.updatecustomerinfo.UpdateCustomerInfoBody;
import ir.sarvwood.workshop.webservice.updatecustomerinfo.UpdateCustomerInfoController;
import ir.solmazzm.lib.engine.util.DialogUtil;

public class EditProfileFragment extends Fragment implements IInternetController {

    @BindView(R.id.et_full_name)
    AppCompatEditText etFullName;
    @BindView(R.id.et_company)
    AppCompatEditText etCompany;
    @BindView(R.id.et_mobile)
    AppCompatEditText etMobile;
    @BindView(R.id.et_address)
    AppCompatEditText etAddress;
    private GetCustomerInfoReturnValue getCustomerInfoReturnValue;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @OnClick(R.id.btn_register)
    void updateInfo() {

        if (!checkValidity()) {
            APP.customToast(getString(R.string.text_need_all_parameter));
            return;
        }

        if (!isOnline()) {
            openInternetCheckingDialog();
        }

        update();
    }

    private boolean checkValidity() {
        return !(
                "".equals(Objects.requireNonNull(etFullName.getText()).toString()) &&
                        "".equals(Objects.requireNonNull(etCompany.getText()).toString()) &&
                        "".equals(Objects.requireNonNull(etMobile.getText()).toString()) &&
                        "".equals(Objects.requireNonNull(etAddress.getText()).toString()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);

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
        loadInfo();

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


    private void loadInfo() {
        getCustomerInfoReturnValue =
                GeneralPreferences.getInstance(APP.currentActivity).getListCustomerInfoResponse();

        etFullName.setText(getCustomerInfoReturnValue.getFullName());
        etCompany.setText(getCustomerInfoReturnValue.getCompanyName());
        etMobile.setText(getCustomerInfoReturnValue.getPhone());
        etAddress.setText(getCustomerInfoReturnValue.getAddress());

    }

    private void update() {
        UpdateCustomerInfoBody updateCustomerInfoBody = UpdateCustomerInfoBody.builder()
                .customerId(getCustomerInfoReturnValue.getCustomerId())
                .fullName(etFullName.getText().toString())
                .companyName(etCompany.getText().toString())
                .address(etAddress.getText().toString())
                .latitiude(getCustomerInfoReturnValue.getLatitiude())
                .longtiude(getCustomerInfoReturnValue.getLongtiude())
                .phone(etMobile.getText().toString())
                .build();

        UpdateCustomerInfoController updateCustomerInfoController = new UpdateCustomerInfoController();
        updateCustomerInfoController.start(getCustomerInfoReturnValue.getCustomerId(), getCustomerInfoReturnValue.getAccessToken(),
                updateCustomerInfoBody, new IResponseListener<SarvApiResponseNoList>() {
                    @Override
                    public void onSuccess(SarvApiResponseNoList response) {
                        if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                            APP.customToast("لطفا مجددا وارد برنامه شوید");
                            GeneralPreferences.getInstance(APP.currentActivity).deleteAllInfo();
                            APP.killApp();
                        } else
                            APP.customToast(response.getMessage());
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error);
                    }
                }
        );
    }

}
