package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.fragment.MyOrderListFragment;
import ir.sarvwood.workshop.utils.APP;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.fab)
    protected FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        APP.currentActivity = MainActivity.this;

        getMyOwnOrder();
        setButtonClickConfig();

    }

    private void setButtonClickConfig() {
        floatingActionButton.setOnClickListener(view -> openOrderList());
    }

    private void openOrderList() {

        startActivity(new Intent(MainActivity.this, OrderActivity.class));

    }

    public void getMyOwnOrder() {

        MyOrderListFragment mainFragment = MyOrderListFragment.newInstance();

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mainFragment)
                .addToBackStack(null)
                .commit();
    }


}
