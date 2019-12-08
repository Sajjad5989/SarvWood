package ir.sarvwood.workshop.webservice.discardoption;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DiscardOptionApi {


    @Headers({BuildConfig.contentType, BuildConfig.deviceType})
    @POST("order/getOrderDiscardOptions")
    Call<SarvApiResponse<DiscardOptionReturnValue>> execute(@Header(BuildConfig.userId) int userId,
                                                            @Header(BuildConfig.accessToken) String accessToken);


}
