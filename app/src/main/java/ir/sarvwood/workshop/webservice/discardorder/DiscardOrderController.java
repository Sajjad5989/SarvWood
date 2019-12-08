package ir.sarvwood.workshop.webservice.discardorder;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DiscardOrderController {
    public DiscardOrderController() {

    }

    public void start(int userId, String token, DiscardOrderBody discardOrderBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        DiscardOrderApi discardOrderApi = retrofit.create(DiscardOrderApi.class);
        Call<SarvApiResponseNoList> call = discardOrderApi.execute(userId, token, discardOrderBody);
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
