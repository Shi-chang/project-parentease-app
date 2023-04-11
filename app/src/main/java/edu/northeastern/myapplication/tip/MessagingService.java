package edu.northeastern.myapplication.tip;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import edu.northeastern.myapplication.R;

/**
 * Messaging service class of this app.
 */
public class MessagingService extends FirebaseMessagingService {

    private static final String ID = "ParentEaseChannelId";
    private static final String NAME = "ParentEaseChannelName";
    private static final String DESCRIPTION = "ParentEaseChannelDescription";

    /**
     * The onCreate method called when the activity is starting.
     */
    @SuppressLint("HardwareIds")
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // Save the new token to a database or send it to your server

    }

    /**
     * The myClassifier method called when remoteMessage is not null.
     *
     * @param remoteMessage the remote message received in database
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        myClassifier(remoteMessage);
    }

    /**
     * The send notification method is called when the remote message is not null.
     *
     * @param remoteMessage the remote message
     */
    private void myClassifier(RemoteMessage remoteMessage) {
        if (remoteMessage.getFrom() != null && remoteMessage.getData().size() > 0) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            sendNotification(notification);
        }
    }

    /**
     * Sends notification method.
     *
     * @param remoteMessageNotification remote message notification
     */
    private void sendNotification(RemoteMessage.Notification remoteMessageNotification) {
        Intent intent = new Intent(this, SingleTipActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Create the intent that will be launched when the user clicks the notification
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification;
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        NotificationChannel notificationChannel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription(DESCRIPTION);
        notificationManager.createNotificationChannel(notificationChannel);
        builder = new NotificationCompat.Builder(this, ID);

        notification = builder.setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle(remoteMessageNotification.getTitle())
                .setContentText(remoteMessageNotification.getBody())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_action, "Open", pendingIntent)
                .build();
        notificationManager.notify(0, notification);
    }


}
