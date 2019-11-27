package ir.sarvwood.workshop.webservice.getcustomerinfo;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetCustomerInfoController {

    public GetCustomerInfoController() {
    }

    public void start(GetCustomerInfoBody getCustomerInfoBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        GetCustomerInfoApi getCustomerInfoApi = retrofit.create(GetCustomerInfoApi.class);
        Call<SarvApiResponse<GetCustomerInfoReturnValue>> call = getCustomerInfoApi.execute(getCustomerInfoBody);
        call.enqueue(new Callback<SarvApiResponse<GetCustomerInfoReturnValue>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<GetCustomerInfoReturnValue>> call, @NonNull Response<SarvApiResponse<GetCustomerInfoReturnValue>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<GetCustomerInfoReturnValue>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

}
