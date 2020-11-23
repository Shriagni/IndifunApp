package com.deificindia.indifun1.anim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
//import base.common.utils.Utils;
//import base.widget.fragment.a;
import com.deificindia.indifun1.screenutils.ab.a;
import com.deificindia.indifun1.screenutils.ab.b;

import com.deificindia.indifun1.screenutils.AbstractView;
import com.deificindia.indifun1.screenutils.LiveLikeEntity;
import com.deificindia.indifun1.screenutils.Utils;
//import com.mico.live.utils.k;
//import com.mico.model.vo.live.LiveLikeEntity;
import java.util.LinkedList;

import static android.view.View.MeasureSpec.EXACTLY;
//import widget.nice.common.abs.AbstractView;

public class HeartFloatingView extends AbstractView implements ValueAnimator.AnimatorUpdateListener {
    private boolean a1;
    private LinkedList<b> b1 = new LinkedList<>();
    private SparseArray<a> c1 = new SparseArray<>();
    private ArrayMap<String, Drawable> d1 = new ArrayMap<>();

    /* renamed from: e  reason: collision with root package name */
    private boolean f4860e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f4861f = true;

    public HeartFloatingView(@NonNull Context context) {
        super(context);
        this.a1 = com.deificindia.indifun1.screenutils.abc.a.g(context);
    }

    @NonNull
    private b a() {
        b pollFirst = !this.b1.isEmpty() ? this.b1.pollFirst() : null;
        if (Utils.isNull(pollFirst)) {
            return new b(getDimen(32.0f), this.a1);
        }
        pollFirst.h();
        return pollFirst;
    }

    private void b(int i2, @NonNull Drawable drawable, int i3, int i4) {
        a aVar;
        a aVar2 = this.c1.get(i2);
        if (Utils.isNull(aVar2)) {
            SparseArray<a> sparseArray = this.c1;
            a aVar3 = new a(i2, this);
            sparseArray.put(i2, aVar3);
            aVar = aVar3;
        } else {
            aVar = aVar2;
        }
        aVar.a(a(), drawable, i3, i4, this);
    }

    private void e(boolean z) {
        int size = this.c1.size();
        int i2 = 0;
        if (!z) {
            while (i2 < size) {
                this.c1.valueAt(i2).c();
                i2++;
            }
            return;
        }
        while (i2 < size) {
            this.c1.valueAt(i2).e(this.b1);
            i2++;
        }
        invalidate();
    }

    public void c() {
        e(true);
    }

    /* access modifiers changed from: package-private */
    public void d(@NonNull b bVar) {
        bVar.h();
        if (this.b1.size() < 15) {
            this.b1.add(bVar);
        }
    }

    public void f(LiveLikeEntity liveLikeEntity) {
        /*if (this.f4860e && this.f4861f && !Utils.isNull(liveLikeEntity)) {
            int width = getWidth();
            int height = getHeight();
            if (width > 0 && height > 0) {
                int i2 = -1;
                if (!TextUtils.isEmpty(liveLikeEntity.remote_id)) {
                    Drawable f2 = k.b().f(liveLikeEntity.remote_id, true, this.d1);
                    if (Utils.nonNull(f2)) {
                        b(-1, f2, width, height);
                        return;
                    }
                } else {
                    i2 = liveLikeEntity.local_id;
                }
                int length = k.d.length;
                if (i2 < 0 || i2 >= length) {
                    double random = Math.random();
                    double d2 = (double) length;
                    Double.isNaN(d2);
                    i2 = (int) (random * d2);
                }
                b(i2, k.b().d(i2, this.d1), width, height);
            }
        }*/
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f4860e = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f4860e = false;
        e(false);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.c1.size();
        if (size > 0) {
            int width = getWidth();
            int height = getHeight();
            for (int i2 = 0; i2 < size; i2++) {
                this.c1.valueAt(i2).d(canvas, width, height, this);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(Math.round(((float) View.getDefaultSize(getSuggestedMinimumHeight(), i3)) * 0.55f), EXACTLY));
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(@NonNull View view, int i2) {
        super.onVisibilityChanged(view, i2);
        this.f4861f = i2 == 0;
    }

    public HeartFloatingView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a1 = com.deificindia.indifun1.screenutils.abc.a.g(context);
    }

    public HeartFloatingView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.a1 = com.deificindia.indifun1.screenutils.abc.a.g(context);
    }
}
