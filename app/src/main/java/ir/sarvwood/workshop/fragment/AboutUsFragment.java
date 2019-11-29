package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.sarvwood.workshop.webservice.staticpages.GetStaticPagesTextBody;
import ir.sarvwood.workshop.webservice.staticpages.GetStaticPagesTextController;
import ir.sarvwood.workshop.webservice.staticpages.GetStaticPagesTextReturnValue;

public class AboutUsFragment extends Fragment {

    private static int currentType;
    @BindView(R.id.tv_about_us)
    protected AppCompatTextView tvAboutUs;


    public static AboutUsFragment newInstance(int type) {

        currentType = type;
        return new AboutUsFragment();
    }

    @BindView(R.id.image_call_us)
    protected AppCompatImageView imageCallUs;

   private void call() {
        String phone = GeneralPreferences.getInstance(APP.currentActivity).getBaseInfo().getSupportPhone();
        if (!"".equals(phone)) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone.trim()));
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);

        ButterKnife.bind(this, v);
        imageCallUs.setVisibility(currentType==2?View.VISIBLE:View.GONE);
        imageCallUs.setOnClickListener(view -> call());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        APP.currentActivity = getActivity();
        getAboutUsText();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getAboutUsText() {

        int userId = GeneralPreferences.getInstance(APP.currentActivity).getCustomerId();
        String token = GeneralPreferences.getInstance(APP.currentActivity).getToken();

        GetStaticPagesTextBody getStaticPagesTextBody = GetStaticPagesTextBody.builder().type(currentType).build();

        GetStaticPagesTextController getStaticPagesTextController = new GetStaticPagesTextController();
        getStaticPagesTextController.start(userId, token, getStaticPagesTextBody,
                new IResponseListener<SarvApiResponse<GetStaticPagesTextReturnValue>>() {
                    @Override
                    public void onSuccess(SarvApiResponse<GetStaticPagesTextReturnValue> response) {
                        if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                            tvAboutUs.setText(Html.fromHtml(response.getData().get(0).getBody()));
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error);
                    }
                });
    }


}
