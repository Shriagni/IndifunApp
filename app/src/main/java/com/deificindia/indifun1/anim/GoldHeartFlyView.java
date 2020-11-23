package com.deificindia.indifun1.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
//import base.common.utils.ResourceUtils;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.screenutils.DeviceUtils;
import com.deificindia.indifun1.screenutils.ResourceUtils;
import com.deificindia.indifun1.screenutils.ViewUtil;
import com.deificindia.indifun1.screenutils.g;
import com.deificindia.indifun1.screenutils.j;
//import com.mico.common.logger.DebugLog;
//import com.mico.common.util.DeviceUtils;
//import f.b.b.g;
//import j.a.i;
import java.util.concurrent.CancellationException;
//import kotlin.coroutines.c;
//import kotlin.jvm.internal.j;
import kotlinx.coroutines.CoroutineStart;
//import kotlinx.coroutines.f1;
//import kotlinx.coroutines.o0;
//import kotlinx.coroutines.y0;
//import widget.ui.view.utils.ViewUtil;

public final class GoldHeartFlyView extends FrameLayout {
    private LayoutParams a;
    private final int b;
    //private f1 c;

    public static final class a implements Animator.AnimatorListener {
        final /* synthetic */ GoldHeartFlyView a;

        a(GoldHeartFlyView goldHeartFlyView) {
            this.a = goldHeartFlyView;
        }

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationEnd(Animator animator) {
            this.a.f();
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }
    }

    static final class bb implements OnClickListener {
        public static final bb a = new bb();

        bb() {
        }

        public final void onClick(View view) {
        }
    }

    public GoldHeartFlyView(Context context) {
        this(context, (AttributeSet) null, 0, 6);
    }

    public GoldHeartFlyView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public GoldHeartFlyView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        //j.c(context, "context");

        this.b = Math.min(ResourceUtils.getScreenHeight(context), ResourceUtils.getScreenWidth(context));
    }

    /* access modifiers changed from: private */
    public final void d(ImageView imageView, float f2, float f3, float f4, float f5, boolean z) {
        imageView.setPivotX(0.0f);
        imageView.setPivotY(0.0f);
        LayoutParams layoutParams = this.a;
        if (layoutParams != null) {
            addView(imageView, layoutParams);
            g.h(imageView, R.drawable.golden_heart_1);
            AnimatorSet animatorSet = new AnimatorSet();
            float dpToPx = (float) DeviceUtils.dpToPx(30);
            float f6 = dpToPx / f5;
            float f7 = dpToPx / ((float) 2);
            animatorSet.playTogether(new Animator[]{
                    ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, new float[]{0.0f, (f3 - f2) - f7}),
                    ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, new float[]{0.0f, (f4 - f7) - ((((float) this.b) / 2.0f) - (f5 / 2.0f))}),
                    ObjectAnimator.ofFloat(imageView, View.SCALE_X, new float[]{1.0f, f6}),
                    ObjectAnimator.ofFloat(imageView, View.SCALE_Y, new float[]{1.0f, f6})
            });
            if (z) {
                animatorSet.addListener(new a(this));
            }
            animatorSet.setDuration(500);
            animatorSet.start();
            return;
        }
        j.m("layoutParams");
        throw null;
    }

    private final void e() {
        View view = new View(getContext());
        view.setOnClickListener(bb.a);
        addView(view, 0, new LayoutParams(-1, -1));
    }

    /* access modifiers changed from: private */
    public final void f() {
        ViewUtil.removeChild(this);
    }

    /* access modifiers changed from: private */
    public final ImageView g() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
    }

    public final void h(int i2, int i3, int i4, int i5) {
        int i6 = i2;
        int i7 = i5;
        //DebugLog.d("GoldHeartFlyView:coordinateY=" + i6 + ",endY=" + i3 + ",endX=" + i4 + ",heartSize=" + i7);
        e();
        LayoutParams layoutParams = new LayoutParams(i7, i7);
        this.a = layoutParams;
        if (layoutParams != null) {
            layoutParams.gravity = 1;
            if (layoutParams != null) {
                layoutParams.topMargin = i6;
                //this.c = e.b(y0.a, o0.c(), (CoroutineStart) null, new GoldHeartFlyView$startAnim$1(this, i2, i3, i4, i5, (c) null), 2, (Object) null);
                return;
            }
            j.m("layoutParams");
            throw null;
        }
        j.m("layoutParams");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
       /* f1 f1Var = this.c;
        if (f1Var != null) {
            f1.a.a(f1Var, (CancellationException) null, 1, (Object) null);
        }*/
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ GoldHeartFlyView(Context context, AttributeSet attributeSet, int i2, int i3) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
