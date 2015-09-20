package com.drcom.Android.DrCOMWS.listener;

public abstract interface OnclientLoginListener
{
    public abstract void clientLoginFail(int paramInt);

    public abstract void clientLoginSuccess(boolean paramBoolean);
}
