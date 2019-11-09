package ir.sarvwood.workshop.interfaces;

public interface IResponseListener<T> {

    void onSuccess( T response );

    void onFailure( String error );
}
