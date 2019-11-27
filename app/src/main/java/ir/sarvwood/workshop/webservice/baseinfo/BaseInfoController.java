package ir.sarvwood.workshop.webservice.baseinfo;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BaseInfoController {
    public BaseInfoController() {
    }

    public void start(int userId, String token, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        BaseInfoApi baseInfoApi = retrofit.create(BaseInfoApi.class);
        Call<SarvApiResponse<BaseInfoReturnValue>> call = baseInfoApi.execute(userId, token);
        call.enqueue(new Callback<SarvApiResponse<BaseInfoReturnValue>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<BaseInfoReturnValue>> call, @NonNull Response<SarvApiResponse<BaseInfoReturnValue>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<BaseInfoReturnValue>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }
}
