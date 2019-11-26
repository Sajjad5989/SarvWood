package ir.sarvwood.workshop.webservice.insertorder;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InsertOrderController {

    public InsertOrderController() {
    }

    public void start(int userId,String token,InsertOrderBody insertOrderBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        InsertOrderApi insertOrderApi = retrofit.create(InsertOrderApi.class);
        Call<SarvApiResponse<InsertOrderBodyReturnValue>> call = insertOrderApi.execute(userId,token,insertOrderBody);
        call.enqueue(new Callback<SarvApiResponse<InsertOrderBodyReturnValue>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<InsertOrderBodyReturnValue>> call, @NonNull Response<SarvApiResponse<InsertOrderBodyReturnValue>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<InsertOrderBodyReturnValue>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
