package com.deificindia.indifun1.screenutils.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.ViewPropertyAnimator;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;

import com.deificindia.indifun1.screenutils.Utils;
//import base.common.utils.Utils;

public class ViewAnimatorUtil {
    private ViewAnimatorUtil() {
    }

    public static void cancelAnimator(Animator animator) {
        if (Utils.ensureNotNull(animator)) {
            animator.cancel();
        }
    }

    public static void removeListeners(Animator animator) {
        if (Utils.ensureNotNull(animator)) {
            animator.removeAllListeners();
            if (animator instanceof ValueAnimator) {
                ((ValueAnimator) animator).removeAllUpdateListeners();
            }
        }
    }

    public static void cancelAnimator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        if (Utils.ensureNotNull(viewPropertyAnimatorCompat)) {
            viewPropertyAnimatorCompat.cancel();
        }
    }

    public static void cancelAnimator(ViewPropertyAnimator viewPropertyAnimator) {
        if (Utils.ensureNotNull(viewPropertyAnimator)) {
            viewPropertyAnimator.cancel();
        }
    }

    public static void removeListeners(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        if (Utils.ensureNotNull(viewPropertyAnimatorCompat)) {
            viewPropertyAnimatorCompat.setListener((ViewPropertyAnimatorListener) null);
            viewPropertyAnimatorCompat.setUpdateListener((ViewPropertyAnimatorUpdateListener) null);
        }
    }

    public static void cancelAnimator(Animator animator, boolean z) {
        if (Utils.ensureNotNull(animator)) {
            if (z) {
                removeListeners(animator);
            }
            animator.cancel();
            if (!z) {
                removeListeners(animator);
            }
        }
    }

    public static void removeListeners(ViewPropertyAnimator viewPropertyAnimator) {
        if (Utils.ensureNotNull(viewPropertyAnimator)) {
            viewPropertyAnimator.setListener((Animator.AnimatorListener) null);
            if (Build.VERSION.SDK_INT >= 19) {
                viewPropertyAnimator.setUpdateListener((ValueAnimator.AnimatorUpdateListener) null);
            }
        }
    }

    public static void cancelAnimator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, boolean z) {
        if (Utils.ensureNotNull(viewPropertyAnimatorCompat)) {
            if (z) {
                removeListeners(viewPropertyAnimatorCompat);
            }
            viewPropertyAnimatorCompat.cancel();
            if (!z) {
                removeListeners(viewPropertyAnimatorCompat);
            }
        }
    }

    public static void cancelAnimator(ViewPropertyAnimator viewPropertyAnimator, boolean z) {
        if (Utils.ensureNotNull(viewPropertyAnimator)) {
            if (z) {
                removeListeners(viewPropertyAnimator);
            }
            viewPropertyAnimator.cancel();
            if (!z) {
                removeListeners(viewPropertyAnimator);
            }
        }
    }
}
