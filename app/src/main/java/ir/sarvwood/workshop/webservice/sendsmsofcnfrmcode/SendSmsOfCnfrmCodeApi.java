package ir.sarvwood.workshop.webservice.sendsmsofcnfrmcode;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendSmsOfCnfrmCodeApi {


    @Headers({BuildConfig.contentType, BuildConfig.deviceType })
    @POST( "customer/sendSmsOfCnfrmCode" )
    Call<SarvApiResponseNoList> execute(@Body SendSmsOfCnfrmCodeBody sendSmsOfCnfrmCodeBody );

}
