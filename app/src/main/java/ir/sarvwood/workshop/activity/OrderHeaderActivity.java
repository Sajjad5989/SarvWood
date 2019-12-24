package ir.sarvwood.workshop.activity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.adapter.RadioAdapter;
import ir.sarvwood.workshop.dialog.internet.ConnectionInternetDialog;
import ir.sarvwood.workshop.dialog.internet.InternetConnectionListener;
import ir.sarvwood.workshop.interfaces.IDefault;
import ir.sarvwood.workshop.interfaces.IInternetController;
import ir.sarvwood.workshop.interfaces.IRtl;
import ir.sarvwood.workshop.model.order.CheckableObject;
import ir.sarvwood.workshop.model.order.HeaderWoodModel;
import ir.sarvwood.workshop.model.order.OrderListCreation;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.utils.OnlineCheck;

public class OrderHeaderActivity extends AppCompatActivity implements IInternetController, Serializable, IRtl, IDefault {


    public static HeaderWoodModel headerWoodModel;
    // public static int listRowIdx = -1;
    // public static List<WoodModel> woodOrderModelListHeader = new ArrayList<>();
    public static OrderListCreation orderListCreationHeader;

    public static List<CheckableObject> woodTypeList;
    public static List<CheckableObject> pvcThicknessList;
    public static List<CheckableObject> woodSheetList;


    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.recycler_wood)
    protected AnimatedRecyclerView recyclerWood;
    @BindView(R.id.recycler_pvc_thickness)
    protected AnimatedRecyclerView recyclerThickness;
    @BindView(R.id.recycler_sheet_size)
    protected AnimatedRecyclerView recyclerSheetSize;
    @BindView(R.id.linear_custom_dimension)
    protected LinearLayout linearCustomDimension;
    @BindView(R.id.et_wood_color)
    protected AppCompatEditText etWoodColor;
    @BindView(R.id.et_wood_brand)
    protected AppCompatEditText etWoodBrand;
    @BindView(R.id.et_wood_code)
    protected AppCompatEditText etWoodCode;
    @BindView(R.id.et_pvc_color)
    protected AppCompatEditText etPvcColor;
    @BindView(R.id.chk_wood_arrow)
    protected AppCompatCheckBox chkWoodArrow;


    @BindView(R.id.et_height)
    protected AppCompatEditText etHeight;

    @BindView(R.id.et_width)
    protected AppCompatEditText etWidth;


    private RadioAdapter woodTypeAdapter;
    private RadioAdapter pvcThicknessAdapter;
    private RadioAdapter woodSizeAdapter;

    @OnClick(R.id.btn_ok_header_order)
    void okHeaderOrder() {

        if (!checkValidity())
            return;

        headerWoodModel.setColor(etWoodColor.getText().toString());
        headerWoodModel.setBrand(etWoodBrand.getText().toString());
        headerWoodModel.setCode(etWoodCode.getText().toString());

        headerWoodModel.setPvcColor(etPvcColor.getText().toString());
        headerWoodModel.setPatterned(chkWoodArrow.isChecked() ? 1 : 0);

        if (headerWoodModel.getWoodSheetList().getIndex() == 4) {
            headerWoodModel.setWoodSheetLength(Integer.valueOf(etHeight.getText().toString()));
            headerWoodModel.setWoodSheetWidth(Integer.valueOf(etWidth.getText().toString()));
        }

        openPreOrderList();

    }

    private boolean checkValidity() {
        if ("".equals(etWoodColor.getText().toString())) {
            APP.customToast("رنگ چوب وارد نشده است", OrderHeaderActivity.this);
            return false;
        }
        if ("".equals(etWoodBrand.getText().toString())) {
            APP.customToast("برند چوب وارد نشده است", OrderHeaderActivity.this);
            return false;
        }
        if ("".equals(etWoodCode.getText().toString())) {
            APP.customToast("کد چوب وارد نشده است", OrderHeaderActivity.this);
            return false;
        }

        if (headerWoodModel.getWoodType() == null) {
            APP.customToast("لطفا نوع چوب را انتخاب نمایید", OrderHeaderActivity.this);
            return false;
        }
        if (headerWoodModel.getPvcThickness() == null) {
            APP.customToast("لطفا ضخامت پی وی سی را انتخاب نمایید", OrderHeaderActivity.this);
            return false;
        }

        if (headerWoodModel.getWoodSheetList() == null) {
            APP.customToast("لطفا سایز ورق را انتخاب نمایید", OrderHeaderActivity.this);
            return false;
        }

        if (headerWoodModel.getWoodSheetList().getIndex() == 4) {

            if ("".equals(etHeight.getText().toString()) || "0".equals(etHeight.getText().toString())) {
                APP.customToast("لطفا طول ورق را انتخاب نمایید", OrderHeaderActivity.this);
                return false;
            }

            if ("".equals(etWidth.getText().toString()) || "0".equals(etWidth.getText().toString())) {
                APP.customToast("لطفا عرض ورق را انتخاب نمایید", OrderHeaderActivity.this);
                return false;
            }

        }


        return true;

    }

    private void openPreOrderList() {
        Intent intent = new Intent(OrderHeaderActivity.this, ContainerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentFlag", 5);
        intent.putExtras(bundle);
        OrderHeaderActivity.this.finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_header_order);
        ButterKnife.bind(this);
        prepareToolbar();
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        if (bundle != null) {
            headerWoodModel = (HeaderWoodModel) bundle.getSerializable("headerWoodModel");
            etWoodColor.setText(headerWoodModel.getColor());
            etWoodBrand.setText(headerWoodModel.getBrand());
            etWoodCode.setText(headerWoodModel.getCode());
            etPvcColor.setText(headerWoodModel.getPvcColor());
            chkWoodArrow.setChecked(headerWoodModel.getPatterned() == 1);

            if (headerWoodModel.getWoodSheetList().getIndex() == 4 ) {
                linearCustomDimension.setVisibility(View.VISIBLE);
                etHeight.setText(String.valueOf(headerWoodModel.getWoodSheetLength()));
                etWidth.setText(String.valueOf(headerWoodModel.getWoodSheetWidth()));
            }
        } else
            headerWoodModel = new HeaderWoodModel();

        orderListCreationHeader = new OrderListCreation(this);
        woodTypeList = orderListCreationHeader.getWoodTypeList(headerWoodModel.getWoodType());
        pvcThicknessList = orderListCreationHeader.getPvcThickness(headerWoodModel.getPvcThickness());
        woodSheetList = orderListCreationHeader.getWoodSheetList(headerWoodModel.getWoodSheetList());

        callAllAdapters();
    }

    private void callAllAdapters() {
        callAdapterWood();
        callAdapterSheetSize();
        callAdapterThickness();
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


    @Override
    public boolean isOnline() {
        return OnlineCheck.getInstance(this).isOnline();
    }

    private void openInternetCheckingDialog() {
        ConnectionInternetDialog dialog = new ConnectionInternetDialog(this, new InternetConnectionListener() {
            @Override
            public void onInternet() {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }

            @Override
            public void onFinish() {
                APP.killApp(OrderHeaderActivity.this);
            }

            @Override
            public void OnRetry() {

                //getMyOrderBody();
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

    private void callAdapterWood() {
        List<CheckableObject> list = getListByPosition(0);

        if (list != null)
            recyclerWood.setVisibility(View.VISIBLE);

        woodTypeAdapter = new RadioAdapter(list, (v, position) -> {
            changeWoodTypeListValue(position);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerWood.setLayoutManager(layoutManager);
        recyclerWood.setAdapter(woodTypeAdapter);
        recyclerWood.scheduleLayoutAnimation();
    }


    private void callAdapterThickness() {
        List<CheckableObject> list = getListByPosition(1);

        if (list != null)
            recyclerThickness.setVisibility(View.VISIBLE);

        pvcThicknessAdapter = new RadioAdapter(list, (v, position) -> {
            changePvcThicknessListValue(position);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerThickness.setLayoutManager(layoutManager);
        recyclerThickness.setAdapter(pvcThicknessAdapter);
        recyclerThickness.scheduleLayoutAnimation();
    }

    private void callAdapterSheetSize() {
        List<CheckableObject> list = getListByPosition(2);

        if (list != null)
            recyclerSheetSize.setVisibility(View.VISIBLE);

        woodSizeAdapter = new RadioAdapter(list, (v, position) -> {
            changeWoodSheetListValue(position);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerSheetSize.setLayoutManager(layoutManager);
        recyclerSheetSize.setAdapter(woodSizeAdapter);
        recyclerSheetSize.scheduleLayoutAnimation();
    }

    private List<CheckableObject> getListByPosition(int stepPosition) {
        if (stepPosition == 0)
            return woodTypeList;
        else if (stepPosition == 1)
            return pvcThicknessList;
        else if (stepPosition == 2)
            return woodSheetList;
        else return new ArrayList<>();
    }

    private void changeWoodTypeListValue(int position) {
        for (CheckableObject co : woodTypeList) {
            co.setChecked(false);
        }

        woodTypeList.get(position).setChecked(true);
        woodTypeList.get(position).setIndex(position);
        headerWoodModel.setWoodType(woodTypeList.get(position));

        woodTypeAdapter.notifyDataSetChanged();
    }

    private void changePvcThicknessListValue(int position) {
        for (CheckableObject co : pvcThicknessList) {
            co.setChecked(false);
        }
        pvcThicknessList.get(position).setChecked(true);
        pvcThicknessList.get(position).setIndex(position);
        headerWoodModel.setPvcThickness(pvcThicknessList.get(position));

        pvcThicknessAdapter.notifyDataSetChanged();
    }


    private void changeWoodSheetListValue(int position) {
        for (CheckableObject co : woodSheetList) {
            co.setChecked(false);
        }
        woodSheetList.get(position).setChecked(true);
        woodSheetList.get(position).setIndex(position);

        linearCustomDimension.setVisibility(View.GONE);
        headerWoodModel.setWoodSheetList(woodSheetList.get(position));

        int sheetLen = 0;
        int sheetWid = 0;

        switch (position) {
            case 0:
                sheetLen = 366;
                sheetWid = 183;
                break;
            case 1:
                sheetLen = 280;
                sheetWid = 122;
                break;
            case 2:
                sheetLen = 240;
                sheetWid = 122;
                break;
            case 3:
                sheetLen = 210;
                sheetWid = 183;
                break;
            default:
                linearCustomDimension.setVisibility(View.VISIBLE);
                if (headerWoodModel.getWoodSheetLength() > 0)
                    etHeight.setText(String.valueOf(headerWoodModel.getWoodSheetLength()));
                else
                    etHeight.setText("");

                if (headerWoodModel.getWoodSheetWidth() > 0)
                    etWidth.setText(String.valueOf(headerWoodModel.getWoodSheetWidth()));
                else
                    etWidth.setText("");

                break;
        }

        headerWoodModel.setWoodSheetLength(sheetLen);
        headerWoodModel.setWoodSheetWidth(sheetWid);

        woodSizeAdapter.notifyDataSetChanged();
    }


}
