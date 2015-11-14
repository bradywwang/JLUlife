package com.brady.jlulife.Models.Listener;

import com.brady.jlulife.Entities.News;

import java.util.List;

/**
 * Created by wang on 2015/9/28.
 */
public abstract interface OnNewsDetailinfoGetListener {
    public abstract void onGetInfoSuccess(News news);
    public abstract void onGetInfoFail();

}
