package com.brady.jlulife.Models;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.brady.jlulife.Models.Listener.OnListinfoGetListener;
import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Models.Listener.OnObjectGetListener;
import com.brady.jlulife.Utils.ConstValue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
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
public class JLUNewsModel {
    private static final int MSG_GET_NEWSLIST_SUCCESS = 100001;
    private static final int MSG_GET_NEWSDETAIL_SUCCESS = 100002;
    private static final int MSG_GET_NEWSLIST_FAIL = 100003;
    private static final int MSG_GET_NEWSDETAIL_FAIL = 100004;

    OnListinfoGetListener mListinfoGetListener;
    OnObjectGetListener mNewsDetailinfoGetListener;
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
    public void getNewsDetail(String newsId,OnObjectGetListener newsDetailinfoGetListener){
        mNewsDetailinfoGetListener = newsDetailinfoGetListener;
        String parseURI = ConstValue.NEWS_MORE+"?id="+newsId;
        mClient.get(parseURI, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String parseString = "";
                News news = null;
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
                                    news= new News();
                                if(tagName !=null && tagName.equals("CTitle")){
                                    String title = parser.nextText();
                                    news.setTitle(title);
                                }
                                if(tagName!=null && tagName.equals("CID")){
                                    String cid = parser.nextText();
                                    news.setId(cid);
                                }
                                if(tagName!=null && tagName.equals("CAuthor")){
                                    String cAuthor = parser.nextText();
                                    news.setSubmitDepartment(cAuthor);
                                }
                                if(tagName!=null && tagName.equals("CTime")){
                                    String cTime = parser.nextText();
                                    news.setSubmitTime(cTime);
                                }
                                if(tagName!=null && tagName.equals("CContent")){
                                    String CContent = parser.nextText();
                                    news.setContent(CContent);
                                }
                                if(tagName!=null && tagName.equals("CTop")) {
                                    String CTop = parser.nextText();
                                    news.setcTop(CTop);
                                }
                                if(tagName!=null && tagName.equals("CAttach")) {
                                    String CAttach = parser.nextText();
                                    news.setcAttach(CAttach);
                                }
                                if(tagName!=null && tagName.equals("CAttachName")) {
                                    String CAttachName = parser.nextText();
                                    news.setcAttachName(CAttachName);
                                }
                                break;
                            }
                            case XmlPullParser.END_TAG:{
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
                if(news!=null) {
                    Message msg = new Message();
                    msg.what = MSG_GET_NEWSDETAIL_SUCCESS;
                    msg.obj = news;
                    mHandler.sendMessage(msg);
                }else{
                    mHandler.sendEmptyMessage(MSG_GET_NEWSDETAIL_FAIL);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendEmptyMessage(MSG_GET_NEWSDETAIL_FAIL);
            }
        });
    }
    public void getNewsList(int pageId,OnListinfoGetListener listinfoGetListener){
        mListinfoGetListener = listinfoGetListener;
        String parseURI = ConstValue.NEWS_HOST+"?p="+pageId+"&id=1";
        Log.i("news model","load start");
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
                Log.i("news Mobile","success");
                Message msg = new Message();
                msg.what = MSG_GET_NEWSLIST_SUCCESS;
                msg.obj = list;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendEmptyMessage(MSG_GET_NEWSLIST_FAIL);
            }
        });
    }
}
