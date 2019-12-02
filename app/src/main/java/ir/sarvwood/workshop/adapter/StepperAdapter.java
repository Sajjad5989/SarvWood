package ir.sarvwood.workshop.adapter;

import android.content.Context;
import android.os.Bundle;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.fragment.OrderFragment;
import ir.sarvwood.workshop.utils.APP;

public class StepperAdapter extends AbstractFragmentStepAdapter {

    public StepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {

        final OrderFragment step = new OrderFragment();
        Bundle b1 = new Bundle();
        b1.putInt("positionNumber", position);
        step.setArguments(b1);
        return step;

    }

    @Override
    public int getCount() {
        return 9;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {

        String[] subTitles = APP.currentActivity.getResources().getStringArray(R.array.step_subtitle);
        StepViewModel.Builder builder = new StepViewModel.Builder(context)
                .setTitle(getCurrentStep(position))
                .setSubtitle(subTitles[position])
                ;

        switch (position) {
            case 0:
                builder
                        .setEndButtonLabel("مرحله بعد")
                        .setBackButtonLabel("انصراف")
                        .setNextButtonEndDrawableResId(R.drawable.ic_arrow_left)
                        .setBackButtonStartDrawableResId(StepViewModel.NULL_DRAWABLE);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                builder
                        .setEndButtonLabel("مرحله بعد")
                        .setBackButtonLabel("مرحله قبل")
                        .setNextButtonEndDrawableResId(R.drawable.ic_arrow_left)
                        .setBackButtonStartDrawableResId(R.drawable.ic_arrow_right);
                break;
            case 8:
                builder
                        .setEndButtonLabel("ثبت برش")
                        .setBackButtonLabel("مرحله قبل")
                        .setNextButtonEndDrawableResId(R.drawable.ic_arrow_left)
                        .setBackButtonStartDrawableResId(R.drawable.ic_arrow_right);
                break;
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
        return builder.create();
    }

    private String getCurrentStep(int step) {
        String numberStr;
        switch (step) {
            case 0:
                numberStr = "اول";
                break;
            case 1:
                numberStr = "دوم";
                break;
            case 2:
                numberStr = "سوم";
                break;
            case 3:
                numberStr = "چهارم";
                break;
            case 4:
                numberStr = "پنجم";
                break;
            case 5:
                numberStr = "ششم";
                break;
            case 6:
                numberStr = "هفتم";
                break;
            case 7:
                numberStr = "هشتم";
                break;
            default:
                numberStr = "نهم";
                break;
        }

        numberStr = "مرحله " + numberStr;
        return numberStr;
    }

}
