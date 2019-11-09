package ir.sarvwood.workshop.webservice.sarvwoodapi;

import ir.sarvwood.workshop.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    static Retrofit getRetrofit( ){


        if ( retrofit == null ) {
            retrofit = new Retrofit.Builder().baseUrl( BuildConfig.baseUrl ).
                    addConverterFactory( GsonConverterFactory.create() ).build();
        }

        return retrofit;

    }

}
