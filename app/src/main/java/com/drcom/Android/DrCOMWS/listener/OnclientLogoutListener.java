package com.drcom.Android.DrCOMWS.listener;

public abstract interface OnclientLogoutListener
{
    public abstract void clientLogoutFail(int paramInt);

    public abstract void clientLogoutSuccess(boolean paramBoolean);
}
