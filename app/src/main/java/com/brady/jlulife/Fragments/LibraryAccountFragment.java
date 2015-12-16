package com.brady.jlulife.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;

import org.apache.http.util.EncodingUtils;

/**
 * Created by brady on 15-12-15.
 */
public class LibraryAccountFragment extends BaseFragment {
    private WebView webView;
    private Context mContext;
    private static LibraryAccountFragment instance;
    public static LibraryAccountFragment getInstance(){
        if(instance == null){
            instance = new LibraryAccountFragment();
        }return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        webView = (WebView) view.findViewById(R.id.lib_view);
        initWebView();
        showLendItems();
    }
    public boolean canGoBack() {
        return webView.canGoBack();
    }

    public void preformBack() {
        webView.goBack();
    }

    private void initWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideDialog();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;//true表示此事件在此处被处理，不需要再广播
            }

            @Override   //转向错误时的处理
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
            }
        });
    }
    private void showLendItems(){
        Bundle arguments = getArguments();
        String uname = "";
        String pwd = "";
        if(arguments!=null){
            uname = arguments.getString("uname");
            pwd = arguments.getString("pwd");
        }
        synCookies(mContext,ConstValue.LIBRARY_ACCOUNT_WEB);
        String postData = "backurl=/cmpt/opac/opacLink.jspx?backurl=http%3A%2F%2F202.198.25.5%3A8080%2Fsms%2Fopac%" +
                "2Fuser%2FlendStatus.action%3Fxc%3D3&shcoolid=920&userType=0&username=" + uname + "&password=" + pwd;
        webView.postUrl(ConstValue.LIBRARY_ACCOUNT_WEB, EncodingUtils.getBytes(postData, "base64"));
    }

    public static void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        String newCookie = cookieManager.getCookie(url);
        if(newCookie != null){
        }
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        StringBuilder sbCookie = new StringBuilder();
        sbCookie.append(String.format("xc=%s","5"));
        sbCookie.append(String.format(";mgid=%s", "274"));
        sbCookie.append(String.format(";maid=%s", "920"));

        String cookieValue = sbCookie.toString();



//        String cookies = "xc=5; mgid=274; maid=920;";
        cookieManager.setCookie(url, "xc=5");//cookies是在HttpClient中获得的cookie
        cookieManager.setCookie(url,"mgid=274");
        cookieManager.setCookie(url,"maid=920");
        CookieSyncManager.getInstance().sync();
        newCookie = cookieManager.getCookie(url);
        if(newCookie != null){
        }
    }
}
