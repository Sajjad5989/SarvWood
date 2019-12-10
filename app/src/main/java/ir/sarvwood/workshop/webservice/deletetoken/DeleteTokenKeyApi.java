package ir.sarvwood.workshop.webservice.deletetoken;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.savetoken.SaveTokenKeyBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DeleteTokenKeyApi {

      @Headers({BuildConfig.contentType, BuildConfig.deviceType})
    @POST("common/deleteTokenKey")
      Call<SarvApiResponseNoList> execute(@Header(BuildConfig.userId) int userId,
                                          @Header(BuildConfig.accessToken) String accessToken,
                                          @Body DeleteTokenKeyBody deleteTokenKeyBody);


}
