package com.brady.androidherotest.MeasureView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by brady on 15-12-9.
 */
public class CustomedView extends View{
    private String TAG = getClass().getSimpleName();

    public CustomedView(Context context) {
        super(context);
    }

    public CustomedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomedView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureWidth(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec){
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);
        switch (specMode){
            case MeasureSpec.EXACTLY:{
                result=specSize;
                break;
            }case MeasureSpec.UNSPECIFIED:{
                result = 200;
            }case MeasureSpec.AT_MOST:{
                result = 200;
                Log.i(TAG,result+"size"+specSize);
                result = Math.min(result,specSize);
            }
        }
        return result;
    }
}
