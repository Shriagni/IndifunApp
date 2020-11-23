package com.deificindia.indifun1.screenutils;

import android.view.View;
import android.view.ViewGroup;

import com.deificindia.indifun1.ui.like.Utils;

public class ViewUtil {
    public static void removeChild(View view) {
        ViewGroup viewGroup;
        if (view!=null && (viewGroup = (ViewGroup) view.getParent()) != null) {
            viewGroup.removeView(view);
        }
    }
}
