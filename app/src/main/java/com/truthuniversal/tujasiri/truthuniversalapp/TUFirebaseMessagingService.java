package com.truthuniversal.tujasiri.truthuniversalapp;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Set;

/**
 * Created by tujasiri on 11/9/2016.
 */
public class TUFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        System.out.println("remoteMessage==>"+remoteMessage.toString());
        //System.out.println("title ==>"+remoteMessage.getData().get("title"));

        Bundle pushNotification = new Bundle();

        pushNotification.putString("message",remoteMessage.getData().get("message"));
        pushNotification.putString("title",remoteMessage.getData().get("title"));


        //showNotification(remoteMessage.getData().get("message"));
        showNotification(pushNotification);
    }

    //private void showNotification(String message) {
    private void showNotification(Bundle pushNotificationBundle) {

        String message = "";
        String title = "";

        message = (String)pushNotificationBundle.get("message");
        title = (String)pushNotificationBundle.get("title");

        System.out.println("title==>"+title);

        System.out.println("in showNotification");

        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.putExtra("pushNotificationBundle",pushNotificationBundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Message from TU!")
                .setContentText(message)
                //.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setSmallIcon(R.drawable.ic_truth_universal_logo_dark)
                .setContentIntent(pendingIntent);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());

    }


}
