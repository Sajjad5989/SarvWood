package ir.sarvwood.workshop.webservice.staticpages;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetStaticPagesTextController {

    public GetStaticPagesTextController() {
    }

    public void start(int userId, String token, GetStaticPagesTextBody getStaticPagesTextBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        GetStaticPagesTextApi getMyOrdersApi = retrofit.create(GetStaticPagesTextApi.class);
        Call<SarvApiResponse<GetStaticPagesTextReturnValue>> call = getMyOrdersApi.execute(userId, token, getStaticPagesTextBody);
        call.enqueue(new Callback<SarvApiResponse<GetStaticPagesTextReturnValue>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<GetStaticPagesTextReturnValue>> call,@NonNull  Response<SarvApiResponse<GetStaticPagesTextReturnValue>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<GetStaticPagesTextReturnValue>> call,@NonNull  Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
