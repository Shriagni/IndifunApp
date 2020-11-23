package com.deificindia.indifun1.Utility;

import android.util.Log;

import com.google.gson.Gson;

public class Logger {

    public static void loge(String... ss){
        StringBuilder sb = new StringBuilder();
        for (String s: ss){
            sb.append(s);
            sb.append(" | ");
        }

        Log.e("TAG", sb.toString());
    }

    public static void logpk(String... ss){
        StringBuilder sb = new StringBuilder();
        for (String s: ss){
            sb.append(s);
            sb.append(" | ");
        }

        Log.e("PK", sb.toString());
    }

    public static String  toGson(String tag,Object obj){
        String reslt = new Gson().toJson(obj);
        //loge("gson:"+tag, reslt);
        return reslt;
    }

    public static String  toGson(Object obj){
        String reslt = new Gson().toJson(obj);
        //loge("gson:", reslt);
        return reslt;
    }

    public static void rep2String(){

    }
}
