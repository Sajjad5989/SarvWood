package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.dialog.productteam.ProductTeamDialog;
import ir.sarvwood.workshop.dialog.socaildialog.SocialNetworkDialog;
import ir.sarvwood.workshop.dialog.socaildialog.SocialNetworkOnClickListener;
import ir.sarvwood.workshop.dialog.yesno.YesNoDialog;
import ir.sarvwood.workshop.interfaces.IDefault;
import ir.sarvwood.workshop.interfaces.IRtl;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.webservice.baseinfo.BaseInfoReturnValue;
import ir.solmazzm.lib.engine.util.DialogUtil;

public class MoreActivity extends AppCompatActivity implements IRtl, IDefault {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    private BaseInfoReturnValue baseInfoReturnValue;

    @OnClick({R.id.tv_view_exit})
    void exitApp() {
        exit();
    }

    @OnClick(R.id.tv_edit_profile)
    void openEditProfileFragment() {
        openFragment(2);
    }

    @OnClick(R.id.tv_about_us)
    void openAboutUs() {
        openFragment(3);
    }

    @OnClick(R.id.tv_contact_us)
    void openAContactUs() {
        openFragment(7);
    }

    @OnClick(R.id.tv_suggest)
    void openSuggest() {
        openFragment(4);
    }

    @OnClick(R.id.tv_follow_us)
    void openDialog() {
        SocialNetworkDialog socialNetworkDialog = new SocialNetworkDialog(this, new SocialNetworkOnClickListener() {
            @Override
            public void onInstagram() {
                goToWebUrl(baseInfoReturnValue.getInstagramLink());
            }

            @Override
            public void onApparat() {
                goToWebUrl(baseInfoReturnValue.getAndroidAppDlLink());
            }

            @Override
            public void onTelegram() {
                goToWebUrl(baseInfoReturnValue.getTelegramLink());
            }
        });
        DialogUtil.showDialog(this, socialNetworkDialog, false, true);

    }

    @OnClick(R.id.tv_about_team)
    void openTeamProduction() {
        ProductTeamDialog productTeamDialog = new ProductTeamDialog(this);
        DialogUtil.showDialog(this, productTeamDialog, false, true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);


        OnActivityDefaultSetting();
        prepareToolbar();
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        loadInfo();
    }

    private void prepareToolbar() {
        toolbar.setTitle(R.string.text_more_info);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        APP.currentActivity = MoreActivity.this;
        APP.setPersianUi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
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

    private void exit() {

        String msgExit = getResources().getString(R.string.text_exit_app) +
                getResources().getString(R.string.text_exit_app_confirmation);
        YesNoDialog yesNoDialog = new YesNoDialog(MoreActivity.this, getString(R.string.text_exit_title)
                , msgExit, done -> {
            if (done) {
                GeneralPreferences.getInstance(this).remove(BuildConfig.userName);
                GeneralPreferences.getInstance(this).remove(BuildConfig.userPass);
                APP.killApp();
            }
        });

        DialogUtil.showDialog(MoreActivity.this, yesNoDialog, false, true);

    }

    private void openFragment(int type) {
        Intent intent = new Intent(MoreActivity.this, ContainerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentFlag", type);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void loadInfo() {
        baseInfoReturnValue = GeneralPreferences.getInstance(MoreActivity.this).getBaseInfo();
    }

    private void goToWebUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}
