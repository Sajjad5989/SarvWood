package ir.sarvwood.workshop.dialog.update;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;

public class UpdateDialog extends Dialog {

    private ChooseRequestListener chooseRequestListener;
    private String updateMessage;
    @BindView(R.id.tv_message)
    protected AppCompatTextView tvMessage;


    public UpdateDialog(@NonNull Context context, ChooseRequestListener chooseRequestListener, String updateMessage) {

        super(context);
        this.chooseRequestListener = chooseRequestListener;
        this.updateMessage = updateMessage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_update);

        ButterKnife.bind(this);
        tvMessage.setText(updateMessage);
    }

    @OnClick(R.id.tv_yes)
    void acceptButtonClick() {
        chooseRequestListener.onChooseRequest(true);
        dismiss();
    }

    @OnClick(R.id.tv_no)
    void rejectButtonClick() {
        chooseRequestListener.onChooseRequest(false);
        dismiss();
    }


}
