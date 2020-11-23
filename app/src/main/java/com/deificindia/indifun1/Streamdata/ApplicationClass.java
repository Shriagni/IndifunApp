package com.deificindia.indifun1.Streamdata;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.GetSet;
import com.deificindia.indifun1.Utility.NetworkReceiver;
import com.deificindia.indifun1.Utility.api;
import com.google.android.material.snackbar.Snackbar;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class ApplicationClass extends Application {

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    private static Snackbar snackbar = null;
    private static ApplicationClass mInstance;
    private static Toast toast = null;
    private io.socket.client.Socket mSocket;

    public static synchronized ApplicationClass getInstance() {
        return mInstance;
    }

    // Showing network status in Snackbar
    public static void showSnack(final Context context, View view, boolean isConnected) {
        if (snackbar == null) {
            snackbar = Snackbar
                    .make(view, context.getString(R.string.network_failure), Snackbar.LENGTH_INDEFINITE)
                    .setAction("SETTINGS", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            context.startActivity(intent);
                        }
                    });
            View sbView = snackbar.getView();
            //TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
           // textView.setTextColor(Color.WHITE);
        }

        if (!isConnected && !snackbar.isShown()) {
            snackbar.show();
        } else {
            snackbar.dismiss();
            snackbar = null;
        }
    }

    public static void showToast(final Context context, String text, int duration) {

        if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
            toast = Toast.makeText(context, text, duration);
            toast.show();
        } else toast.cancel();
    }

    /**
     * To convert the given dp value to pixel
     **/
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static void hideSoftKeyboard(Activity context, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException npe) {
        } catch (Exception e) {
        }
    }

    public static float pxToDp(Context context, float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        pref = getApplicationContext().getSharedPreferences("SavedPref", MODE_PRIVATE);
        editor = pref.edit();
        //ACRA.init(this);

        if (pref.getBoolean("isLogged", false)) {
            GetSet.setLogged(true);
            GetSet.setUserId(pref.getString("userId", null));
            GetSet.setUserName(pref.getString("userName", null));
            GetSet.setFullName(pref.getString("fullName", null));
            GetSet.setImageUrl(pref.getString("userImage", null));
            GetSet.setToken(pref.getString("token", null));
        }

        {
            try {
                mSocket = IO.socket(api.CHAT_SERVER_URL);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
       // MultiDex.install(this);
    }

    public void setConnectivityListener(NetworkReceiver.ConnectivityReceiverListener listener) {
        NetworkReceiver.connectivityReceiverListener = listener;
    }

}
