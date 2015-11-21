package com.brady.jlulife.Models;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.brady.jlulife.Models.Listener.OnListinfoGetListener;
import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Models.Listener.OnObjectGetListener;
import com.brady.jlulife.Utils.ConstValue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2015/10/10.
 */
public abstract class NewsModel {
    protected static final int MSG_GET_NEWSLIST_SUCCESS = 100001;
    protected static final int MSG_GET_NEWSDETAIL_SUCCESS = 100002;
    protected static final int MSG_GET_NEWSLIST_FAIL = 100003;
    protected static final int MSG_GET_NEWSDETAIL_FAIL = 100004;
    protected News mNews;
    OnListinfoGetListener mListinfoGetListener;
    OnObjectGetListener mNewsDetailinfoGetListener;
    AsyncHttpClient mClient;

    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
            case MSG_GET_NEWSLIST_SUCCESS:{
                mListinfoGetListener.onGetInfoSuccess((List) msg.obj);
                break;
            }
            case MSG_GET_NEWSLIST_FAIL: {
                mListinfoGetListener.onGetInfoFail();
                break;
            }
                case MSG_GET_NEWSDETAIL_SUCCESS:{
                    mNewsDetailinfoGetListener.onGetInfoSuccess((News) msg.obj);
                    break;
                }
                case MSG_GET_NEWSDETAIL_FAIL:{
                    mNewsDetailinfoGetListener.onGetInfoFail();
                }
            }
        }
    };

    public abstract void getNewsDetail(String newsId,OnObjectGetListener newsDetailinfoGetListener);



    public abstract void getNewsList(int pageId,OnListinfoGetListener listinfoGetListener);




    protected void initClient(){
        if(mClient==null)
            mClient = new AsyncHttpClient();
    }
}
