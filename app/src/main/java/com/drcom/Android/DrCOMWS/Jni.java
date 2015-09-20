package com.drcom.Android.DrCOMWS;

public class Jni {
    private static final String LIBRARY_NAME = "DrClientLib";

    static {
        try {
            System.loadLibrary("DrClientLib");
        } catch (Exception localException) {
            for (; ; ) {
                localException.printStackTrace();
            }
        }
    }

    @SuppressWarnings("JniMissingFunction")
    public native String getData();

    @SuppressWarnings("JniMissingFunction")
    public native String getFluxStatus();

    @SuppressWarnings("JniMissingFunction")
    public native String getGatewayAddress();

    @SuppressWarnings("JniMissingFunction")
    public native boolean getLoginStatus();

    @SuppressWarnings("JniMissingFunction")
    public native String getMac();

    @SuppressWarnings("JniMissingFunction")
    public native String getTimeStatus();

    @SuppressWarnings("JniMissingFunction")
    public native String getUnfineError();

    @SuppressWarnings("JniMissingFunction")
    public native String getXip();

    @SuppressWarnings("JniMissingFunction")
    public native int httpLogin(String paramString1, String paramString2, String paramString3);

    @SuppressWarnings("JniMissingFunction")
    public native int httpLoginAuth();

    @SuppressWarnings("JniMissingFunction")
    public native int httpLoginCheck();

    @SuppressWarnings("JniMissingFunction")
    public native int httpLogout();

    @SuppressWarnings("JniMissingFunction")
    public native boolean httpStatus();

    @SuppressWarnings("JniMissingFunction")
    public native void setGatwatAddress(String paramString);

    @SuppressWarnings("JniMissingFunction")
    public native boolean setSSID(String paramString);
}
