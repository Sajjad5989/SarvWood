package ir.sarvwood.workshop.webservice.authenticationofcnfrmcode;

import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.sarvwoodapi.AppController;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthenticationOfCnfrmCodeController {


    public AuthenticationOfCnfrmCodeController() {
    }

    public void start(AuthenticationOfCnfrmCodeBody authenticationOfCnfrmCodeBody, IResponseListener listener)
    {
        Retrofit retrofit = AppController.getInstance().getRetrofitObject();
        AuthenticationOfCnfrmCodeApi authenticationOfCnfrmCodeApi = retrofit.create(AuthenticationOfCnfrmCodeApi.class);
        Call<ResponseBody> call = authenticationOfCnfrmCodeApi.execute(authenticationOfCnfrmCodeBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Gson gson = new Gson();
//                ResponseData<Response> Response = gson.fromJson(response.toString(), ResponseData.class);
//                if (Response.getCode() == 0) {
//                    listener.onSuccess(Response.getCode());
//                } else {
//                    listener.onFailure(Response.getMessage());
//                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                listener.onFailure(t.getMessage());
            }
        });
    }
}
