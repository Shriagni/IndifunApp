package com.deificindia.indifun1.fcm;

import android.util.Log;

import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.Logger;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        try {
            if (remoteMessage != null && remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
                //Log.d(TAG, "Message data payload: " + remoteMessage.getData());
                String mobile = IndifunApplication.getInstance().getCredential().getContact().replace("+", "");
                if(mobile!=null && Objects.equals(remoteMessage.getFrom(), "/topics/" + mobile)){
                    Logger.loge(TAG, "Message data payload notice");
                    newsNotification(remoteMessage);
                }
                if(Objects.equals(remoteMessage.getFrom(), "/topics/" + Config.NEWS)){
                    Log.e(TAG, "Message data payload notice");
                    newsNotification(remoteMessage);
                }

            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
        }catch (Exception e){}
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        //Log.d(TAG, "Refreshed token: " + token);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }

    private void newsNotification(final RemoteMessage remoteMessage) {
        /*case "/topics/" + URLLINK:
        Log.d(TAG, "Message data payload yotube");
        urllive(remoteMessage);
        break;*/
        Map<String, String> data = remoteMessage.getData();
        Logger.loge("Notice", data.toString());
        /*String baseurl = data.get("baseurl");
        String fullurl = data.get("fullurl");
        String version = data.get("version");*/
        //if(baseurl!=null) STRING("LIVEURL", baseurl);
        //if(fullurl!=null) STRING("LIVEURL2", fullurl);
        //if(version!=null) VERSIONS("MENUSURLS", Long.parseLong(version));
    }



}
