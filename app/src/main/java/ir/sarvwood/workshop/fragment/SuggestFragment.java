package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;
import ir.sarvwood.workshop.webservice.insrtsuggestion.InsrtSuggestionBody;
import ir.sarvwood.workshop.webservice.insrtsuggestion.InsrtSuggestionController;
import ir.sarvwood.workshop.webservice.insrtsuggestion.InsrtSuggestionReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;

public class SuggestFragment extends Fragment {


    @BindView(R.id.tv_suggest_text)
    protected AppCompatTextView tvSuggestText;

    @BindView(R.id.et_suggest_text)
    protected AppCompatEditText etSuggestText;
    @BindView(R.id.btn_1)
    protected AppCompatButton btn1;
    @BindView(R.id.btn_2)
    protected AppCompatButton btn2;
    @BindView(R.id.btn_3)
    protected AppCompatButton btn3;
    @BindView(R.id.btn_4)
    protected AppCompatButton btn4;
    @BindView(R.id.btn_5)
    protected AppCompatButton btn5;
    private int type = 0;

    public static SuggestFragment newInstance() {
        return new SuggestFragment();
    }

    @OnClick(R.id.btn_register)
    void register() {
        if (!checkValidity())
            return;

        insertSuggest();

    }

    private boolean checkValidity() {
        if (type == 0) {
            APP.customToast(getString(R.string.text_suggest_kind));
            return false;
        }

        if ("".equals(etSuggestText.getText().toString())) {
            APP.customToast(getString(R.string.text_suggest_input));
            return false;
        }

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_suggest, container, false);

        APP.currentActivity = getActivity();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        btn1.setOnClickListener(this::onClick);
        btn2.setOnClickListener(this::onClick);
        btn3.setOnClickListener(this::onClick);
        btn4.setOnClickListener(this::onClick);
        btn5.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_1:
                type = 1;
                tvSuggestText.setText("لطفا نظر خود را وارد نمایید");
                break;

            case R.id.btn_2:
                type = 2;
                tvSuggestText.setText("لطفا انتقاد خود را وارد نمایید");
                break;

            case R.id.btn_3:
                type = 3;
                tvSuggestText.setText("لطفا پیشنهاد خود را وارد نمایید");
                break;
            case R.id.btn_4:
                type = 4;
                tvSuggestText.setText("لطفا نیازمندی های خود را وارد نمایید");
                break;
            case R.id.btn_5:
                type = 5;
                tvSuggestText.setText("لطفا  خطای مشاهده شده را وارد نمایید");
                break;

            default:
                break;
        }

    }

    private void insertSuggest() {

        int userId = GeneralPreferences.getInstance(APP.currentActivity).getCustomerId();
        String token = GeneralPreferences.getInstance(APP.currentActivity).getToken();
        InsrtSuggestionBody insrtSuggestionBody = InsrtSuggestionBody.builder().
                reporterId(userId).
                type(type).
                comments(etSuggestText.getText().toString())
                .build();
        InsrtSuggestionController insrtSuggestionController = new InsrtSuggestionController();
        insrtSuggestionController.start(userId, token, insrtSuggestionBody,
                new IResponseListener<SarvApiResponse<InsrtSuggestionReturnValue>>() {
                    @Override
                    public void onSuccess(SarvApiResponse<InsrtSuggestionReturnValue> response) {
                        if (response.getCode() == 0 && "success".equals(response.getStatus())) {
                            APP.customToast(getString(R.string.text_successful));
                            APP.currentActivity.finish();
                        } else {
                            APP.customToast(response.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        APP.customToast(error);

                    }
                });

    }

}
