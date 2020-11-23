package com.deificindia.indifun1.ui.imagepicker.util;

import android.graphics.Bitmap;


public class BitmapUtil {   

    public static Bitmap resizeBitmap(Bitmap src, int max) {
        if(src == null)
            return null;
        
        int width = src.getWidth();
        int height = src.getHeight();
        float rate = 0.0f;

        if (width > height) {
            rate = max / (float) width;
            height = (int) (height * rate);
            width = max;
        } else {
            rate = max / (float) height;
            width = (int) (width * rate);
            height = max;
        }

        return Bitmap.createScaledBitmap(src, width, height, true);           
    }
    

    public static Bitmap resize(Bitmap src, int max, boolean isKeep) {
        if(!isKeep)
            return resizeBitmap(src, max);
        
        int width = src.getWidth();
        int height = src.getHeight();
        float rate = 0.0f;
        
        if (width > height) {
            if (max > width) {
                rate = max / (float) width;
                height = (int) (height * rate);
                width = max;
            }
        } else {
            if (max > height) {
                rate = max / (float) height;
                width = (int) (width * rate);
                height = max;
            }
        }
 
        return Bitmap.createScaledBitmap(src, width, height, true);
    }
    

    public static Bitmap resizeSquare(Bitmap src, int max) {
        if(src == null)
            return null;
        
        return Bitmap.createScaledBitmap(src, max, max, true);
    }
    

    public static Bitmap cropCenterBitmap(Bitmap src, int w, int h) {
        if(src == null)
            return null;
        
        int width = src.getWidth();
        int height = src.getHeight();
                
        if(width < w && height < h)
            return src;
        
        int x = 0;
        int y = 0;
        
        if(width > w)
            x = (width - w)/2;
        
        if(height > h)
            y = (height - h)/2;
        
        int cw = w; // crop width
        int ch = h; // crop height
        
        if(w > width)
            cw = width;
        
        if(h > height)
            ch = height;
        

        return Bitmap.createBitmap(src, x, y, cw, ch);
    }
    
    
    
    
    
    
    
    
}