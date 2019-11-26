package ir.sarvwood.workshop.webservice.myorders;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetMyOrdersController {


    public GetMyOrdersController() {
    }

    public void start(int userId,String token,GetMyOrderBody getMyOrderBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        GetMyOrdersApi getMyOrdersApi = retrofit.create(GetMyOrdersApi.class);
        Call<SarvApiResponse<MyOrderReturnValue>> call = getMyOrdersApi.execute(userId,token,getMyOrderBody);

        call.enqueue(new Callback<SarvApiResponse<MyOrderReturnValue>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<MyOrderReturnValue>> call,@NonNull Response<SarvApiResponse<MyOrderReturnValue>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<MyOrderReturnValue>> call,@NonNull  Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

}
