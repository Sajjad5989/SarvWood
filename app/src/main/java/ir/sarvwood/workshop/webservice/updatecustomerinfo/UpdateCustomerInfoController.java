package ir.sarvwood.workshop.webservice.updatecustomerinfo;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateCustomerInfoController {
    public UpdateCustomerInfoController() {
    }

    public void start(int userId, String token, UpdateCustomerInfoBody updateCustomerInfoBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        UpdateCustomerInfoApi updateCustomerInfoApi = retrofit.create(UpdateCustomerInfoApi.class);
        Call<SarvApiResponse<UpdateCustomerInfoReturnValue>> call = updateCustomerInfoApi.execute(userId, token, updateCustomerInfoBody);
        call.enqueue(new Callback<SarvApiResponse<UpdateCustomerInfoReturnValue>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<UpdateCustomerInfoReturnValue>> call, @NonNull Response<SarvApiResponse<UpdateCustomerInfoReturnValue>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<UpdateCustomerInfoReturnValue>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }
}
