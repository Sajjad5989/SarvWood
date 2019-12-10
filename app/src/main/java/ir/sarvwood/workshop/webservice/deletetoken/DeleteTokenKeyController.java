package ir.sarvwood.workshop.webservice.deletetoken;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.savetoken.SaveTokenKeyApi;
import ir.sarvwood.workshop.webservice.savetoken.SaveTokenKeyBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeleteTokenKeyController {
    public DeleteTokenKeyController() {
    }

    public void start(int userId, String token, DeleteTokenKeyBody keyBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        DeleteTokenKeyApi deleteTokenKeyApi = retrofit.create(DeleteTokenKeyApi.class);
        Call<SarvApiResponseNoList> call = deleteTokenKeyApi.execute(userId, token, keyBody);
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
