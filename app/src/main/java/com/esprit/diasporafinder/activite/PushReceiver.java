package com.esprit.diasporafinder.activite;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

import com.backendless.messaging.PublishOptions;
import com.backendless.push.BackendlessBroadcastReceiver;
import com.esprit.diasporafinder.R;

/**
 * Created by Bourgeois on 18/05/2016.
 */
public class PushReceiver extends BackendlessBroadcastReceiver {
    private static final int NOTIFICATION_ID = 1;
    public static String PREFERENCE_MESSAGE = "idMessage";


    public void onRegistered( Context context, String registrationId ){

    }
    public void onUnregistered( Context context, Boolean unregistered ){

    }
    public boolean onMessage( Context context, Intent intent ){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(1000);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mediaPlayer = MediaPlayer.create(context, notification);
        mediaPlayer.start();


        CharSequence tickerText = intent.getStringExtra( PublishOptions.ANDROID_TICKER_TEXT_TAG );

        CharSequence contentTitle = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TITLE_TAG);

        CharSequence contentText = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TEXT_TAG);

        //CharSequence idPost = intent.getStringExtra("android-content-idPost");
        //CharSequence interfaceOrientation = intent.getStringExtra("android-content-interface");


        //String message = intent.getStringExtra( PublishOptions.MESSAGE_TAG );
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

//Here you put the Activity that you want to open when you click the Notification

//(and you can pass some Bundle/Extra if you want to add information about the Notification)

      /*  String orientationString = (String) interfaceOrientation;

        if(orientationString.equalsIgnoreCase("message")){
            SharedPreferences reportingPref =context.getSharedPreferences(PREFERENCE_MESSAGE,  context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = reportingPref.edit();
            prefEditor.putString("idDiscussion", (String) idPost);
            prefEditor.putString("interFace", "message");
            prefEditor.commit();
        }*/



        Intent notificationIntent = new Intent(context, HomePageActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

//Custom notification



        android.support.v4.app.NotificationCompat.Builder notificatio = new NotificationCompat.Builder(context)

                .setContentTitle(contentTitle).setTicker(tickerText).setContentText(contentText).setSmallIcon(R.drawable.ic_app);

        notificatio.setContentIntent(contentIntent);

//Dismiss notification when click on it

        notificatio.setAutoCancel(true);

        mNotificationManager.notify(NOTIFICATION_ID, notificatio.build());

// super.onMessage(context, intent);

        return false;
    }
}
