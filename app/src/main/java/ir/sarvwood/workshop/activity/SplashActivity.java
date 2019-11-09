package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;
import java.util.Objects;

import androidx.constraintlayout.widget.ConstraintLayout;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;

public class SplashActivity  extends AppCompatActivity implements IInternetController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  FirebaseApp.initializeApp(this);

        setPersian();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        doActionAfterSplash();
    }
    private void setPersian() {
        String languageToLoad = "fa";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        new Geocoder(SplashActivity.this, new Locale("fa"));
    }

    private void doActionAfterSplash() {

        final Handler handler = new Handler();
        handler.postDelayed(this::letsGo, 3000);
    }

    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(this).isOnline();
    }


    private void letsGo() {

//        boolean showedSlider = GeneralPreferences.getInstance(this).getBoolean(getString(R.string.text_preference_slider_is_show));
//
//        if (showedSlider) {
//            String userName = GeneralPreferences.getInstance(SplashActivity.this).getString(BuildConfig.userName);
//            String userPass = GeneralPreferences.getInstance(SplashActivity.this).getString(BuildConfig.userPass);
//
//            if (userName == null || userPass == null) {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//            } else {
//                getServiceMan();
//
//            }
//        } else {
//            startActivity(new Intent(SplashActivity.this, IntroSliderActivity.class));
//        }
        startActivity(new Intent(SplashActivity.this, IntroSliderActivity.class));

        SplashActivity.this.finish();

    }
    private void openInternetCheckingDialog() {
        ConnectionInternetDialog dialog = new ConnectionInternetDialog(this, new InternetConnectionListener() {
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
                //تابع مربوطه ر صدا بزنم
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg_no_padding));
        dialog.show();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width = (int) ((width) * 0.8);
        dialog.getWindow().setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }


}
