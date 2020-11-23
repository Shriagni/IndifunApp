package com.deificindia.indifun1.screenutils.ab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import com.deificindia.indifun1.anim.HeartFloatingView;
import com.deificindia.indifun1.screenutils.Utils;
import com.deificindia.indifun1.screenutils.utils.ViewAnimatorUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class a extends AnimatorListenerAdapter implements Runnable {
    private final int a;
    private View b;
    private final List<b> c = new ArrayList();
    private ValueAnimator d;

    private Runnable f4862e;

    public a(int i2, @NonNull HeartFloatingView heartFloatingView) {
        this.a = i2;
        this.b = heartFloatingView;
    }

    private void b() {
        if (Utils.nonNull(this.d)) {
            ValueAnimator valueAnimator = this.d;
            this.d = null;
            ViewAnimatorUtil.cancelAnimator((Animator) valueAnimator, false);
        }
    }

    private void f() {
        if (Utils.nonNull(this.f4862e)) {
            Runnable runnable = this.f4862e;
            this.f4862e = null;
            if (Utils.nonNull(this.b)) {
                this.b.removeCallbacks(runnable);
            }
        }
    }

    private void g() {
        f();
        b();
    }

    /* access modifiers changed from: package-private */
    public void a(@NonNull b bVar, @NonNull Drawable drawable, int i2, int i3, @NonNull ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        f();
        bVar.f(i2, i3, drawable, this.a == -1);
        this.c.add(bVar);
        if (Utils.isNull(this.d)) {
            /*if (j.a.a.a) {
                Log.d("FloatingDrawHelper", "Start current Animator；helperId = " + this.a);
            }*/
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 100});
            this.d = ofInt;
            ofInt.setDuration(2500);
            ofInt.setRepeatCount(-1);
            ofInt.addUpdateListener(animatorUpdateListener);
            ofInt.addListener(this);
            ofInt.start();
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        g();
        this.c.clear();
    }

    /* access modifiers changed from: package-private */
    public void d(Canvas canvas, int i2, int i3, @NonNull HeartFloatingView heartFloatingView) {
        if (!this.c.isEmpty()) {
            Iterator<b> it = this.c.iterator();
            while (it.hasNext()) {
                b next = it.next();
                next.c(canvas, i2, i3);
                if (next.g()) {
                    it.remove();
                    /*if (j.a.a.a) {
                        Log.d("FloatingDrawHelper", "item已完成，并进行回收复用；helperId = " + this.a);
                    }*/
                    heartFloatingView.d(next);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void e(@NonNull LinkedList<b> linkedList) {
        g();
        int size = this.c.size();
        if (size > 0) {
            int size2 = 15 - linkedList.size();
            if (size2 > 0) {
                int min = Math.min(size2, size);
                for (int i2 = 0; i2 < min; i2++) {
                    b bVar = this.c.get(i2);
                    bVar.h();
                    linkedList.add(bVar);
                }
            }
            this.c.clear();
        }
    }

    public void onAnimationRepeat(Animator animator) {
        super.onAnimationRepeat(animator);
        if (Utils.nonNull(this.d) && Utils.nonNull(this.b) && this.c.isEmpty()) {
            /*if (j.a.a.a) {
                Log.d("FloatingDrawHelper", "onAnimationRepeat；没有可绘制的item，停止当前Animator；helperId = " + this.a);
            }*/
            View view = this.b;
            this.f4862e = this;
            view.post(this);
        }
    }

    public void run() {
        /*if (j.a.a.a) {
            Log.d("FloatingDrawHelper", "停止当前Animator；helperId = " + this.a);
        }*/
        this.f4862e = null;
        b();
    }
}
