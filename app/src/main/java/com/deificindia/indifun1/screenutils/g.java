package com.deificindia.indifun1.screenutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

public class g {
    public static void a(Bitmap bitmap) {
        try {
            if (!Utils.isNull(bitmap) && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (Throwable th) {
            //Ln.e(th);
        }
    }

    public static void b(Bitmap... bitmapArr) {
        for (Bitmap a : bitmapArr) {
            a(a);
        }
    }

    public static void c(Bitmap bitmap, ImageView imageView) {
        a(bitmap);
        d(imageView);
    }

    public static void d(ImageView imageView) {
        try {
            if (!Utils.isNull(imageView)) {
                imageView.setImageResource(0);
            }
        } catch (Throwable th) {
            //Ln.e(th);
        }
    }

    public static void e(ImageView... imageViewArr) {
        for (ImageView d : imageViewArr) {
            d(d);
        }
    }

    public static Bitmap f(Context cnx, int i2) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inDither = false;
            options.inPurgeable = true;
            options.inSampleSize = 1;
            //AppInfoUtils appInfoUtils = AppInfoUtils.INSTANCE;
            return BitmapFactory.decodeResource(cnx.getResources(), i2, options);
        } catch (Exception e2) {
            //Ln.e((Throwable) e2);
            return null;
        } catch (OutOfMemoryError unused) {
            System.gc();
            return null;
        }
    }

    public static void g(ImageView imageView, Bitmap bitmap) {
        try {
            if (!Utils.isNull(bitmap) && !bitmap.isRecycled()) {
                imageView.setImageBitmap(bitmap);
            }
        } catch (Exception e2) {
            //Ln.e((Throwable) e2);
        } catch (OutOfMemoryError unused) {
            System.gc();
        }
    }

    public static void h(ImageView imageView, int i2) {
        try {
            if (!Utils.isNull(imageView)) {
                imageView.setImageResource(i2);
            }
        } catch (Throwable th) {
            //Ln.e(th);
        }
    }

    public static Bitmap i(Context cnx, ImageView imageView, int i2) {
        Bitmap f2 = f(cnx, i2);
        g(imageView, f2);
        return f2;
    }

    public static void j(View view, int i2) {
        try {
            if (!Utils.isNull(view)) {
                view.setBackgroundResource(i2);
            }
        } catch (Throwable th) {
            //Ln.e(th);
        }
    }

    public static void k(View view, int i2) {
        try {
            if (!Utils.isNull(view)) {
                view.setBackgroundColor(i2);
            }
        } catch (Throwable th) {
            //Ln.e(th);
        }
    }
}
