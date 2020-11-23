package com.deificindia.indifun1.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun1.R;

import java.util.Random;

public class LoadingAnimView extends RelativeLayout {


    String[] loadinganim = new String[]{"835-loading.json", "5316-loading.json", "8771-loading.json"};
    LottieAnimationView lottieAnimationView;
    View ErrorLay;
    TextView ErrorText;

    public LoadingAnimView(Context context) {
        super(context);
        init(context);
    }

    public LoadingAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context cnx){
        LayoutInflater.from(cnx).inflate(R.layout.loading_anima_view, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        lottieAnimationView = findViewById(R.id.imgError);
        ErrorLay = findViewById(R.id.errorLayout);
        ErrorText = findViewById(R.id.tvErro);
    }

    public void showError(/*"nodata.json"*/){
        ErrorLay.setVisibility(View.VISIBLE);

        if(lottieAnimationView.isAnimating())
            lottieAnimationView.cancelAnimation();

        lottieAnimationView.setAnimation("37826-boy-cry.json");
        setErrText("");
        playAnim();
    }
    public void startloading(){
        if(lottieAnimationView.isAnimating())
            lottieAnimationView.cancelAnimation();

        //int ran =  new Random().nextInt(loadinganim.length);
        lottieAnimationView.setAnimation("835-loading.json");
        setErrText("");
        playAnim();
    }

    public void stopAnim(){
        if(lottieAnimationView.isAnimating())
            lottieAnimationView.cancelAnimation();

        ErrorLay.setVisibility(View.GONE);
    }

    public void playAnim(){
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(true);
    }

    public void setErrText(String txt){
        ErrorText.setText(txt);
    }
}
