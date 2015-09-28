package com.brady.jlulife.CallbackListeners;

import java.util.List;

/**
 * Created by wang on 2015/9/28.
 */
public abstract interface OnListinfoGetListener {
    public abstract void onGetInfoSuccess(List list);
    public abstract void onGetInfoFail();

}
