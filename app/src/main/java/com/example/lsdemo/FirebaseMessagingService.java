package com.example.lsdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String messageTitle=remoteMessage.getNotification().getTitle();
        String messageBody=remoteMessage.getNotification().getBody();

        NotificationCompat.Builder mbuilder=
                new NotificationCompat.Builder(FirebaseMessagingService.this,getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.icon)
                .setOngoing(true)
                .setContentTitle(messageTitle)
                .setContentText(messageBody);


        int mNotificationId=(int) System.currentTimeMillis();
       // NotificationManagerCompat manager=NotificationManagerCompat.from(getApplicationContext());

        NotificationChannel notificationChannel=new NotificationChannel(getString(R.string.default_notification_channel_id),"Notification",NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        manager.notify(mNotificationId,mbuilder.build());

    }
}
