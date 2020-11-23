package com.deificindia.indifun1.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import com.deificindia.indifun1.R;

import com.deificindia.indifun1.agoraapis.api.mod.GiftInfo2;
import com.deificindia.indifun1.agorlive.proxy.model.model.GiftInfo;
import com.deificindia.indifun1.agorlive.proxy.model.response.GiftRankResponse;
import com.deificindia.indifun1.dialogs.giftsheet.GiftItemListener;
import com.deificindia.indifun1.dialogs.giftsheet.OnGiftItemSelectedListener;
import com.deificindia.indifun1.dialogs.giftsheet.ParentFragmentPagerAdapter;
import com.deificindia.indifun1.ui.PagerSlidingTabStrip;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GiftBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener, OnGiftItemSelectedListener {

    private GiftActionSheetListener mListener;



    public interface GiftActionSheetListener {
        void onActionSheetGiftSend(int position, GiftInfo2 info);
    }

    public void setActionSheetListener(GiftActionSheetListener listener) {
        this.mListener = listener;
    }

    ViewPager viewPager2;
    PagerSlidingTabStrip tabLayout;

    String mValueFormat;
    private int mSelected = -1;

    GiftInfo2 giftInfo;

    @Override
    public void onItemSelected(int pos, GiftInfo2 info) {
        mSelected = pos;
        giftInfo = info;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gift_bottom_sheet_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mValueFormat = getResources().getString(R.string.live_room_gift_action_sheet_value_format);

        viewPager2 = view.findViewById(R.id.viewpager2);
        tabLayout = view.findViewById(R.id.tabs);

        AppCompatTextView sendBtn = view.findViewById(R.id.live_room_action_sheet_gift_send_btn);
        GiftItemListener.setOnGiftItemSelectedListener(this);
        sendBtn.setOnClickListener(this);
        initPagers();
    }

    void initPagers(){
        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                /*Toast.makeText(HomeActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();*/
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

        viewPager2.setAdapter(new ParentFragmentPagerAdapter(getChildFragmentManager()));
        tabLayout.setViewPager(viewPager2);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.live_room_action_sheet_gift_send_btn) {

            if (mListener != null && mSelected != -1) {

                mListener.onActionSheetGiftSend(mSelected, giftInfo);

            }
        }
    }




}
