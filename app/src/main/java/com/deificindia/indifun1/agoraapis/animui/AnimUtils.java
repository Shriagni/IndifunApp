package com.deificindia.indifun1.agoraapis.animui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.deificindia.indifun1.Utility.UiUtils;
import com.deificindia.indifun1.anim.TranslateView;
import com.deificindia.indifun1.screenutils.DeviceUtils;
import com.deificindia.indifun1.screenutils.ResourceUtils;

import static com.deificindia.indifun1.Utility.Logger.loge;


public class AnimUtils {

    public static int minScreen(Context context){
        return Math.min(ResourceUtils.getScreenHeight(context), ResourceUtils.getScreenWidth(context));
    }

    private static int getRandIn(View parent, View child){
        int parent_rel_left = TranslateView.getRelLeft(parent);
        int parent_rel_right = TranslateView.getRelRight(parent);
        int parent_center = parent.getWidth()/2;
        int child_width = child.getWidth();


        int rnd = UiUtils.randBw(parent_center-child_width, parent_center+child_width);
        loge("AnimUtil", ""+parent_rel_left, ""+parent_rel_right, ""+parent_center, ""+child_width, ""+rnd);
       /* if(rnd > (parent_rel_right-child.getWidth())){
            return rnd - child.getWidth();
        }
        if(rnd < parent_rel_left+child.getWidth()){
            return rnd + child.getWidth();
        }*/
        return rnd;
    }

    public static void animateMe(LinearLayout parent, ImageView imageView) {

        AnimatorSet animatorSet = new AnimatorSet();
        int parent_h = parent.getHeight()/4;
        int parent_w = parent.getWidth();
        int parent_bottom = TranslateView.getRelBottom(parent);
        int parent_rel_left = TranslateView.getRelLeft(parent);
        int parent_rel_right = TranslateView.getRelRight(parent);

        float dpToPx = (float) DeviceUtils.dpToPx(30);

        animatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, parent_bottom, -parent_h),
               ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, parent_w/2, getRandIn(parent, imageView)),
        });

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animateMe2(parent, imageView);
            }
        });

        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    public static void animateMe2(LinearLayout parent, ImageView imageView) {

        AnimatorSet animatorSet = new AnimatorSet();
        int parent_h = parent.getHeight()/2;
        int parent_w = parent.getWidth();
        int parent_bottom = TranslateView.getRelBottom(parent);
        int parent_rel_left = TranslateView.getRelLeft(parent);
        int parent_rel_right = TranslateView.getRelRight(parent);

        float dpToPx = (float) DeviceUtils.dpToPx(30);

        animatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, parent_h/2, -parent_h),
                ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X,
                        getRandIn(parent, imageView),
                        getRandIn(parent, imageView)),
                ObjectAnimator.ofFloat(imageView, View.ALPHA, 1.0f, 0.1f)
        });

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                parent.removeView(imageView);
            }
        });

        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    public static void topGiftAnimView(Context context, LinearLayout parent, View imageView) {

        int screewidth = DeviceUtils.getScreenWidthPixels(context);
        int viewWidth = imageView.getWidth();
        int center = screewidth/2-viewWidth/2;

        ObjectAnimator lftToCenter = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, screewidth+viewWidth, 0)
                .setDuration(1000); // to animate left to right

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(lftToCenter);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                new Handler().postDelayed(()->{
                    topGiftAnimViewLeft(parent, imageView);
                }, 1000);
            }
        });

        animatorSet.start();

    }

    public static void topGiftAnimViewLeft(LinearLayout parent, View imageView) {

        int viewWidth = imageView.getWidth();

        ObjectAnimator centerToRgt = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, 0, -viewWidth)
                .setDuration(1000); // to animate left to right

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(centerToRgt);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
               parent.removeView(imageView);
            }
        });

        animatorSet.start();

    }

    //////////////////////////////////////////////////////////////////////////////
    public static void sideGiftAnim(LinearLayout parent, View imageView) {

        int viewWidth = imageView.getWidth();

        ObjectAnimator lftToCenter = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, -viewWidth, 0)
                .setDuration(1000); // to animate left to right

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(lftToCenter);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                new Handler().postDelayed(()->{
                    sideGiftAnim_2(parent, imageView);
                }, 1000);

            }
        });

        animatorSet.start();

    }

    public static void sideGiftAnim_2(LinearLayout parent, View imageView) {

        int viewWidth = imageView.getWidth();

        ObjectAnimator lftToCenter = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, 0, -viewWidth)
                .setDuration(1000); // to animate left to right

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(lftToCenter);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                parent.removeView(imageView);
            }
        });

        animatorSet.start();

    }




}
