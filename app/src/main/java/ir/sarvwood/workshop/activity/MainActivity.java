package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.fragment.MyOrderListFragment;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.webservice.apibodies.GetCustomerInfoBody;
import ir.sarvwood.workshop.webservice.sarvwoodapi.WebserviceCaller;

public class MainActivity extends AppCompatActivity implements IResponseListener {


    @BindView( R.id.fab )
    protected FloatingActionButton floatingActionButton;

    private void getCustomerData( ){
        WebserviceCaller webserviceCaller = new WebserviceCaller();
        GetCustomerInfoBody getCustomerInfoBody = GetCustomerInfoBody.builder().build();
        webserviceCaller.getCustomerInfo( getCustomerInfoBody, this );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );

        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.activity_main );
        ButterKnife.bind( this );
        APP.currentActivity = MainActivity.this;
        getCustomerData( );
        getMyOwnOrder();
        setButtonClickConfig();

    }

    private void setButtonClickConfig( ){
        floatingActionButton.setOnClickListener( view -> openOrderList() );
    }

    private void openOrderList( ){

        startActivity( new Intent( MainActivity.this, OrderActivity.class ) );

    }

    public void getMyOwnOrder( ){

        MyOrderListFragment mainFragment = MyOrderListFragment.newInstance();

        getFragmentManager().beginTransaction()
                .add( R.id.fragment_container, mainFragment )
                .addToBackStack( null )
                .commit();
    }


    @Override
    public void onSuccess( Object response ){
        GetCustomerInfoBody getCustomerInfoBody = ( GetCustomerInfoBody ) response;
    }

    @Override
    public void onFailure( String error ){
        APP.customToast( error );
    }
}
