package ir.sarvwood.workshop.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.webservice.getcustomerinfo.GetCustomerInfoReturnValue;
import ir.sarvwood.workshop.webservice.insrtcustomer.InsrtCustomerSimpleRerunValue;
import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsItemReturnValue;
import ir.sarvwood.workshop.webservice.orderdetail.GetOrderDetailsReturnValue;
import ir.sarvwood.workshop.webservice.sarvwoodapi.ResponseData;
import okhttp3.Response;


public class GeneralPreferences {
    private static GeneralPreferences instance = null;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final Context context;

    private GeneralPreferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("general_pref_file", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static GeneralPreferences getInstance(Context context) {
        if (instance == null)
            instance = new GeneralPreferences(context);
        return instance;
    }

    public void remove(String tag) {
        editor.remove(tag).commit();
    }

    public void putBoolean(String title, boolean value) {
        remove(title);
        editor.putBoolean(title, value);
        editor.apply();
    }

    public boolean getBoolean(String title) {
        return sharedPreferences.getBoolean(title, false);
    }


    public void putString(String title, String value) {
        remove(title);
        editor.putString(title, value);
        editor.apply();
    }

    public String getString(String title) {
        return sharedPreferences.getString(title, null);
    }

    public void putInt(String title, int value) {
        remove(title);
        editor.putInt(title, value);
        editor.apply();
    }

    public int getInt(String title) {
        return sharedPreferences.getInt(title, 0);
    }


    public void putCustomerId(int userId)
    {
        String title = BuildConfig.userId;
        remove(title);
        editor.putInt(title, userId);
        editor.apply();
    }
    public int getCustomerId()
    {
        return sharedPreferences.getInt(BuildConfig.userId, 0);
    }

    public void putToken(String token)
    {
        String title = BuildConfig.accessToken;
        remove(title);
        editor.putString(title, token);
        editor.apply();
    }
    public String getToken()
    {
        return sharedPreferences.getString(BuildConfig.accessToken,"");
    }

    public void putListCustomerInfoResponse(GetCustomerInfoReturnValue response) {

        String tag = "CustomerInfo";
        remove(tag);
        editor.putString(tag, new Gson().toJson(response, GetCustomerInfoReturnValue.class));
        editor.apply();

    }

    public GetCustomerInfoReturnValue getListCustomerInfoResponse() {
        String responseString = sharedPreferences.getString("CustomerInfo", null);
        if (responseString == null)
            return new GetCustomerInfoReturnValue();


        Gson gson = new Gson();
        return gson.fromJson(responseString, (Type) GetCustomerInfoReturnValue.class);

    }

    public void putInsrtCustomerSimpleRerunValueResponse(InsrtCustomerSimpleRerunValue response) {

        String tag = "InsertCustomer";
        remove(tag);
        editor.putString(tag, new Gson().toJson(response, InsrtCustomerSimpleRerunValue.class));
        editor.apply();

    }

    public  void putOrderList(GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue> value)
    {
        String tag = "OrderList";
        remove(tag);
        editor.putString(tag, new Gson().toJson(value, GetOrderDetailsReturnValue.class));
        editor.apply();
    }


    public  GetOrderDetailsReturnValue<GetOrderDetailsItemReturnValue> getOrderList() {
        String responseString = sharedPreferences.getString("OrderList", null);
        if (responseString == null)
            return new GetOrderDetailsReturnValue();


        Gson gson = new Gson();
        return gson.fromJson(responseString, (Type) GetOrderDetailsReturnValue.class);

    }



//
//    private void putServiceManString(String serviceMan) {
//        editor.remove(serviceMan).commit();
//        editor.putString(context.getString(R.string.text_service_man), serviceMan);
//        editor.apply();
//    }
//

}
