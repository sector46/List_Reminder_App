package com.psu.acc.list_reminder;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
//import android.widget.Toast;

/**
 * Created by ARavi1 on 3/7/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Show the toast on the app
        //Toast.makeText(context, "Notification triggered!", Toast.LENGTH_LONG).show();
        Notification(context, intent.getStringExtra("LIST_NAME"));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void Notification(Context context, String listName) {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Calling NotificationView
        Intent intent = new Intent(context, NotificationView.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dismissIntent = NotificationView.getDismissIntent(0, context);

        // Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                        // Set Icon
                .setSmallIcon(R.drawable.notification_icon)
                        // Set Ticker Message
                .setTicker(listName)
                        // Set sound
                .setSound(soundUri)
                        // Set Title
                .setContentTitle(context.getString(R.string.notificationtitle))
                        // Set Text
                .setContentText("List '" + listName + "' is due now!")
                        // Add an Action Button below Notification, just dismiss the notification on click.
                .addAction(R.drawable.ok, "OK", dismissIntent)
                        // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                        // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());

    }
}
