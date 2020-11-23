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

import com.deificindia.indifun1.Fragment.BroadcasterLvFragment;
import com.deificindia.indifun1.Fragment.UserlevelFragment;
import com.deificindia.indifun1.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityUserLevel extends AppCompatActivity {
    ViewPager ulViewpage;
    TabLayout UserlevelTab;
    ImageView ulBack;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_level);
        ulViewpage = findViewById(R.id.ulViewpage);
        UserlevelTab = findViewById(R.id.userLevelTab);
        ulBack = findViewById(R.id.ulBack);
        getTabs();
        ulBack.setOnClickListener(new View.OnClickListener() {
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
                viewPagerAdapter.addFragment(UserlevelFragment.getInstance(), "User Lv.");
                viewPagerAdapter.addFragment(BroadcasterLvFragment.getInstance(), "Broadcaster Lv.");

                ulViewpage.setAdapter(viewPagerAdapter);

                UserlevelTab.setupWithViewPager(ulViewpage);

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