package com.project.ync.blooddonation.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.ui.event.DetailEventActivity_;
import com.project.ync.blooddonation.ui.event.DetailFindBloodActivity_;

import java.util.Map;

/**
 * @author YNC
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingService";
    private Map<String, String> mData;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        mData = remoteMessage.getData();
        Log.i("TAG111", "ID POST" + (String) mData.get("id"));
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        showNotification(getApplicationContext(), remoteMessage);
    }

    /**
     * Show notification
     */
    private void showNotification(Context context, RemoteMessage remoteMessage) {
        // Instantiate a Builder object.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setOngoing(false);
        builder.setVibrate(new long[]{1000, 1000});
        Intent notifyIntent;
        Log.i("DATA1", remoteMessage.getData().get("type") + "");
        if (remoteMessage.getData().get("type").equals("1")) {
            notifyIntent = DetailFindBloodActivity_.intent(context).mId(Integer.parseInt(remoteMessage.getData().get("id"))).get();
        } else {
            notifyIntent = DetailEventActivity_.intent(context).mId(Integer.parseInt(remoteMessage.getData().get("id"))).get();
        }
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Creates the PendingIntent
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
        builder.setContentIntent(notifyPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, builder.build());
    }
}
