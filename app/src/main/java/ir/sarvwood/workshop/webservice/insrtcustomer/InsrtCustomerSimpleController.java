package ir.sarvwood.workshop.webservice.insrtcustomer;


import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.ResponseData;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InsrtCustomerSimpleController {


    public InsrtCustomerSimpleController() {
    }

    public void start(InsrtCustomerSimpleBody insrtCustomerSimpleBody, IResponseListener listener) {

        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        InsrtCustomerSimpleApi insrtCustomerSimpleApi = retrofit.create(InsrtCustomerSimpleApi.class);
        Call<SarvApiResponse<InsrtCustomerSimpleRerunValue>> call = insrtCustomerSimpleApi.execute(insrtCustomerSimpleBody);
        call.enqueue(new Callback<SarvApiResponse<InsrtCustomerSimpleRerunValue>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<InsrtCustomerSimpleRerunValue>> call,@NonNull Response<SarvApiResponse<InsrtCustomerSimpleRerunValue>> response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<InsrtCustomerSimpleRerunValue>> call,@NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

}
