package ir.sarvwood.workshop.webservice.sendsmsofcnfrmcode;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SendSmsOfCnfrmCodeController {


    public SendSmsOfCnfrmCodeController() {
    }

    public void start(SendSmsOfCnfrmCodeBody sendSmsOfCnfrmCodeBody, IResponseListener listener) {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        SendSmsOfCnfrmCodeApi sendSmsOfCnfrmCodeApi = retrofit.create(SendSmsOfCnfrmCodeApi.class);
        Call<SarvApiResponseNoList> call = sendSmsOfCnfrmCodeApi.execute(sendSmsOfCnfrmCodeBody);
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
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                Gson gson = new Gson();
//                ResponseData<Response> Response = gson.fromJson(response.toString(), ResponseData.class);
//                if (Response.getCode() == 0) {
//                    // success
//                    listener.onSuccess(null);//Response.getData()
//                } else {
//                    // error
//                    listener.onFailure(Response.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                listener.onFailure(t.getMessage());
//            }
//        });

    }
}
