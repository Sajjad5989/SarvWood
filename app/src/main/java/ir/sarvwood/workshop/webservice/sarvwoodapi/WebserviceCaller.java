package ir.sarvwood.workshop.webservice.sarvwoodapi;

import com.google.gson.Gson;

import java.io.IOException;

import ir.sarvwood.workshop.interfaces.IResponseListener;
import ir.sarvwood.workshop.model.ResponseData;
import ir.sarvwood.workshop.webservice.apibodies.AuthenticationOfCnfrmCodeBody;
import ir.sarvwood.workshop.webservice.apibodies.DeleteTokenKeyBody;
import ir.sarvwood.workshop.webservice.apibodies.DiscardOrderBody;
import ir.sarvwood.workshop.webservice.apibodies.GetCustomerInfoBody;
import ir.sarvwood.workshop.webservice.apibodies.GetCustomerInfoByIdBody;
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

    private ApiInterface apiInterface;

    public WebserviceCaller( ){
        apiInterface = ApiClient.getRetrofit().create( ApiInterface.class );
    }

    private void processResponseBody( Call< ResponseBody > call, IResponseListener listener ){
        call.enqueue( new Callback< ResponseBody >() {
            @Override
            public void onResponse( Call< ResponseBody > call, Response< ResponseBody > response ){
                try {
                    Gson gson = new Gson();
                    ResponseData responseData = gson.fromJson( response.body().string(), ResponseData.class );
                    if ( ( responseData.getStatus().equals( "success" ) ) && ( responseData.getCode() > 0 ) ) {
                        listener.onSuccess( responseData.getData() );
                    } else {
                        listener.onFailure( responseData.getMessage() );
                    }
                } catch ( IOException e ) {
                    listener.onFailure( e.getMessage() );
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure( Call< ResponseBody > call, Throwable t ){
                listener.onFailure( t.getMessage() );
            }
        } );
    }


    public void deleteTokenKey( int userId, String token, DeleteTokenKeyBody deleteTokenKeyBody, IResponseListener listener ){
        processResponseBody( apiInterface.deleteTokenKey( userId, token, deleteTokenKeyBody ), listener );
    }

    public void getBaseInfoOfApp( int userId, String token, IResponseListener listener ){
        processResponseBody( apiInterface.getBaseInfoOfApp( userId, token ), listener );
    }

    public void getStaticPagesText( int userId, String token, GetStaticPagesTextBody body, IResponseListener listener ){
        processResponseBody( apiInterface.getStaticPagesText( userId, token, body ), listener );
    }

    public void insrtSuggestion( int userId, String token, InsrtSuggestionBody body, IResponseListener listener ){
        processResponseBody( apiInterface.insrtSuggestion( userId, token, body ), listener );
    }

    public void saveDeviceInfo( int userId, String token, SaveDeviceInfoBody body, IResponseListener listener ){
        processResponseBody( apiInterface.saveDeviceInfo( userId, token, body ), listener );
    }

    public void saveTokenKey( int userId, String token, SaveTokenKeyBody body, IResponseListener listener ){
        processResponseBody( apiInterface.saveTokenKey( userId, token, body ), listener );
    }

    public void authenticationOfCnfrmCode( AuthenticationOfCnfrmCodeBody body, IResponseListener listener ){
        processResponseBody( apiInterface.authenticationOfCnfrmCode( body ), listener );
    }

    /*public void changeCustomerPassword( ChangeCustomerPasswordBody body, IResponseListener listener ){
        processResponseBody( apiInterface.changeCustomerPassword( body ), listener );
    }*/

    public void getCustomerInfo( GetCustomerInfoBody body, IResponseListener listener ){
        processResponseBody( apiInterface.getCustomerInfo( body ), listener );
    }

    public void getCustomerInfoById( int userId, String token, GetCustomerInfoByIdBody body, IResponseListener listener ){
        processResponseBody( apiInterface.getCustomerInfoById( userId, token, body ), listener );
    }

    public void getMyOrders( int userId, String token, GetCustomerInfoByIdBody body, IResponseListener listener ){
        processResponseBody( apiInterface.getMyOrders( userId, token, body ), listener );
    }

    public void insrtCustomerSimple( InsrtCustomerSimpleBody body, IResponseListener listener ){
        processResponseBody( apiInterface.insrtCustomerSimple( body ), listener );
    }

    public void sendSmsOfCnfrmCode( SendSmsOfCnfrmCodeBody body, IResponseListener listener ){
        processResponseBody( apiInterface.sendSmsOfCnfrmCode( body ), listener );
    }

    public void updateCustomerInfo( int userId, String token, UpdateCustomerInfoBody body, IResponseListener listener ){
        processResponseBody( apiInterface.updateCustomerInfo( userId, token, body ), listener );
    }

    public void discardOrder( int userId, String token, DiscardOrderBody body, IResponseListener listener ){
        processResponseBody( apiInterface.discardOrder( userId, token, body ), listener );
    }

    /*public void getOrderDetails( int userId, String token, GetOrderDetailsBody body, IResponseListener listener ){
        processResponseBody( apiInterface.discardOrder( userId, token, body ), listener );
    }*/

/*    public void getOrderDiscardOptions( int userId, String token, IResponseListener listener ){
        processResponseBody( apiInterface.discardOrder( userId, token ), listener );
    }*/

    public void getOrderStates( int userId, String token, IResponseListener listener ){
        processResponseBody( apiInterface.getOrderStates( userId, token ), listener );
    }

    public void insrtOrder( int userId, String token, InsrtOrderBody body, IResponseListener listener ){
        processResponseBody( apiInterface.insrtOrder( userId, token, body ), listener );
    }

    public void setSheetSupplier( int userId, String token, SheetSupplierBody body, IResponseListener listener ){
        processResponseBody( apiInterface.setSheetSupplier( userId, token, body ), listener );
    }

    public void updateOrder( int userId, String token, InsrtOrderBody body, IResponseListener listener ){
        processResponseBody( apiInterface.updateOrder( userId, token, body ), listener );
    }


}
