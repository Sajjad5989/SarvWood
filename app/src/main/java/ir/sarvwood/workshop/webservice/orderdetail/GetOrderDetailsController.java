package ir.sarvwood.workshop.webservice.orderdetail;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetOrderDetailsController {

    public GetOrderDetailsController() {
    }

    public void start(int userId, String token, GetOrderDetailsBody getOrderDetailsBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        GetOrderDetailsApi getOrderDetailsApi = retrofit.create(GetOrderDetailsApi.class);
        Call<SarvApiResponse<GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue>>>
                call = getOrderDetailsApi.execute(userId, token, getOrderDetailsBody);
        call.enqueue(new Callback<SarvApiResponse<GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue>>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue>>> call,
                                   @NonNull Response<SarvApiResponse<GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue>>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue>>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
