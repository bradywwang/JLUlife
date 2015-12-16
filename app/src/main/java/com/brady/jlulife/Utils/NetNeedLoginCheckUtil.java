package com.brady.jlulife.Utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by brady on 15-12-16.
 */
public class NetNeedLoginCheckUtil extends AsyncTask<Integer, Integer, Boolean> {

    NeedLoginCallBack callBack;

    public NetNeedLoginCheckUtil(NeedLoginCallBack callBack) {
        super();
        this.callBack = callBack;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        return isWifiSetPortal();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (callBack != null) {
            callBack.needLogin(result);
        }
    }

    private boolean isWifiSetPortal() {
        final String mWalledGardenUrl = "http://connect.rom.miui.com/generate_204";
        final int WALLED_GARDEN_SOCKET_TIMEOUT_MS = 10000;

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(mWalledGardenUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setConnectTimeout(WALLED_GARDEN_SOCKET_TIMEOUT_MS);
            urlConnection.setReadTimeout(WALLED_GARDEN_SOCKET_TIMEOUT_MS);
            urlConnection.setUseCaches(false);
            urlConnection.getInputStream();
            return urlConnection.getResponseCode() != 204;
        } catch (IOException e) {
            return false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public static void needLoginNetworkCheck(NeedLoginCallBack callBack) {
        new NetNeedLoginCheckUtil(callBack).execute();
    }

    public interface NeedLoginCallBack{
        void needLogin(boolean needLogin);
    }
}