package ir.sarvwood.workshop.webservice.savedeviceinfo;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponseNoList;
import ir.sarvwood.workshop.webservice.staticpages.GetStaticPagesTextBody;
import ir.sarvwood.workshop.webservice.staticpages.GetStaticPagesTextReturnValue;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SaveDeviceInfoApi {


    @Headers({BuildConfig.contentType, BuildConfig.deviceType})
    @POST("common/saveDeviceInfo")
    Call<SarvApiResponseNoList> execute(@Header(BuildConfig.userId) int userId,
                                        @Header(BuildConfig.accessToken) String accessToken,
                                        @Body SaveDeviceInfoBody saveDeviceInfoBody);

}
