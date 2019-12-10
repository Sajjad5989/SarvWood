package ir.sarvwood.workshop.webservice.setsupplier;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.myorders.GetMyOrdersApi;
import ir.sarvwood.workshop.webservice.myorders.MyOrderReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SetSheetSupplierController {
    public SetSheetSupplierController() {
    }

    public void start(int userId, String token, SheetSupplierBody supplierBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        SetSheetSupplierApi supplierApi = retrofit.create(SetSheetSupplierApi.class);
        Call<SarvApiResponseNoList> call = supplierApi.execute(userId, token, supplierBody);
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
