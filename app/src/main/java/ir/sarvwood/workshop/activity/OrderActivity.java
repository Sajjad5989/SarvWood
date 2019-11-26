package ir.sarvwood.workshop.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.adapter.StepperAdapter;
import ir.sarvwood.workshop.interfaces.IDefault;
import ir.sarvwood.workshop.interfaces.IRtl;
import ir.sarvwood.workshop.model.WoodOrderModel;
import ir.sarvwood.workshop.utils.APP;

public class OrderActivity extends AppCompatActivity implements StepperLayout.StepperListener, Serializable, IRtl, IDefault {


    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.stepperLayout)
    protected StepperLayout mStepperLayout;

    public static WoodOrderModel woodOrderModel = new WoodOrderModel();
    public static List<WoodOrderModel> woodOrderModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        APP.currentActivity = OrderActivity.this;
        prepareToolbar();
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        openOrderSteps();
    }
    @Override
    protected void onResume() {
        super.onResume();
        APP.currentActivity = OrderActivity.this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void OnActivityDefaultSetting() {
        OnPageRight();
    }

    @Override
    public void OnPageRight() {
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    private void prepareToolbar() {
        toolbar.setTitle(R.string.text_order);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right);
    }

    private void openOrderSteps()
    {
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);
    }

    @Override
    public void onCompleted(View completeButton) {
       // Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
       // Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {
        finish();
    }


}