package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.deificindia.indifun1.Model.retro.Image;
import com.deificindia.indifun1.R;

import com.deificindia.indifun1.agoraapis.animui.AnimUtils;
import com.deificindia.indifun1.agoraapis.animui.GiftAnim;
import com.deificindia.indifun1.agoraapis.animui.GiftAnimView;
import com.deificindia.indifun1.agoraapis.animui.GiftHeartAnimView;
import com.deificindia.indifun1.agoraapis.animui.ImageFloatView;
import com.deificindia.indifun1.agoraapis.maths.AgoraUiUtils;
import com.deificindia.indifun1.agorlive.ui.components.LiveHostNameLayout;
import com.deificindia.indifun1.agorlive.ui.components.LiveRoomUserLayout;
import com.deificindia.indifun1.agorlive.ui.components.PkLayout;
import com.deificindia.indifun1.ui.BubbleEmitterView;

import java.util.Random;

public class Test1Activity extends AppCompatActivity {

    BubbleEmitterView bubbleEmitterView;
    ImageFloatView imageFloatView;

    private LiveHostNameLayout mNamePad;
    protected LiveRoomUserLayout participants;
    protected LinearLayout itemTags, layout_room_enter_animation,
            layout_heart_animation_area, layout_gift_animation,
            layout_gift_2_animation;

    PkLayout pk_host_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        //bubbleEmitterView = findViewById(R.id.bubble);
//        imageFloatView = findViewById(R.id.bloatview);

        //emittbubble();
        initViewS();
    }

    void initViewS(){
        mNamePad = findViewById(R.id.pk_host_in_name_pad);
        mNamePad.init();

        mNamePad.setName("jamell");
        mNamePad.setIcon(getResources().getDrawable(R.drawable.img_user_default));

        participants = findViewById(R.id.pk_host_in_participant);
        participants.init();
        //participants.setUserLayoutListener(this);

        itemTags = findViewById(R.id.itemLayouts);
        layout_room_enter_animation = findViewById(R.id.layout_room_enter_animation);
        layout_heart_animation_area = findViewById(R.id.layout_heart_animation_area);
        layout_gift_animation = findViewById(R.id.layout_gift_animation);

        pk_host_layout = findViewById(R.id.pk_host_layout);
        layout_gift_2_animation = pk_host_layout.findViewById(R.id.layout_gift_2_animation);

        findViewById(R.id.start_pk_button).setOnClickListener(v->{
            ImageView img = img();
            layout_heart_animation_area.addView(img);
            AnimUtils.animateMe(layout_heart_animation_area, img);
        });

        AgoraUiUtils.setChip(this, itemTags, AgoraUiUtils.formatValue(9876678), AgoraUiUtils.getUriToResource(this, R.drawable.ic_heart3));
        AgoraUiUtils.setChip(this, itemTags, "877", AgoraUiUtils.getUriToResource(this, R.drawable.ic_star3));
        AgoraUiUtils.setChip(this, itemTags, "Lv.5", AgoraUiUtils.getUriToResource(this, R.drawable.stars));
    }

    void emittbubble(){
        Random random1 = new Random();
        int timne = random1.nextInt(500-100) + 100;

        new Handler().postDelayed(()->{
            Random random = new Random();
            int size = random.nextInt(80-20) + 20;
            bubbleEmitterView.emitBubble(size);

        }, timne);
    }

    public void onBubble(View view) {
        onGift();
    }

    public void onBubble2(View view) {
        ImageView img = imageFloatView.generateImage();
        imageFloatView.setToLayout(img);
        imageFloatView.setImageRes(img, R.drawable.golden_heart_1);
        imageFloatView.animateMe(img,2.0f, 3.0f, 4.0f, 3.0f, true);
    }

    public void onGift(){
        GiftAnimView gav=new GiftAnimView(this);
        gav.setData("", "Indifun", "5", "gave gift", R.drawable.bg_red);

        layout_gift_animation.addView(gav);
        AnimUtils.topGiftAnimView(this, layout_gift_animation, gav);

    }

    public final ImageView img() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(R.drawable.heart_on);
        return imageView;
    }

    public void playAnim(View view) {
        onGift();
    }

    public void playAnim2(View view) {
        GiftHeartAnimView animView = new GiftHeartAnimView(this);
        layout_gift_2_animation.addView(animView);
        animView.setData("", "jam", "gave gift", R.drawable.heart_on, -1);
        AnimUtils.sideGiftAnim(layout_gift_2_animation, animView);
    }


}