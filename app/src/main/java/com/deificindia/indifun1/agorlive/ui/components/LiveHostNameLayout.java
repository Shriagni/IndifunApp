package com.deificindia.indifun1.agorlive.ui.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.URLs;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class LiveHostNameLayout extends RelativeLayout {
    private static final int IMAGE_VIEW_ID = 1 << 4;
    private static final int IMAGE_VIEW_ADD_ID = 5;
    private static final int TEXT_VIEW_ID = 6;

    private int mMaxWidth;
    private int mHeight;
    private CircleImageView mIconImageView;
    private AppCompatTextView mNameTextView;
    private AppCompatImageView mAddFriend;

    String name;
    String user_id;
    boolean isHost;
    LayoutParams params;

    int iconPadding;

    public LiveHostNameLayout(Context context) {
        super(context);
    }

    public LiveHostNameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveHostNameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(boolean lightMode) {
        mMaxWidth = getResources().getDimensionPixelSize(R.dimen.live_name_pad_max_width);
        mHeight = getResources().getDimensionPixelSize(R.dimen.live_name_pad_height);

        if (lightMode) {
            setBackgroundResource(R.drawable.round_scalable_gray_transparent_bg);
        } else {
            setBackgroundResource(R.drawable.round_scalable_gray_bg);
        }



        mIconImageView = new CircleImageView(getContext());
        mIconImageView.setId(IMAGE_VIEW_ID);
        addView(mIconImageView);
        iconPadding = getResources().getDimensionPixelSize(R.dimen.live_name_pad_icon_padding);
        params = (LayoutParams) mIconImageView.getLayoutParams();
        int iconSize = mHeight - iconPadding * 2;
        params.width = iconSize;
        params.height = iconSize;
        params.leftMargin = iconPadding;
        params.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mIconImageView.setLayoutParams(params);

        mNameTextView = new AppCompatTextView(getContext());
        mNameTextView.setId(TEXT_VIEW_ID);
        addView(mNameTextView);
        params = (LayoutParams) mNameTextView.getLayoutParams();
        params.addRule(RelativeLayout.END_OF, IMAGE_VIEW_ID);
        //params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.START_OF, IMAGE_VIEW_ADD_ID);
        params.leftMargin = mHeight / 5;
        params.rightMargin = params.leftMargin;
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        mNameTextView.setLayoutParams(params);

        int textSize = getResources().getDimensionPixelSize(R.dimen.text_size_small);
        mNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (lightMode) {
            mNameTextView.setTextColor(Color.BLACK);
        } else {
            mNameTextView.setTextColor(Color.WHITE);
        }

        mNameTextView.setSingleLine(true);
        mNameTextView.setFocusable(true);
        mNameTextView.setFocusableInTouchMode(true);
        mNameTextView.setSelected(true);
        mNameTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mNameTextView.setMarqueeRepeatLimit(-1);
        mNameTextView.setTextAlignment(TextView.TEXT_ALIGNMENT_GRAVITY);
        mNameTextView.setGravity(Gravity.CENTER);

        mIconImageView.setOnClickListener(v->{
            if(_add_Listener!=null && user_id!=null) _add_Listener.onAvtarClick(user_id);
        });

    }

    void addSubscribe(){
        mAddFriend = new AppCompatImageView(getContext());
        mAddFriend.setId(IMAGE_VIEW_ADD_ID);
        addView(mAddFriend);
        params = (LayoutParams) mAddFriend.getLayoutParams();
        //params.addRule(RelativeLayout.END_OF, TEXT_VIEW_ID);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        params.width = 30;
        params.height = 30;
        params.rightMargin = iconPadding;
        mAddFriend.setLayoutParams(params);
        mAddFriend.setImageResource(R.drawable.add);

        mAddFriend.setOnClickListener(v->{
            if(_add_Listener!=null && user_id!=null) _add_Listener.onAddClick(user_id);
        });
    }

    public void init() {
        init(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mMaxWidth, mHeight);
        int widthSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);
    }

    public void setName(String name, String broadcaster_id, boolean isOwner) {
        this.name = name;
        this.user_id = broadcaster_id;
        this.isHost = isOwner;
        mNameTextView.setText(name);

        if(!isOwner) {
            addSubscribe();
        }
    }

    public void setName(String name) {
        this.name = name;
        mNameTextView.setText(name);
    }

    public void setIcon(Drawable drawable) {
        mIconImageView.setImageDrawable(drawable);
    }

    public void setAvtarByLink(String link) {
        Picasso.get().load(URLs.UserImageBaseUrl+link).error(R.drawable.img_user_default)
                .into(mIconImageView);
    }
    /**
     * For development only, test fake user icon
     * @param name
     */
    public void setIconResource(String name) {
        RoundedBitmapDrawable drawable = null;
        try {
            drawable = RoundedBitmapDrawableFactory.create(getResources(), getResources().getAssets().open(name));
            drawable.setCircular(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mIconImageView.setImageDrawable(drawable);
    }

    OnAddClickListener _add_Listener;

    public interface OnAddClickListener{
        void onAddClick(String userId);
        void onAvtarClick(String userId);
    }

    public void setListener(OnAddClickListener listener){ _add_Listener = listener; }

}
