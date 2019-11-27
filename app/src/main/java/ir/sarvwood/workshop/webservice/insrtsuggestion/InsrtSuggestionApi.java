package ir.sarvwood.workshop.webservice.insrtsuggestion;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InsrtSuggestionApi {


    @Headers({BuildConfig.contentType, BuildConfig.deviceType})
    @POST("common/insrtSuggestion")
    Call<SarvApiResponse<InsrtSuggestionReturnValue>> execute(@Header(BuildConfig.userId) int userId,
                                                              @Header(BuildConfig.accessToken) String accessToken,
                                                              @Body InsrtSuggestionBody insrtSuggestionBody);
}
