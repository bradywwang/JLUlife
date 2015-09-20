package com.drcom.Android.DrCOMWS.listener;

public abstract interface OnclientStatusRefreshListener
{
    public abstract void clientOffLine();

    public abstract void clientStatusUpdated(String paramString1, String paramString2);
}
