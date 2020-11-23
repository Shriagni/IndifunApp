package com.deificindia.indifun1.ui.imagepicker.view;

import android.content.Context;
import android.util.AttributeSet;

public class CustomSquareImageView extends androidx.appcompat.widget.AppCompatImageView {



    public CustomSquareImageView(Context context) {
        super(context);
    }

    public CustomSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    //Squares the thumbnail
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);

    }




}
