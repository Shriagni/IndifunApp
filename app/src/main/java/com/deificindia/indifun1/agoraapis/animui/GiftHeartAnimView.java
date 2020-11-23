package com.deificindia.indifun1.agoraapis.animui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deificindia.indifun1.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class GiftHeartAnimView extends RelativeLayout {

    public GiftHeartAnimView(Context context) {
        super(context);
        init(context);
    }

    public GiftHeartAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GiftHeartAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context){

        LayoutInflater.from(context).inflate(R.layout.gift_heart_anim_view, this, true);

    }

    View parent;
    CircleImageView imguser;
    TextView username;
    TextView tv_mesage, tv_no_of_heart;
    ImageView img_heart;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        parent = findViewById(R.id.relate);
        imguser = findViewById(R.id.ima);
        username = findViewById(R.id.txt_name);
        tv_mesage = findViewById(R.id.txt_gift);

        img_heart = findViewById(R.id.heart_image);
        tv_no_of_heart = findViewById(R.id.tv_no_of_heart);

        if(imgurl!=null)
        {
            Picasso.get().load(imgurl).into(imguser);
        }
        if(username!=null) username.setText(usrname);
        if(username!=null) username.setText(usrname);
        if(msg!=null) tv_mesage.setText(msg);
        if(gift_icon>0) img_heart.setImageResource(gift_icon);
        if(parentbg>0) parent.setBackgroundResource(parentbg);

    }

    String imgurl;
    String usrname;
    String msg;
    int parentbg;
    int gift_icon;

    public void setData(String imgurl, String uname, String msg, int gift_icon, int parentbg){
        this.imgurl = imgurl;
        this.usrname = uname;
        this.msg = msg;
        this.parentbg = parentbg;
        this.gift_icon = gift_icon;
    }

    public void updateGiftCount(int count){
        tv_no_of_heart.setText(count);
    }
}
