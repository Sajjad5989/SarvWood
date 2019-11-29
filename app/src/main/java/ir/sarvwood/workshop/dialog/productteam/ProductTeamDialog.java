package ir.sarvwood.workshop.dialog.productteam;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import ir.sarvwood.workshop.R;

public class ProductTeamDialog extends Dialog {

    public ProductTeamDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_product_team);
        onClickSetting();
    }

    private void onClickSetting() {
        AppCompatTextView tvClose = findViewById(R.id.button_close_dialog);
        tvClose.setOnClickListener(view -> dismiss());
    }

}
