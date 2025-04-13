package com.example.vroomandroidapplicationv4.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.vroomandroidapplicationv4.R;
import com.example.vroomandroidapplicationv4.utils.MessageObserver;
import com.example.vroomandroidapplicationv4.HomeActivity;

//Reason to use singleton - ensure only one instance is created, ensure managing of notications centrallu, can easy call the singleton with global access
public class NotificationManagerSingleton implements MessageObserver {
    private static final String CHANNEL_ID = "vroom_notifications"; // Notification Channel ID
    private static NotificationManagerSingleton instance; // prevent direct instantiation of the class ned use getInstance
    private Context context;

    private NotificationManagerSingleton(Context context) { //private prevents other classes from creating an instance of NotifcationManagerSingleton so can use getInstance() instead
        this.context = context.getApplicationContext();
        createNotificationChannel();
    }

    public static synchronized NotificationManagerSingleton getInstance(Context context) { //synchronized to sure that when multiple threads call getinstance() it is called one at a time
        if (instance == null) {
            instance = new NotificationManagerSingleton(context);
        }
        return instance;
    }

    @Override
    public void onNewMessage(String message) { //triggered when new message arrives, part of MessageObeserver interface
        Log.d("NotificationManager", "New AI message received: " + message); //logging the message
        sendNotification(message); // triggers notifcaiton
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { //ned create notification channel for newer android systems(Android Oreo and above?)
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, //to identify the channel
                    "Vroom Messages",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for AI messages"); //appears in the settings

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void sendNotification(String message) { //method to create and send a notification when message is received
        Log.d("PushNotification", "New Message: " + message);

        Intent intent = new Intent(context, HomeActivity.class); // Open MainActivity with a flag to indicate ChatFragment should be opened
        intent.putExtra("openChatFragment", true);  // Flag for ChatFragment
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        ); //wraps the intent and allows it to be triggered when notif is clicked

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID) //builds the notification
                .setSmallIcon(R.drawable.vroom)
                .setContentTitle("New AI Message")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);//issue the notication to the system
        notificationManager.notify(1, builder.build());
    }

}
