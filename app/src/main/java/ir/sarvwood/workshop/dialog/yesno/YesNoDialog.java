package ir.sarvwood.workshop.dialog.yesno;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;

public class YesNoDialog extends Dialog {
    private final ResponseListener responseListener;
    @BindView(R.id.tv_message)
    protected AppCompatTextView tvMessage;
    @BindView(R.id.tv_title)
    protected AppCompatTextView tvTitle;
    private Context context;
    private String title;
    private String message;

    public YesNoDialog(@NonNull Context context, String title, String message, ResponseListener responseListener) {

        super(context);
        this.context = context;
        this.responseListener = responseListener;
        this.title=title;
        this.message=message;
    }


    @OnClick(R.id.tv_yes)
    void exitButtonClick() {
        responseListener.OnAccept(true);
        dismiss();
    }

    @OnClick(R.id.tv_no)
    void rejectButtonClick() {
        responseListener.OnAccept(false);
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_yes_no);

        ButterKnife.bind(this);

        tvTitle.setText(title);
        tvMessage.setText(Html.fromHtml(message));
    }

}
