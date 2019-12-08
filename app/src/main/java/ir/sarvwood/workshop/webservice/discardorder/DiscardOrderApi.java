package ir.sarvwood.workshop.webservice.discardorder;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DiscardOrderApi {


    @Headers({BuildConfig.contentType, BuildConfig.deviceType})
    @POST("order/discardOrder")
    Call<SarvApiResponseNoList> execute(@Header(BuildConfig.userId) int userId,
                                        @Header(BuildConfig.accessToken) String accessToken,
                                        @Body DiscardOrderBody discardOrderBody);


}
