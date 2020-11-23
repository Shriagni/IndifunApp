package com.deificindia.indifun1.fcm;

import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.Logger;
import com.google.firebase.messaging.FirebaseMessaging;

public class Config {

    public static final String NEWS = "notice1";

    public static void subscribetopic(){
        String mobile = IndifunApplication.getInstance().getCredential().getContact().replace("+", "");
        Logger.loge("TAG","topic", mobile);

        if(mobile!=null)
            FirebaseMessaging.getInstance().subscribeToTopic(mobile);

        FirebaseMessaging.getInstance().subscribeToTopic(Config.NEWS);

    }

    public static void unsubscribetopic(){
        String mobile = IndifunApplication.getInstance().getCredential().getContact().replace("+", "");
        Logger.loge("TAG","topic", mobile);

        if(mobile!=null)
            FirebaseMessaging.getInstance().unsubscribeFromTopic(mobile);

        FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.NEWS);

    }

}
