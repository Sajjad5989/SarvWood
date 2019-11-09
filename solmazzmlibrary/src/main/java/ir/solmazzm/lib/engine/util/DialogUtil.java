package ir.solmazzm.lib.engine.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.view.Display;

import java.util.Objects;

import androidx.constraintlayout.widget.ConstraintLayout;
import ir.solmazzm.lib.engine.R;


public class DialogUtil {
    public static void showDialog(Activity activity, Dialog dialog, boolean cancelable, boolean wrapContent) {
        dialog.setCancelable(cancelable);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(activity.getDrawable(R.drawable.dialog_bg_no_padding));

        dialog.show();
        Display display = Objects.requireNonNull(activity).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width = (int) ((width) * 0.9);
        int height = (wrapContent) ? ConstraintLayout.LayoutParams.WRAP_CONTENT : ConstraintLayout.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setLayout(width, height);
    }
}
