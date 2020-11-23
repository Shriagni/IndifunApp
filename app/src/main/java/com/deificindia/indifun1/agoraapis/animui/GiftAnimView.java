package com.deificindia.indifun1.agoraapis.animui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.ui.TagView;

import de.hdodenhof.circleimageview.CircleImageView;

public class GiftAnimView extends RelativeLayout {
    public GiftAnimView(Context context) {
        super(context);
        init(context);
    }

    public GiftAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GiftAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.item_gift_anim_layout, this, true);
        //FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    View parent;
    CircleImageView imguser;
    TextView username;
    TagView tagview;
    TextView message;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

         parent = findViewById(R.id.parent);
         imguser = findViewById(R.id.imguser);
         username = findViewById(R.id.username);
         tagview = findViewById(R.id.tagview);
         message = findViewById(R.id.message);

        username.setText(usrname);
        message.setText(msg);

        tagview.getTagText().setText(level);
        tagview.changeBg(R.drawable.bg_blue);
        tagview.setTagImage(R.drawable.stars);
        parent.setBackgroundResource(parentbg);

         /*Picasso.get().load(imgurl)
                .into(imguser);*/

    }

    String imgurl;
    String usrname;
    String level;
    String msg;
    int parentbg;

    public void setData(String imgurl, String uname, String level, String msg, int parentbg){
        this.imgurl = imgurl;
        this.usrname = uname;
        this.level = level;
        this.msg = msg;
        this.parentbg = parentbg;





    }
}
