package com.deificindia.indifun1.screenutils.abc;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Menu;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.deificindia.indifun1.screenutils.Utils;

public final class a {
    public static <T> T a(Object obj, @NonNull Class<T> cls) {
        if (cls.isInstance(obj)) {
            //return obj;
        }
        return null;
    }

    @Deprecated
    public static void b(Context context, View view) {
        if (g(context)) {
            view.setRotationY(180.0f);
        }
    }

    public static void c(Menu menu) {
        if (menu instanceof MenuBuilder) {
            try {
                //((MenuBuilder) menu).setOptionalIconsVisible(true);
            } catch (Throwable th) {
                //Ln.e(th);
            }
        }
    }

    public static <T> T d(@NonNull Fragment fragment, Class<T> cls) {
        FragmentActivity activity = fragment.getActivity();
        if (cls.isInstance(activity)) {
            return cls.cast(activity);
        }
        return null;
    }

    public static <T> T e(@NonNull Fragment fragment, Class<T> cls) {
        Fragment parentFragment = fragment.getParentFragment();
        if (cls.isInstance(parentFragment)) {
            return cls.cast(parentFragment);
        }
        return null;
    }

    public static int f(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static boolean g(Context context) {
        if (context == null || Build.VERSION.SDK_INT < 17 || context.getResources().getConfiguration().getLayoutDirection() != 1) {
            return false;
        }
        return true;
    }

    public static void h(Activity activity) {
        try {
            activity.getWindow().setFlags(1024, 1024);
        } catch (Throwable th) {
            //Ln.e(th);
        }
    }

    public static void i(Activity activity, float f2) {
        if (!Utils.isNull(activity) && f2 >= 0.0f) {
            activity.getWindow().setDimAmount(Math.min(f2, 1.0f));
        }
    }
}
