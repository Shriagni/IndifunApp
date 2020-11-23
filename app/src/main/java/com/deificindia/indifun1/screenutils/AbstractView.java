package com.deificindia.indifun1.screenutils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

public abstract class AbstractView extends View {
    private final float mDensity = getResources().getDisplayMetrics().density;

    public AbstractView(@NonNull Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public final float getDensity() {
        return this.mDensity;
    }

    /* access modifiers changed from: protected */
    public final int getDimen(float f2) {
        return Math.round(f2 * this.mDensity);
    }

    /* access modifiers changed from: protected */
    /*public final boolean isRtlLayoutDirection() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }*/

    public AbstractView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AbstractView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
