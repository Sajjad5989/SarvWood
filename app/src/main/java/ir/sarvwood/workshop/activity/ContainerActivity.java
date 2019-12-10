package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.fragment.AboutUsFragment;
import ir.sarvwood.workshop.fragment.ChangePassFragment;
import ir.sarvwood.workshop.fragment.EditProfileFragment;
import ir.sarvwood.workshop.fragment.PreOrderFragment;
import ir.sarvwood.workshop.fragment.RegisterFragment;
import ir.sarvwood.workshop.fragment.SuggestFragment;
import ir.sarvwood.workshop.interfaces.IDefault;
import ir.sarvwood.workshop.interfaces.IRtl;
import ir.sarvwood.workshop.utils.APP;

public class ContainerActivity extends AppCompatActivity implements IRtl, IDefault {


    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);

        OnActivityDefaultSetting();
        prepareToolbar();
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Intent in = getIntent();
        bundle = in.getExtras();
        if (bundle != null)
            openCorrectFragment(bundle.getInt("fragmentFlag"));
    }


    private void prepareToolbar() {
        toolbar.setTitle(getString(R.string.text_more_info));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void openCorrectFragment(int type) {
        switch (type) {
            case 1:
                toolbar.setTitle(R.string.text_register);
                openRegister();
                break;
            case 2:
                toolbar.setTitle(R.string.text_edit_profile);
                openEditProfile();
                break;
            case 3:
                toolbar.setTitle(R.string.text_about_us);
                openAboutContactUs(1);
                break;
            case 4:
                toolbar.setTitle(R.string.text_suggest);
                openSuggest();
                break;
            case 5:
                toolbar.setTitle(R.string.text_pre_order);
                openPreOrderList();
                break;
            case 6:
                toolbar.setTitle(R.string.text_order_item_detail);
                openOrderItems();
                break;
            case 7:
                toolbar.setTitle(R.string.text_contact_us);
                openAboutContactUs(2);
                break;
                case 8:
                toolbar.setTitle(getString(R.string.text_change_pass));
                    openChangePassword();
                break;
        }
    }

    private void openAboutContactUs(int type) {
        AboutUsFragment aboutUsFragment = AboutUsFragment.newInstance(type);
        getFragmentManager().beginTransaction()
                .add(R.id.frag_container, aboutUsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void openEditProfile() {
        EditProfileFragment editProfileFragment = EditProfileFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.frag_container, editProfileFragment)
                .addToBackStack(null)
                .commit();
    }
    private void openRegister() {
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.frag_container, registerFragment)
                .addToBackStack(null)
                .commit();
    }

    private void openSuggest() {
        SuggestFragment registerFragment = SuggestFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.frag_container, registerFragment)
                .addToBackStack(null)
                .commit();
    }

    private void openPreOrderList() {

        OrderActivity.woodOrderModelList = new ArrayList<>();
        PreOrderFragment preOrderFragment = PreOrderFragment.newInstance( 2);
        getFragmentManager().beginTransaction()
                .add(R.id.frag_container, preOrderFragment)
                .addToBackStack(null)
                .commit();
    }

    private void openChangePassword()
    {
        ChangePassFragment changePassFragment = ChangePassFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.frag_container, changePassFragment)
                .addToBackStack(null)
                .commit();
    }

    private void  openOrderItems() {
//        GetOrderDetailsItemReturnValueList returnValueList;
//        returnValueList = (GetOrderDetailsItemReturnValueList) bundle.getSerializable("GetOrderDetailsItemList");

        PreOrderFragment preOrderFragment = PreOrderFragment.newInstance( 1);
        getFragmentManager().beginTransaction()
                .add(R.id.frag_container, preOrderFragment)
                .addToBackStack(null)
                .commit();
    }


}
