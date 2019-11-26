package ir.sarvwood.workshop.webservice.authenticationofcnfrmcode;

import ir.sarvwood.workshop.BuildConfig;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthenticationOfCnfrmCodeApi {


    @Headers({BuildConfig.contentType, BuildConfig.deviceType })
    @POST( "customer/authenticationOfCnfrmCode" )
    Call<ResponseBody> execute (@Body AuthenticationOfCnfrmCodeBody authenticationOfCnfrmCodeBody);


}
