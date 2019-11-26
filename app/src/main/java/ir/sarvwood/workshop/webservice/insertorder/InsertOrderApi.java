package ir.sarvwood.workshop.webservice.insertorder;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InsertOrderApi {

    @Headers({BuildConfig.contentType, BuildConfig.deviceType})
    @POST("order/insrtOrder")
    Call<SarvApiResponse<InsertOrderBodyReturnValue>> execute(
            @Header(BuildConfig.userId) int userId,
            @Header(BuildConfig.accessToken) String accessToken,
            @Body InsertOrderBody insertOrderBody);
}
