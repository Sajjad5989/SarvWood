package ir.sarvwood.workshop.webservice.getcustomerinfo;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetCustomerInfoApi {

    @Headers({BuildConfig.contentType, BuildConfig.deviceType })
    @POST("customer/getCustomerInfo")
    Call<SarvApiResponse<GetCustomerInfoReturnValue>> execute(@Body GetCustomerInfoBody getCustomerInfoBody);


}
