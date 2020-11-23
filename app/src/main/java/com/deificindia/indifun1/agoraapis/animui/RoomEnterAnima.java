package com.deificindia.indifun1.agoraapis.animui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deificindia.indifun1.R;

public class RoomEnterAnima extends FrameLayout {

    public RoomEnterAnima(@NonNull Context context) {
        super(context);
    }

    public RoomEnterAnima(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        roomJoin(context);
    }

    public RoomEnterAnima(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        roomJoin(context);
    }

    public RoomEnterAnima(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        roomJoin(context);
    }

    void roomJoin(Context context){
       View view = LayoutInflater.from(context).inflate(R.layout.room_enter_anima_layout, this, true);
        //View view = inflate(context, R.layout.room_enter_anima_layout, this);

    }

    public RelativeLayout parent;
    public TextView _userName;

    public String userName;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        parent = findViewById(R.id.parent);
        _userName = findViewById(R.id.tv_username);

        setName(userName);

    }

    public void setName(String name){
        this.userName = name;
        if(name!=null && _userName!=null) _userName.setText(name);
    }



    void playAnim(View view){
        /*AnimatorSet animatorSet = new AnimatorSet();
        // translationX to move object along x axis
        // next values are position value
        float startvalue = screenwidth+tvUser_join.getWidth();
        ObjectAnimator lftToRgt = ObjectAnimator
                .ofFloat(view,"translationX", startvalue, halfW )
                .setDuration(800); // to animate left to right

        ObjectAnimator rgtToLft = ObjectAnimator
                .ofFloat(view,"translationX",halfW, -tvUser_join.getWidth() )
                .setDuration(1200); // to animate right to left

        animatorSet.play( lftToRgt ).before( rgtToLft ); // manage sequence
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                room_join_layout_anim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        animatorSet.start(); // play the animation*/
    }
}
