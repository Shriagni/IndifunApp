package com.deificindia.indifun1.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.ui.like.LikeButton;

public class LikeCommentPanel extends RelativeLayout implements View.OnClickListener {

    OnComentClick _listener1;
    OnLikeClick _listener2;

    public void setOnUserClickListener(OnComentClick listener1, OnLikeClick listener2){
        this._listener1 = listener1;
        this._listener2 = listener2;
    }

    public LikeButton likeButton;
    public TextView like, comment;
    public ImageView iv_comment;

    LinearLayout like_panel, comment_panel;

    public LikeCommentPanel(Context context) {
        super(context);
        initView(context);
    }

    public LikeCommentPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LikeCommentPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context){

        LayoutInflater.from(context).inflate(R.layout.like_comment_panel, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        like_panel = findViewById(R.id.like_panel);
        comment_panel = findViewById(R.id.comment_panel);

        likeButton = findViewById(R.id.likeimage);
        iv_comment = findViewById(R.id.commentimage);
        like = findViewById(R.id.tv_likecount);
        comment = findViewById(R.id.tv_commentcount);

        like_panel.setOnClickListener(this);
        comment_panel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.like_panel:
                if(_listener2!=null) _listener2.onLike();
                break;
            case R.id.comment_panel:
                if(_listener1!=null) _listener1.onComment();
                break;
        }
    }

    public void likeEnable(boolean b){
        like_panel.setEnabled(b);
    }

    public void setLikeCount(String likes){ this.like.setText(likes); }

    public void setComment(String comm){ this.comment.setText(comm); }

    public interface OnComentClick{
        void onComment();
    }

    public interface OnLikeClick{
        void onLike();
    }


}
