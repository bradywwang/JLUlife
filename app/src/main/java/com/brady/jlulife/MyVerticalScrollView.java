package com.brady.jlulife;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by brady on 15-11-27.
 */
public class MyVerticalScrollView extends ScrollView {
    private OnScrollListener listener;

    public MyVerticalScrollView(Context context) {
        super(context);
    }

    public MyVerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyVerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnScrollListener(OnScrollListener scrollListener){
        listener = scrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        listener.OnScrollChanged(l,t,oldl,oldt);
    }
}
