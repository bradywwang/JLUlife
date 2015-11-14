package com.brady.jlulife.Models.Listener;

import com.brady.jlulife.Entities.News;

import java.util.List;

/**
 * Created by wang on 2015/9/28.
 */
public abstract interface OnObjectGetListener {
    public abstract void onGetInfoSuccess(Object object);
    public abstract void onGetInfoFail();

}
