package com.brady.jlulife;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by brady on 15-11-27.
 */
public interface OnScrollListener {
    public void OnScrollChanged(int x,int y,int oldx,int oldy);
}
