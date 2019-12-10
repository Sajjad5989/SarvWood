package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.fragment.MyOrderListFragment;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.savetoken.SaveTokenKeyBody;
import ir.sarvwood.workshop.webservice.savetoken.SaveTokenKeyController;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.fab)
    protected FloatingActionButton floatingActionButton;
    @BindView(R.id.image_more_info)
    protected AppCompatImageView imageMoreInfo;

    @BindView(R.id.text_user)
    protected AppCompatTextView textUser;

    @BindView(R.id.tv_help)
    protected AppCompatTextView tvHelp;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tvHelp.setText(Html.fromHtml(getString(R.string.text_help_one)));
        getUserName();
        getMyOwnOrder();
        setButtonClickConfig();

        checkTokenKey();
    }


    @Override
    public void onBackPressed( ){

        if ( doubleBackToExitPressedOnce ) {
            Intent intent = new Intent( Intent.ACTION_MAIN );
            intent.addCategory( Intent.CATEGORY_HOME );
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
            finishAffinity();
            finish();
            System.exit( 0 );
            MainActivity.this.finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        APP.customToast(getString(R.string.text_all_tap_back_button) ,MainActivity.this);
        new Handler().postDelayed( ( ) -> doubleBackToExitPressedOnce = false, 2000 );
    }

    private void checkTokenKey() {
        String token = GeneralPreferences.getInstance(MainActivity.this).getString("fireToken");
        if (token != null) {
            saveToken(token);
        }
    }

    private void saveToken(String fireToken) {

        int userId = GeneralPreferences.getInstance(MainActivity.this).getCustomerId();
        String token = GeneralPreferences.getInstance(MainActivity.this).getToken();
        SaveTokenKeyBody keyBody = SaveTokenKeyBody.builder()
                .customerId(userId)
                .deviceTokenKey(fireToken)
                .build();

        SaveTokenKeyController saveTokenKeyController = new SaveTokenKeyController();
        saveTokenKeyController.start(userId, token, keyBody, new IResponseListener<SarvApiResponseNoList>() {
            @Override
            public void onSuccess(SarvApiResponseNoList response) {
                if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                    GeneralPreferences.getInstance(MainActivity.this).remove("fireToken");
                } else APP.customToast(response.getMessage(),MainActivity.this);
            }

            @Override
            public void onFailure(String error) {
                APP.customToast(error,MainActivity.this);
            }
        });
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void setButtonClickConfig() {
        floatingActionButton.setOnClickListener(view -> openOrderList());
        imageMoreInfo.setOnClickListener(view -> openMoreInfo());
    }

    private void openMoreInfo() {
        startActivity(new Intent(MainActivity.this, MoreActivity.class));
    }

    private void openOrderList() {
        openPreOrderListFragment();
    }


    private void openPreOrderListFragment() {
        Intent intent = new Intent(MainActivity.this, ContainerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentFlag", 5);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getMyOwnOrder() {
        MyOrderListFragment mainFragment = MyOrderListFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mainFragment)
                .addToBackStack(null)
                .commit();
    }

    private void getUserName() {
        GetCustomerInfoReturnValue
                getCustomerInfoReturnValue =
                GeneralPreferences.getInstance(MainActivity.this).getListCustomerInfoResponse();
        textUser.setText(getCustomerInfoReturnValue.getFullName());
    }

}
