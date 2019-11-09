package ir.sarvwood.workshop.interfaces;

public interface IResponseListener {

    void onResponse( String response );

    void onFailure( String error );
}
