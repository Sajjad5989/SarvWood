package ir.sarvwood.workshop.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import androidx.core.app.NotificationCompat;
import ir.sarvwood.workshop.BuildConfig;
import ir.sarvwood.workshop.R;
import ir.sarvwood.workshop.activity.MainActivity;
import ir.sarvwood.workshop.activity.SupplierActivity;
import ir.sarvwood.workshop.preferences.GeneralPreferences;
import ir.sarvwood.workshop.utils.APP;

public class SarvPushNotificationService extends FirebaseMessagingService {

    private Map< String, String > data;
    private String body;
    private String title;
    private String mod;
    private String orderId;

    @Override
    public void onMessageReceived( RemoteMessage remoteMessage ){
        if ( remoteMessage.getData().size() > 0 ) {
            data = remoteMessage.getData();
            pushNotification();
        }
    }

    private Intent getDestinationIntent( ){
        if ( mod.toLowerCase().equals( "pending" ) ) {
            Intent intent = new Intent( this, SupplierActivity.class );
            Bundle bundle = new Bundle();
            bundle.putInt( "orderId", Integer.valueOf( orderId ) );
            intent.putExtras( bundle );
            return intent;
        } else
            return new Intent( this, MainActivity.class );
    }

    private void pushNotification() {
        processData();


        Intent intent = getDestinationIntent( );//new Intent(this, SupplierActivity.class);
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT );

        Uri sound = Uri.parse( "android.resource://" + getPackageName() + "/raw/" + R.raw.notify );
        MediaPlayer mediaPlayer = MediaPlayer.create( getApplicationContext(), sound );
        mediaPlayer.start();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( this, "ir.sarvwood.workshop" )
                .setContentIntent( pendingIntent )
                .setStyle( new NotificationCompat.BigTextStyle().bigText( body ) )
                .setDefaults( Notification.DEFAULT_ALL )
                .setPriority( Notification.PRIORITY_HIGH )
                .setWhen( System.currentTimeMillis() )
                .setSmallIcon( R.mipmap.ic_launcher )
                .setTicker( title )
                .setAutoCancel( true )
                .setContentTitle( "" )
                .setContentText( body )
                .setSound( null )
                .setContentInfo("");

        NotificationManager notificationManager = ( NotificationManager ) getSystemService( Context.NOTIFICATION_SERVICE );

        if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {

            if (notificationManager != null) {
                notificationManager.deleteNotificationChannel("ir.sarvwood.workshop");
            }

            NotificationChannel channel = new NotificationChannel( "ir.sarvwood.workshop", "کارگاه سرو", NotificationManager.IMPORTANCE_HIGH );
            channel.setLockscreenVisibility( Notification.VISIBILITY_SECRET );
            channel.setShowBadge( true );
            channel.enableLights( true );
            channel.setLightColor( getResources().getColor( R.color.colorAccent ) );
            channel.setDescription( "توضیحات اپلیکیشن" );
            channel.setSound( null, null );


            if ( notificationManager != null ) {
                notificationManager.createNotificationChannel( channel );
            }

        }
        if ( notificationManager != null ) {
            notificationManager.notify( 0 /* ID of notification */, notificationBuilder.build() );
        }

    }

    private void processData() {
        mod = getValue( data, "mod", "Pending" );
        body = getValue( data, "body", "" );
        title = getValue( data, "title", "" );
        orderId = getValue( data, "orderId", "0" );

        /*
          mod = getValue( data, "mod", "New" );
        body = getValue( data, "body", "" );
        title = getValue( data, "title", "" );
        servicemanId = getValue( data, "servicemanId", "0" );
        requestId = getValue( data, "requestId", "0" );
        */
    }

    private String getValue( Map< String, String > data, String tag, String emptyValue ){
        try {
            return data.get( tag );
        } catch ( Exception e ) {
            return emptyValue;
        }
    }


    @Override
    public void onNewToken( String token ){
        sendRegistrationToServer( token );
    }

    private void sendRegistrationToServer(String token) {
        GeneralPreferences.getInstance(SarvPushNotificationService.this).putString("fireToken", token);
    }



}
