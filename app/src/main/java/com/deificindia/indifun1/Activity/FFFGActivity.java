package com.deificindia.indifun1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.deificindia.indifun1.Adapter.FFFGAdapter;
import com.deificindia.indifun1.Fragment.FFFGFragment;
import com.deificindia.indifun1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FFFGActivity extends AppCompatActivity {

    ViewPager2 pager2;
    TabLayout tabLayout;
    TextView tvh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_f_f_g);

        pager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabTextColors(getApplicationContext().getResources().getColor(R.color.white), getApplicationContext()
        .getResources().getColor(R.color.white));

        setUpPager();

        int n = getIntent().getIntExtra("TAB", 0);



        pager2.setCurrentItem(n);

        findViewById(R.id.imgBack).setOnClickListener(v-> finish());
        tvh = findViewById(R.id.tvHeading);
        changeTitle(n);

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    void setUpPager(){
        Fragmentdapter adapter = new Fragmentdapter(getSupportFragmentManager(), getLifecycle());
        pager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, pager2,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Friends");
                            break;
                        case 1:
                            tab.setText("Followings");
                            break;
                        case 2:
                            tab.setText("Followers");
                            break;
                        case 3:
                            tab.setText("Groups");
                            break;
                    }

                }
        ).attach();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab,null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    static class Fragmentdapter extends FragmentStateAdapter{

        public Fragmentdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new FFFGFragment();
            Bundle args = new Bundle();
            args.putInt(FFFGFragment.ARG_PARAM1, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 4;
        }

    }

    public void changeTitle(int n){
        String friends = getIntent().getStringExtra("FriendsN");
        String following = getIntent().getStringExtra("FollowingN");
        String followers = getIntent().getStringExtra("FollowersN");
        String groups = getIntent().getStringExtra("GroupsN");
        switch (n) {
            case 0:
                tvh.setText("Friends"+ "("+ friends + ")");
                break;
            case 1:
                tvh.setText("Followings"+ "("+ following + ")");
                break;
            case 2:
                tvh.setText("Followers"+ "("+ followers + ")");
                break;
            case 3:
               tvh.setText("Groups"+ "("+ groups + ")");
                break;
        }
    }

}