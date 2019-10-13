package com.example.misfarmacias.Fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FireBaseMessaging :FirebaseMessagingService() {
    val TAG="notificacion"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //super.onMessageReceived(p0)

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
               // scheduleJob();
                Log.d("Notificacion", "From: " + remoteMessage.data.toString());
            } else {
                // Handle message within 10 seconds
               // handleNow();
                Log.d("Notificacion", "From: " + remoteMessage.getFrom());
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification()!!.getBody());
        }


    }
}
