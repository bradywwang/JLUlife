package com.brady.androidherotest.MeasureView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by brady on 15-12-9.
 */
public class CustomedTextView extends TextView {
    private Paint mPaint1;
    private Paint mPaint2;
    private int mViewWidth;
    private Matrix mGradientMatrix;
    private int mTranslate;
    private LinearGradient mLinearGradient;

    public CustomedTextView(Context context) {
        super(context);
    }

    public CustomedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.support.v7.appcompat.R.color.material_blue_grey_800));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(getResources().getColor(android.support.v7.appcompat.R.color.foreground_material_dark));
        mPaint2.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, mPaint2);
        canvas.save();
        canvas.translate(10, 0);
        super.onDraw(canvas);
        canvas.restore();
        if(mGradientMatrix!=null){
            mTranslate += mVi2ewWidth/5;
            if(mTranslate>2*mViewWidth){
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate,0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if(mViewWidth>0){
                Paint paint = getPaint();
                mLinearGradient = new LinearGradient(0,0,mViewWidth,0,new int[]{Color.BLUE,0xffffffff,Color.BLUE},null, Shader.TileMode.CLAMP);
                paint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }


}
