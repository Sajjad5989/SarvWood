package ir.sarvwood.workshop.webservice.orderdetail;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.myorders.MyOrderReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.SarvApiResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetOrderDetailsApi {


    @Headers({BuildConfig.contentType, BuildConfig.deviceType})
    @POST("order/getOrderDetails")
    Call<SarvApiResponse<GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue>>> execute(@Header(BuildConfig.userId) int userId,
                                                      @Header(BuildConfig.accessToken) String accessToken,
                                                      @Body GetOrderDetailsBody getOrderDetailsBody);
}
