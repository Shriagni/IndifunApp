package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.deificindia.indifun1.Fragment.CommentFragment;
import com.deificindia.indifun1.Fragment.LikeFragment;
import com.deificindia.indifun1.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MomentNotification extends AppCompatActivity {
    ViewPager NotiViewpage;
    TabLayout NotiTab;
    ImageView NotiBack;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_notification);

        NotiViewpage = findViewById(R.id.NotiViewpage);
        NotiTab = findViewById(R.id.NotiTab);
        NotiBack = findViewById(R.id.NotiBack);
        getTabs();
        NotiBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnBackPress();
            }
        });

    }

    private void OnBackPress() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStackImmediate();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    public  void getTabs(){
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.addFragment(LikeFragment.getInstance(), "Like");
                viewPagerAdapter.addFragment(CommentFragment.getInstance(), "Comment");

                NotiViewpage.setAdapter(viewPagerAdapter);

                NotiTab.setupWithViewPager(NotiViewpage);

            }
        });

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}