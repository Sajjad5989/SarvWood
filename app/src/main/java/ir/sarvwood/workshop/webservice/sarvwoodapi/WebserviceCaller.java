package ir.sarvwood.workshop.webservice.sarvwoodapi;

import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;
import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.webservice.apibodies.AuthenticationOfCnfrmCodeBody;
import ir.sarvwood.workshop.webservice.apibodies.ChangeCustomerPasswordBody;
import ir.sarvwood.workshop.webservice.apibodies.DeleteTokenKeyBody;
import ir.sarvwood.workshop.webservice.apibodies.DiscardOrderBody;
import ir.sarvwood.workshop.webservice.apibodies.GetCustomerInfoBody;
import ir.sarvwood.workshop.webservice.apibodies.GetCustomerInfoByIdBody;
import ir.sarvwood.workshop.webservice.apibodies.GetOrderDetailsBody;
import ir.sarvwood.workshop.webservice.apibodies.GetStaticPagesTextBody;
import ir.sarvwood.workshop.webservice.apibodies.InsrtCustomerSimpleBody;
import ir.sarvwood.workshop.webservice.apibodies.InsrtOrderBody;
import ir.sarvwood.workshop.webservice.apibodies.InsrtSuggestionBody;
import ir.sarvwood.workshop.webservice.apibodies.SaveDeviceInfoBody;
import ir.sarvwood.workshop.webservice.apibodies.SaveTokenKeyBody;
import ir.sarvwood.workshop.webservice.apibodies.SendSmsOfCnfrmCodeBody;
import ir.sarvwood.workshop.webservice.apibodies.SheetSupplierBody;
import ir.sarvwood.workshop.webservice.apibodies.UpdateCustomerInfoBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebserviceCaller {

    private  ApiInterface apiInterface;

    public WebserviceCaller( ){
        apiInterface = ApiClient.getRetrofit().create( ApiInterface.class );
    }

    private void processResponseBody(Call<ResponseBody> call, IResponseListener listener ){
        call.enqueue( new Callback< ResponseBody >() {
            @Override
            public void onResponse(@NonNull Call< ResponseBody > call, @NonNull Response< ResponseBody > response ){
                try {
                    listener.onResponse( Objects.requireNonNull(response.body()).string() );
                } catch ( IOException e ) {
                    listener.onFailure( "" );
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure( @NonNull Call< ResponseBody > call, @NonNull Throwable t ){
                listener.onFailure( t.getMessage() );
            }
        } );
    }

   /*
    public void deleteTokenKey(int userId,String token ,DeleteTokenKeyBody deleteTokenKeyBody, IResponseListener listener ){
        processResponseBody( ApiInterface.deleteTokenKey( userId, token,deleteTokenKeyBody ), listener );
    }

    public void getBaseInfoOfApp( int userId,String token , IResponseListener listener ){
        processResponseBody( ApiInterface.getBaseInfoOfApp( userId, token ), listener );
    }

    public void getStaticPagesText(int userId, String token, GetStaticPagesTextBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.getStaticPagesText( userId, token,body ), listener );
    }
    public void insrtSuggestion(int userId, String token, InsrtSuggestionBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.insrtSuggestion( userId, token,body ), listener );
    }
    public void saveDeviceInfo(int userId, String token, SaveDeviceInfoBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.saveDeviceInfo( userId, token,body ), listener );
    }
    public void saveTokenKey(int userId, String token, SaveTokenKeyBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.saveTokenKey( userId, token,body ), listener );
    }
    public void authenticationOfCnfrmCode(AuthenticationOfCnfrmCodeBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.authenticationOfCnfrmCode( body ), listener );
    }
    public void changeCustomerPassword(ChangeCustomerPasswordBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.changeCustomerPassword( body ), listener );
    }
    public void getCustomerInfo(GetCustomerInfoBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.getCustomerInfo( body ), listener );
    }
    public void getCustomerInfoById(int userId, String token, GetCustomerInfoByIdBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.getCustomerInfoById(userId,token, body ), listener );
    }
    public void getMyOrders(int userId, String token,GetCustomerInfoByIdBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.getMyOrders(userId,token, body ), listener );
    }
    public void insrtCustomerSimple( InsrtCustomerSimpleBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.insrtCustomerSimple( body ), listener );
    }
    public void sendSmsOfCnfrmCode(SendSmsOfCnfrmCodeBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.sendSmsOfCnfrmCode( body ), listener );
    }
    public void updateCustomerInfo(int userId, String token, UpdateCustomerInfoBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.updateCustomerInfo(userId,token, body ), listener );
    }
    public void discardOrder(int userId, String token, DiscardOrderBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.discardOrder( userId,token,body ), listener );
    }
    public void getOrderDetails(int userId, String token, GetOrderDetailsBody body , IResponseListener listener ){
        processResponseBody( ApiInterface.discardOrder(userId,token, body ), listener );
    }
    public void getOrderDiscardOptions(int userId, String token, IResponseListener listener ){
        processResponseBody( ApiInterface.discardOrder( userId,token ), listener );
    }
    public void getOrderStates(int userId, String token, IResponseListener listener ){
        processResponseBody( ApiInterface.getOrderStates( userId,token ), listener );
    }
    public void insrtOrder(int userId, String token, InsrtOrderBody body, IResponseListener listener ){
        processResponseBody( ApiInterface.insrtOrder( userId,token ,body), listener );
    }
    public void setSheetSupplier(int userId, String token, SheetSupplierBody body, IResponseListener listener ){
        processResponseBody( ApiInterface.setSheetSupplier( userId,token ,body), listener );
    }
    public void updateOrder(int userId, String token, InsrtOrderBody body, IResponseListener listener ){
        processResponseBody( ApiInterface.updateOrder( userId,token ,body), listener );
    }
    */




}
