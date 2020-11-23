package com.deificindia.indifun1.screenutils.ab;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.animation.AnimationUtils;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

public final class b {
    private final int a;
    private final boolean b;
    private int c;
    private long d;

    /* renamed from: e  reason: collision with root package name */
    private float f4863e;

    /* renamed from: f  reason: collision with root package name */
    private float f4864f;

    /* renamed from: g  reason: collision with root package name */
    private float f4865g;

    /* renamed from: h  reason: collision with root package name */
    private float f4866h;

    /* renamed from: i  reason: collision with root package name */
    private Drawable f4867i;

    /* renamed from: j  reason: collision with root package name */
    private boolean f4868j;

    /* renamed from: k  reason: collision with root package name */
    private final PointF f4869k = new PointF();

    /* renamed from: l  reason: collision with root package name */
    private final PointF f4870l = new PointF();

    public b(int i2, boolean z) {
        this.a = i2;
        this.b = z;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    private static float a(long j2) {
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - j2;
        if (currentAnimationTimeMillis >= 2500) {
            return 1.0f;
        }
        return (float) (1.0d - Math.pow((double) (1.0f - (((float) currentAnimationTimeMillis) / 2500.0f)), 1.100000023841858d));
    }

    private static int b(float f2) {
        if (f2 > 0.5f) {
            return (int) (Math.min(1.0f, (1.0f - f2) * 2.0f) * 255.0f);
        }
        return 255;
    }

    private float d(float f2) {
        float f3 = 1.0f - f2;
        float f4 = 3.0f * f3;
        return (f3 * f3 * f3 * this.f4863e) + (f3 * f4 * f2 * this.f4869k.x) + (f4 * f2 * f2 * this.f4870l.x) + (f2 * f2 * f2 * this.f4865g);
    }

    private float e(float f2) {
        float f3 = 1.0f - f2;
        float f4 = 3.0f * f3;
        return (f3 * f3 * f3 * this.f4864f) + (f3 * f4 * f2 * this.f4869k.y) + (f4 * f2 * f2 * this.f4870l.y) + (f2 * f2 * f2 * this.f4866h);
    }

    /* access modifiers changed from: package-private */
    public void c(@NonNull Canvas canvas, int i2, int i3) {
        float f2;
        int i4;
        int i5;
        if (this.f4867i != null) {
            int i6 = this.c;
            if (i6 == 1) {
                this.c = 2;
                f2 = 0.0f;
                this.d = AnimationUtils.currentAnimationTimeMillis();
            } else if (i6 == 2) {
                f2 = a(this.d);
            } else {
                return;
            }
            if (f2 < 0.2f) {
                i4 = Math.round(((((float) this.a) * f2) / 0.2f) / 2.0f);
            } else {
                i4 = this.a / 2;
            }
            int d2 = (int) d(f2);
            int e2 = (int) e(f2);
            this.f4867i.setAlpha(b(f2));
            this.f4867i.setBounds(d2 - i4, e2 - i4, d2 + i4, i4 + e2);
            if (this.f4868j) {
                double d3 = (double) (360.0f * f2);
                Double.isNaN(d3);
                float sin = (float) (Math.sin((d3 * 3.141592653589793d) / 90.0d) * 20.0d);
                if (Build.VERSION.SDK_INT >= 21) {
                    i5 = canvas.saveLayer(0.0f, 0.0f, (float) i2, (float) i3, (Paint) null);
                } else {
                    i5 = canvas.saveLayer(0.0f, 0.0f, (float) i2, (float) i3, (Paint) null, ALL_SAVE_FLAG/*31*/);
                }
                canvas.rotate(sin, (float) d2, (float) e2);
                this.f4867i.draw(canvas);
                canvas.restoreToCount(i5);
            } else {
                this.f4867i.draw(canvas);
            }
            if (f2 >= 1.0f) {
                this.c = 3;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void f(int i2, int i3, Drawable drawable, boolean z) {
        this.c = 1;
        this.f4867i = drawable;
        this.f4868j = z;
        int i4 = this.a;
        int i5 = i2 - i4;
        float f2 = ((float) i4) / 2.0f;
        float f3 = (float) i2;
        float f4 = 0.65f * f3;
        double random = Math.random();
        double d2 = (double) i5;
        Double.isNaN(d2);
        float round = ((float) Math.round(random * d2)) + f2;
        float f5 = (float) i3;
        this.f4864f = f5;
        this.f4866h = f2;
        double d3 = (double) (((float) i5) * 0.2f);
        Double.isNaN(d3);
        double random2 = (Math.random() - 0.5d) * 2.0d * d3;
        double d4 = (double) f4;
        Double.isNaN(d4);
        float round2 = (float) Math.round(random2 + d4);
        double random3 = Math.random();
        double d5 = (double) (round - round2);
        Double.isNaN(d5);
        double d6 = random3 * d5;
        double d7 = (double) round2;
        Double.isNaN(d7);
        float round3 = (float) Math.round(d6 + d7);
        float round4 = (float) Math.round(0.75f * f5);
        float round5 = (float) Math.round(f5 * 0.5f);
        if (this.b) {
            round2 = f3 - round2;
            round3 = f3 - round3;
            this.f4863e = f3 - f4;
            this.f4865g = f3 - round;
        } else {
            this.f4863e = f4;
            this.f4865g = round;
        }
        this.f4869k.set(round2, round4);
        this.f4870l.set(round3, round5);
    }

    /* access modifiers changed from: package-private */
    public boolean g() {
        return this.c == 3;
    }

    /* access modifiers changed from: package-private */
    public void h() {
        this.c = 0;
        this.d = 0;
        this.f4867i = null;
        this.f4868j = false;
        this.f4863e = 0.0f;
        this.f4864f = 0.0f;
        this.f4865g = 0.0f;
        this.f4866h = 0.0f;
        this.f4869k.set(0.0f, 0.0f);
        this.f4870l.set(0.0f, 0.0f);
    }
}
