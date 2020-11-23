package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import static com.deificindia.indifun1.Utility.MySharePref.ISOTPVARIFIED;
import static com.deificindia.indifun1.Utility.MySharePref.getBoolData;
import static com.deificindia.indifun1.Utility.UiUtils.isNetworkAvailable;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    private LottieAnimationView mLottieAnimationView;
    boolean isConnected = false;
    private VideoView videoView;
    Snackbar snackbar;
    int exit = 0;

    Disposable disposable;

    Dialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        videoView = findViewById(R.id.VideoView);

        loaddata();
    }

    void loaddata(){
        disposable = Observable.fromCallable(() -> "android.resource://" + getPackageName() + "/" + R.raw.splashvideo).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            videoView.setVideoURI(Uri.parse(s));
            videoView.start();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setVolume(0, 0);
                    mp.setLooping(true);
                }
            });
            checknetwork();
        });

    }

    void checknetwork(){
        isConnected = isNetworkAvailable(SplashActivity.this.getApplication());
        if (isConnected) {
            countdown();
        } else {
            check();
        }
    }

    private void check() {
        if(dialog1!=null && dialog1.isShowing()) return;
        dialog1 = new Dialog(SplashActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog1.setCancelable(false);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_no_internet, null, false);
        dialog1.setContentView(view);

        TextView retry = view.findViewById(R.id.btn_retry_connection);
        ImageView closeBtn = view.findViewById(R.id.imgClose);
        retry.setText("Retry");

        retry.setOnClickListener(v -> {
            if (isConnected) {
                checknetwork();
            }
        });
        closeBtn.setOnClickListener(v-> finish());

        dialog1.show();
    }

    private void countdown() {
        new CountDownTimer(500, 300) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //if(ldata != null){
                if(/*ldata != null*/IndifunApplication.getInstance().getCredential() != null && getBoolData(SplashActivity.this, ISOTPVARIFIED, false)){
                   // Intent mLoginIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    Intent mLoginIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(mLoginIntent);
                    finish();
                }else {
                    Intent mLoginIntent = new Intent(SplashActivity.this, FirstLoginActivity.class);
                    startActivity(mLoginIntent);
                    finish();
                }
            }
        }.start();
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        if(disposable!=null && !disposable.isDisposed()) disposable.dispose();
        super.onDestroy();
    }
}