package ir.sarvwood.workshop.webservice.insrtsuggestion;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InsrtSuggestionController {
    public InsrtSuggestionController() {
    }

    public void start(int userId, String token, InsrtSuggestionBody insrtSuggestionBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        InsrtSuggestionApi insrtSuggestionApi = retrofit.create(InsrtSuggestionApi.class);
        Call<SarvApiResponse<InsrtSuggestionReturnValue>> call = insrtSuggestionApi.execute(userId, token, insrtSuggestionBody);
        call.enqueue(new Callback<SarvApiResponse<InsrtSuggestionReturnValue>>() {
            @Override
            public void onResponse(@NonNull Call<SarvApiResponse<InsrtSuggestionReturnValue>> call, @NonNull Response<SarvApiResponse<InsrtSuggestionReturnValue>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SarvApiResponse<InsrtSuggestionReturnValue>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
