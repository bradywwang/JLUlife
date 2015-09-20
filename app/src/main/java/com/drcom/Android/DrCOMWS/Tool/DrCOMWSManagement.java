package com.drcom.Android.DrCOMWS.Tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.brady.jlulife.R;
import com.drcom.Android.DrCOMWS.Jni;
import com.drcom.Android.DrCOMWS.listener.OnclientLoginListener;
import com.drcom.Android.DrCOMWS.listener.OnclientLogoutListener;
import com.drcom.Android.DrCOMWS.listener.OnclientStatusRefreshListener;

public class DrCOMWSManagement {
    private static int CALLBACK_FAIL = 0;
    private static int CALLBACK_NOWIFI = 2;
    private static int CALLBACK_SUCCEED = 1;
    private static int NOWIFI_CODE = -101;
    private Jni auth = new Jni();
    private Context mContext;
    private Handler mHandlerLogin = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            super.handleMessage(paramAnonymousMessage);
            if (paramAnonymousMessage.what == DrCOMWSManagement.CALLBACK_SUCCEED) {
                DrCOMWSManagement.this.mLoginListener.clientLoginSuccess(DrCOMWSManagement.this.auth.getLoginStatus());
            }else {
                if (paramAnonymousMessage.what == DrCOMWSManagement.CALLBACK_NOWIFI) {
                    Log.i(getClass().getSimpleName(), "reason 1");
                    DrCOMWSManagement.this.mLoginListener.clientLoginFail(paramAnonymousMessage.arg1);

                } else {
                    Log.i(getClass().getSimpleName(), "reason 2" + paramAnonymousMessage.what);
                    DrCOMWSManagement.this.mLoginListener.clientLoginFail(paramAnonymousMessage.arg1);
                }
            }
        }
    };
    private Handler mHandlerLogout = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            super.handleMessage(paramAnonymousMessage);
            if (paramAnonymousMessage.what == DrCOMWSManagement.CALLBACK_SUCCEED) {
                DrCOMWSManagement.this.mLogoutListener.clientLogoutSuccess(DrCOMWSManagement.this.auth.getLoginStatus());
            }else {
                DrCOMWSManagement.this.mLogoutListener.clientLogoutFail(paramAnonymousMessage.arg1);
            }
        }
    };
    private Handler mHandlerStatusRefresh = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            super.handleMessage(paramAnonymousMessage);
            if (paramAnonymousMessage.what == DrCOMWSManagement.CALLBACK_SUCCEED) {
                String[] arrayOfString = (String[]) paramAnonymousMessage.obj;
                DrCOMWSManagement.this.mStatusRefreshListener.clientStatusUpdated(arrayOfString[0], arrayOfString[1]);
            }else {
                DrCOMWSManagement.this.mStatusRefreshListener.clientOffLine();
            }
        }
    };
    private OnclientLoginListener mLoginListener;
    private OnclientLogoutListener mLogoutListener;
    private OnclientStatusRefreshListener mStatusRefreshListener;
    private SharedPreferences m_spfConf = null;
    private String m_strGWAddress = "";

    public DrCOMWSManagement(Context paramContext) {
        this.mContext = paramContext;
        this.m_spfConf = this.mContext.getSharedPreferences("DrCOMClientWS", 0);
        this.m_strGWAddress = getPreferences("DrCOMUrl");
        if (!this.m_strGWAddress.equals("")) {
            this.auth.setGatwatAddress(this.m_strGWAddress);
        }

    }

    private String getPreferences(String paramString) {
        Object localObject = this.m_spfConf.getString(paramString, "");

        if (((String) localObject).length() > 0)
            try {
                Log.e("Localobj", "length" + localObject.toString().length());
                Log.e("LocalObj", localObject.toString());
                String str = AES128.decrypt("DrCOMClientWS", (String) localObject);
                localObject = str;
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        if (localObject == null) {
            Log.e(getClass().getSimpleName(), "local object is null");
            return "";
        }
        return (String) localObject;
    }

    private boolean putPreferences(String paramString1, String paramString2) {
        Object localObject = "";
        try {
            String str = AES128.encrypt("DrCOMClientWS", paramString2);
            localObject = str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        SharedPreferences.Editor localEditor;

        localEditor = this.m_spfConf.edit();
        localEditor.putString(paramString1, (String) localObject);
        return localEditor.commit();
    }

    public void clientLogin(final String paramString1, final String paramString2, OnclientLoginListener paramOnclientLoginListener) {
        Log.e(getClass().getSimpleName(), "start login");
        this.mLoginListener = paramOnclientLoginListener;
        new Thread(new Runnable() {
            public void run() {
                WifiManager localWifiManager = (WifiManager) DrCOMWSManagement.this.mContext.getSystemService(Context.WIFI_SERVICE);
                int i;
                if (localWifiManager.isWifiEnabled()) {
                    WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
                    if (localWifiInfo != null) {
                        DrCOMWSManagement.this.auth.setSSID(localWifiInfo.getSSID());
                    }
                    i = DrCOMWSManagement.this.auth.httpLogin(DrCOMWSManagement.this.m_strGWAddress, paramString1, paramString2);
                    Log.e(getClass().getSimpleName(), "i=" + i);
                    if (i == 1) {
                        DrCOMWSManagement.this.m_strGWAddress = DrCOMWSManagement.this.auth.getGatewayAddress();
                        DrCOMWSManagement.this.putPreferences("DrCOMUrl", DrCOMWSManagement.this.m_strGWAddress);
                        DrCOMWSManagement.this.mHandlerLogin.sendEmptyMessage(DrCOMWSManagement.CALLBACK_SUCCEED);
                    } else {
                        Message localMessage2 = new Message();
                        localMessage2.what = DrCOMWSManagement.CALLBACK_FAIL;
                        localMessage2.arg1 = i;
                        DrCOMWSManagement.this.mHandlerLogin.sendMessage(localMessage2);
                    }

                } else {
                    Message localMessage1 = new Message();
                    localMessage1.what = DrCOMWSManagement.CALLBACK_NOWIFI;
                    localMessage1.arg1 = DrCOMWSManagement.NOWIFI_CODE;
                    DrCOMWSManagement.this.mHandlerLogin.sendMessage(localMessage1);
                }
            }
        }).start();
    }

    public void clientLogout(OnclientLogoutListener paramOnclientLogoutListener) {
        this.mLogoutListener = paramOnclientLogoutListener;
        new Thread(new Runnable() {
            public void run() {
                int i = DrCOMWSManagement.this.auth.httpLogout();
                if (i == 1) {
                    DrCOMWSManagement.this.m_strGWAddress = "";
                    DrCOMWSManagement.this.mHandlerLogout.sendEmptyMessage(DrCOMWSManagement.CALLBACK_SUCCEED);
                }
                else {
                    Message localMessage = new Message();
                    localMessage.what = DrCOMWSManagement.CALLBACK_FAIL;
                    localMessage.arg1 = i;
                    DrCOMWSManagement.this.mHandlerLogout.sendMessage(localMessage);
                }
            }
        }).start();
    }

    public void clientStatusRefresh(OnclientStatusRefreshListener paramOnclientStatusRefreshListener) {
        this.mStatusRefreshListener = paramOnclientStatusRefreshListener;
        new Thread(new Runnable() {
            public void run() {
                boolean bool = DrCOMWSManagement.this.auth.httpStatus();
                if (bool) {
                    String str1 = DrCOMWSManagement.this.auth.getTimeStatus();
                    String str2 = DrCOMWSManagement.this.auth.getFluxStatus();
                    Message localMessage = new Message();
                    localMessage.what = DrCOMWSManagement.CALLBACK_SUCCEED;
                    String[] arrayOfString = new String[2];
                    arrayOfString[0] = str1;
                    arrayOfString[1] = str2;
                    localMessage.obj = arrayOfString;
                    DrCOMWSManagement.this.mHandlerStatusRefresh.sendMessage(localMessage);
                }
                DrCOMWSManagement.this.mHandlerStatusRefresh.sendEmptyMessage(DrCOMWSManagement.CALLBACK_FAIL);
            }
        }).start();
    }

    public int getErrorStringByCode(int paramInt) {
        switch (paramInt){
            case -130:
                return R.string.err_wrong_pwd;
            case -108:
                return R.string.err_wrong_uname;
            case -102:
                return R.string.err_use_nat;
            case -104:
                return R.string.err_is_login;
            case -101:
                return R.string.err_network_unavailable;
            default:
                return R.string.err_default;
        }

    }

    public String getPortalIP() {
        return this.m_strGWAddress;
    }

    public String getTimeStatus() {
        return auth.getTimeStatus();
    }

    public String getFlowStatus() {
        return auth.getFluxStatus();
    }

    public boolean getLoginStatus() {
        return auth.getLoginStatus();
    }
}
