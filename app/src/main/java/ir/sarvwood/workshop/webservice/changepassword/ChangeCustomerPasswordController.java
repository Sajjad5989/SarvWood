package ir.sarvwood.workshop.webservice.changepassword;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangeCustomerPasswordController {
    public ChangeCustomerPasswordController() {

    }

    public void start(int userId, String token, ChangeCustomerPasswordBody changeCustomerPasswordBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        ChangeCustomerPasswordApi changeCustomerPasswordApi = retrofit.create(ChangeCustomerPasswordApi.class);
        Call<SarvApiResponseNoList> call = changeCustomerPasswordApi.execute(userId, token, changeCustomerPasswordBody);
        call.enqueue(new Callback<SarvApiResponseNoList>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponseNoList> call, @NonNull Response<SarvApiResponseNoList> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponseNoList> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }
}