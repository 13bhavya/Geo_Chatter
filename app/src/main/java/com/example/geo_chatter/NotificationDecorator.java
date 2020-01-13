package com.example.geo_chatter;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
public class NotificationDecorator extends Application {

    private static final String TAG = "NotificationDecorator";
    public static final String Channel_1 = "chanel1";
    private final Context context;
    private final NotificationManager notificationMgr;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    Channel_1,
                    "Chanel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Chanel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }


    public NotificationDecorator(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationMgr = notificationManager;
    }

    public void displaySimpleNotification(String title, String contentText) {


        Intent intent = new Intent(context, Chat_room.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // notification message
        try {


            Notification noti = new Notification.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText(contentText)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .build();

            noti.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationMgr.notify(0, noti);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
