package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.fragment.MyOrderListFragment;
import ir.sarvwood.workshop.utils.APP;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.fab)
    protected FloatingActionButton floatingActionButton;
    @BindView(R.id.image_more_info)
    protected AppCompatImageView imageMoreInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getMyOwnOrder();
        setButtonClickConfig();
    }

    @Override
    public void onResume() {
        super.onResume();
        APP.currentActivity = MainActivity.this;
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

}
