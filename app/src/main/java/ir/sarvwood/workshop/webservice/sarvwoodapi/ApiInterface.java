package ir.sarvwood.workshop.webservice.sarvwoodapi;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.authenticationofcnfrmcode.AuthenticationOfCnfrmCodeBody;
import ir.sarvwood.workshop.webservice.apibodies.ChangeCustomerPasswordBody;
import ir.sarvwood.workshop.webservice.apibodies.DeleteTokenKeyBody;
import ir.sarvwood.workshop.webservice.apibodies.DiscardOrderBody;
import ir.sarvwood.workshop.webservice.apibodies.GetCustomerInfoBody;
import ir.sarvwood.workshop.webservice.apibodies.GetCustomerInfoByIdBody;
import ir.sarvwood.workshop.webservice.apibodies.GetOrderDetailsBody;
import ir.sarvwood.workshop.webservice.apibodies.GetStaticPagesTextBody;
import ir.sarvwood.workshop.webservice.apibodies.InsrtOrderBody;
import ir.sarvwood.workshop.webservice.apibodies.InsrtSuggestionBody;
import ir.sarvwood.workshop.webservice.apibodies.SaveDeviceInfoBody;
import ir.sarvwood.workshop.webservice.apibodies.SaveTokenKeyBody;
import ir.sarvwood.workshop.webservice.apibodies.SheetSupplierBody;
import ir.sarvwood.workshop.webservice.apibodies.UpdateCustomerInfoBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {


//    @Headers( { "Content-Type: application/json" } )
//    @POST( "common/deleteTokenKey" )
//    Call< ResponseBody > deleteTokenKey( @Header( BuildConfig.userId ) int userId,
//                                         @Header( BuildConfig.accessToken ) String accessToken,
//                                         @Body DeleteTokenKeyBody deleteTokenKeyBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "common/getBaseInfoOfApp" )
//    Call< ResponseBody > getBaseInfoOfApp( @Header( BuildConfig.userId ) int userId,
//                                           @Header( BuildConfig.accessToken ) String accessToken );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "common/getStaticPagesText" )
//    Call< ResponseBody > getStaticPagesText( @Header( BuildConfig.userId ) int userId,
//                                             @Header( BuildConfig.accessToken ) String accessToken,
//                                             @Body GetStaticPagesTextBody getStaticPagesTextBody );
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "common/insrtSuggestion" )
//    Call< ResponseBody > insrtSuggestion( @Header( BuildConfig.userId ) int userId,
//                                          @Header( BuildConfig.accessToken ) String accessToken,
//                                          @Body InsrtSuggestionBody insrtSuggestionBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "common/saveDeviceInfo" )
//    Call< ResponseBody > saveDeviceInfo( @Header( BuildConfig.userId ) int userId,
//                                         @Header( BuildConfig.accessToken ) String accessToken,
//                                         @Body SaveDeviceInfoBody saveDeviceInfoBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "common/saveTokenKey" )
//    Call< ResponseBody > saveTokenKey( @Header( BuildConfig.userId ) int userId,
//                                       @Header( BuildConfig.accessToken ) String accessToken,
//                                       @Body SaveTokenKeyBody saveTokenKeyBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "customer/authenticationOfCnfrmCode" )
//    Call< ResponseBody > authenticationOfCnfrmCode( @Body AuthenticationOfCnfrmCodeBody authenticationOfCnfrmCodeBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "customer/changeCustomerPassword" )
//    Call< ResponseBody > changeCustomerPassword( @Header( BuildConfig.userId ) int userId,
//                                                 @Header( BuildConfig.accessToken ) String accessToken,
//                                                 @Body ChangeCustomerPasswordBody changeCustomerPasswordBody );
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "customer/getCustomerInfoById" )
//    Call< ResponseBody > getCustomerInfoById( @Header( BuildConfig.userId ) int userId,
//                                              @Header( BuildConfig.accessToken ) String accessToken,
//                                              @Body GetCustomerInfoByIdBody getCustomerInfoByIdBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "customer/getMyOrders" )
//    Call< ResponseBody > getMyOrders( @Header( BuildConfig.userId ) int userId,
//                                      @Header( BuildConfig.accessToken ) String accessToken,
//                                      @Body GetCustomerInfoByIdBody getCustomerInfoByIdBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "customer/updateCustomerInfo" )
//    Call< ResponseBody > updateCustomerInfo( @Header( BuildConfig.userId ) int userId,
//                                             @Header( BuildConfig.accessToken ) String accessToken,
//                                             @Body UpdateCustomerInfoBody updateCustomerInfoBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "order/discardOrder" )
//    Call< ResponseBody > discardOrder( @Header( BuildConfig.userId ) int userId,
//                                       @Header( BuildConfig.accessToken ) String accessToken,
//                                       @Body DiscardOrderBody discardOrderBody );
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "order/getOrderDetails" )
//    Call< ResponseBody > getOrderDetails( @Header( BuildConfig.userId ) int userId,
//                                          @Header( BuildConfig.accessToken ) String accessToken,
//                                          @Body GetOrderDetailsBody GetOrderDetailsBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "order/getOrderDiscardOptions" )
//    Call< ResponseBody > getOrderDiscardOptions( @Header( BuildConfig.userId ) int userId,
//                                                 @Header( BuildConfig.accessToken ) String accessToken );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "order/getOrderStates" )
//    Call< ResponseBody > getOrderStates( @Header( BuildConfig.userId ) int userId,
//                                         @Header( BuildConfig.accessToken ) String accessToken );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "order/insrtOrder" )
//    Call< ResponseBody > insrtOrder( @Header( BuildConfig.userId ) int userId,
//                                     @Header( BuildConfig.accessToken ) String accessToken,
//                                     @Body InsrtOrderBody insrtOrderBody );
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "order/setSheetSupplier" )
//    Call< ResponseBody > setSheetSupplier( @Header( BuildConfig.userId ) int userId,
//                                           @Header( BuildConfig.accessToken ) String accessToken,
//                                           @Body SheetSupplierBody sheetSupplierBody );
//
//
//    @Headers( { "Content-Type: application/json" } )
//    @POST( "order/updateOrder" )
//    Call< ResponseBody > updateOrder( @Header( BuildConfig.userId ) int userId,
//                                      @Header( BuildConfig.accessToken ) String accessToken,
//                                      @Body InsrtOrderBody insrtOrderBody );
//

}
