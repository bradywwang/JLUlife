package com.brady.jlulife.Models;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.brady.jlulife.CallbackListeners.OnListinfoGetListener;
import com.brady.jlulife.CallbackListeners.OnNewsDetailinfoGetListener;
import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Utils.ConstValue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2015/10/10.
 */
public class JLUNewsModel {
    private static final int MSG_GET_NEWSLIST_SUCCESS = 100001;
    private static final int MSG_GET_NEWSDETAIL_SUECCESS = 100002;
    private static final int MSG_GET_NEWSLIST_FAIL = 100003;
    private static final int MSG_GET_NEWSDETAIL_FAIL = 100004;

    OnListinfoGetListener mListinfoGetListener;
    OnNewsDetailinfoGetListener mNewsDetailinfoGetListener;
    AsyncHttpClient mClient;
    private JLUNewsModel(){
        mClient = new AsyncHttpClient();
    }
    private static JLUNewsModel instance;
    public static JLUNewsModel getInstance(){
        if(instance == null)
            instance = new JLUNewsModel();
        return instance;
    }
    private Handler mHandler = new Handler(){
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
            }
        }
    };
    public void getNewsList(int pageId,OnListinfoGetListener listinfoGetListener){
        mListinfoGetListener = listinfoGetListener;
        String parseURI = ConstValue.NEWS_HOST+"?p="+pageId+"&id=1";
        mClient.get(parseURI, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String parseString = "";
                NewsBaseInfo baseInfo = null;
                List<NewsBaseInfo> list = new ArrayList<NewsBaseInfo>();

                try {
                    parseString = new String(bytes,"gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                    xmlPullParserFactory.setNamespaceAware(true);
                    XmlPullParser parser = xmlPullParserFactory.newPullParser();
                    parser.setInput(new StringReader(parseString));
                    int eventType = parser.getEventType();
                    while(eventType != XmlPullParser.END_DOCUMENT){
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.END_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:{
                                String tagName = parser.getName();
                                if(tagName!=null && tagName.equals("MsgItem"))
                                    baseInfo = new NewsBaseInfo();
                                if(tagName !=null && tagName.equals("CTitle")){
                                    String title = parser.nextText();
                                    baseInfo.setTitle(title);
                                }
                                if(tagName!=null && tagName.equals("CID")){
                                    String cid = parser.nextText();
                                    baseInfo.setHref(cid);
                                }
                                if(tagName!=null && tagName.equals("CAuthor")){
                                    String cAuthor = parser.nextText();
                                    baseInfo.setDep(cAuthor);
                                }
                                if(tagName!=null && tagName.equals("CTime")){
                                    String cTime = parser.nextText();
                                    baseInfo.setDate(cTime);
                                }
                                break;
                            }
                            case XmlPullParser.END_TAG:{
                                if(parser.getName().equals("MsgItem"))
                                    list.add(baseInfo);
                                break;
                            }
                            case XmlPullParser.TEXT:
                                break;
                            default:
                                break;
                        }
                        eventType = parser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.what = MSG_GET_NEWSLIST_SUCCESS;
                msg.obj = list;
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendEmptyMessage(MSG_GET_NEWSLIST_FAIL);
            }
        });
    }
}
