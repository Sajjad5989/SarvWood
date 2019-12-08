package ir.sarvwood.workshop.webservice.discardoption;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DiscardOptionController {

    public DiscardOptionController() {
    }

    public void start(int userId, String token, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        DiscardOptionApi discardOptionApi = retrofit.create(DiscardOptionApi.class);
        Call<SarvApiResponse<DiscardOptionReturnValue>> call = discardOptionApi.execute(userId, token);
        call.enqueue(new Callback<SarvApiResponse<DiscardOptionReturnValue>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<DiscardOptionReturnValue>> call, @NonNull Response<SarvApiResponse<DiscardOptionReturnValue>> response) {
                listener.onSuccess(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<DiscardOptionReturnValue>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

}
