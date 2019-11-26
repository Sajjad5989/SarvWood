package ir.sarvwood.workshop.webservice.insrtcustomer;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface InsrtCustomerSimpleApi {

    @Headers({BuildConfig.contentType, BuildConfig.deviceType})
    @POST("customer/insrtCustomerSimple")
    Call<SarvApiResponse<InsrtCustomerSimpleRerunValue>> execute(@Body InsrtCustomerSimpleBody insrtCustomerSimpleBody);

}
