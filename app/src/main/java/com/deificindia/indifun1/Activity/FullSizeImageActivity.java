package com.deificindia.indifun1.Activity;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.deificindia.indifun1.Fragment.ImageViewFragment;
import com.deificindia.indifun1.Model.ImagesParsble;
import com.deificindia.indifun1.R;
import com.squareup.picasso.Picasso;

public class FullSizeImageActivity extends AppCompatActivity {

    public static final String IMG_PARAM = "imgparam";


    ViewPager2 pager2;
    ImagesParsble  imgs;
    TextView tvcount;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_size_image);


        imgs = getIntent().getParcelableExtra("URL");
        int n = getIntent().getIntExtra("TAB", 0);

        pager2 = findViewById(R.id.viewpager);
        tvcount = findViewById(R.id.tvcount);

        findViewById(R.id.imgClose).setOnClickListener(v-> finish());

        Fragmentdapter adapter = new Fragmentdapter(getSupportFragmentManager(), getLifecycle());
        adapter.setImages(imgs);
        pager2.setAdapter(adapter);

        pager2.setCurrentItem(n);

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    void setPage(int pos){
        tvcount.setText(imgs.getImages().size() + "/" + pos+1);

    }

    class Fragmentdapter extends FragmentStateAdapter {

        ImagesParsble images;

        public Fragmentdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new ImageViewFragment();
            Bundle args = new Bundle();
            args.putString(IMG_PARAM, images.getImages().get(position));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return images.getImages().size();
        }

        public void setImages(ImagesParsble images) {
            this.images = images;
        }
    }


}
