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
public class NewsModel {
    private static final int MSG_GET_NEWSLIST_SUCCESS = 100001;
    private static final int MSG_GET_NEWSDETAIL_SUCCESS = 100002;
    private static final int MSG_GET_NEWSLIST_FAIL = 100003;
    private static final int MSG_GET_NEWSDETAIL_FAIL = 100004;
    private News mNews;
    OnListinfoGetListener mListinfoGetListener;
    OnObjectGetListener mNewsDetailinfoGetListener;
    AsyncHttpClient mClient;
    private NewsModel(){
        mClient = new AsyncHttpClient();
    }
    private static NewsModel instance;
    public static NewsModel getInstance(){
        if(instance == null)
            instance = new NewsModel();
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
    public void getNewsDetail(String action,String newsId,OnObjectGetListener newsDetailinfoGetListener){
        mNewsDetailinfoGetListener = newsDetailinfoGetListener;

        switch (action){
            case ConstValue.FUNCTION_JLUNEWS:{
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
                break;
            }case ConstValue.FUNCTION_JWCX:{
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(ConstValue.JWC_HOST + newsId, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String parseString = "";
                        mNews = new News();
                        try {
                            parseString = new String(bytes,"gbk");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Document doc = Jsoup.parse(parseString);
                        Elements contents = doc.getElementsByClass("content");
                        Element content = contents.get(0);
                        String title = content.getElementsByTag("h4").text();
                        String info = content.getElementsByClass("info").text();
                        StringBuilder builder = new StringBuilder();
                        Elements elements = content.getElementsByTag("div");
                        for(int j=0;j<elements.size();j++){
                            if(j==0)
                                continue;
                            Element element = elements.get(j);
                            String text = element.text();
                            text.replace("\n","");
                            builder.append(text);
                            builder.append("\n");
                        }
                        Elements eles = content.getElementsByTag("p");
                        for(int j=0;j<eles.size();j++){
                            if (j==0)
                                continue;
                            Element element = eles.get(j);
                            String text = element.text();
                            text.replace("\n","");
                            text.replace("     ","  ");
                            builder.append(text);
                            builder.append("\n");
                        }
                        String contentText = builder.toString();
//                String cont = elements.html();
//                String contentText = cont.replace("\n","</p>");
                        Log.i("title", title);
                        Log.i("info",info);
                        Log.i("content",contentText);
                        String[] arr = info.split(" 发表于");
                        String dep = arr[0];
                        String date = arr[1].substring(0,19);
                        Log.i("dep",dep);
                        Log.i("date", date);
                        mNews.setTitle(title);
                        mNews.setContent(contentText);
                        mNews.setSubmitDepartment(dep);
                        mNews.setSubmitTime(date);
                        Message msg = new Message();
                        msg.what = MSG_GET_NEWSDETAIL_SUCCESS;
                        msg.obj = mNews;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        mHandler.sendEmptyMessage(MSG_GET_NEWSDETAIL_FAIL);
                    }
                });
            }
        }




    }
    public void getNewsList(String action,int pageId,OnListinfoGetListener listinfoGetListener){
        if(!TextUtils.isEmpty(action)) {
            switch (action) {
                case ConstValue.FUNCTION_JLUNEWS: {
                    mListinfoGetListener = listinfoGetListener;
                    String parseURI = ConstValue.NEWS_HOST + "?p=" + pageId + "&id=1";
                    Log.i("news model", "load start");
                    mClient.get(parseURI, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            String parseString = "";
                            NewsBaseInfo baseInfo = null;
                            List<NewsBaseInfo> list = new ArrayList<NewsBaseInfo>();

                            try {
                                parseString = new String(bytes, "gbk");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            try {
                                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                                xmlPullParserFactory.setNamespaceAware(true);
                                XmlPullParser parser = xmlPullParserFactory.newPullParser();
                                parser.setInput(new StringReader(parseString));
                                int eventType = parser.getEventType();
                                while (eventType != XmlPullParser.END_DOCUMENT) {
                                    switch (eventType) {
                                        case XmlPullParser.START_DOCUMENT:
                                            break;
                                        case XmlPullParser.END_DOCUMENT:
                                            break;
                                        case XmlPullParser.START_TAG: {
                                            String tagName = parser.getName();
                                            if (tagName != null && tagName.equals("MsgItem"))
                                                baseInfo = new NewsBaseInfo();
                                            if (tagName != null && tagName.equals("CTitle")) {
                                                String title = parser.nextText();
                                                baseInfo.setTitle(title);
                                            }
                                            if (tagName != null && tagName.equals("CID")) {
                                                String cid = parser.nextText();
                                                baseInfo.setHref(cid);
                                            }
                                            if (tagName != null && tagName.equals("CAuthor")) {
                                                String cAuthor = parser.nextText();
                                                baseInfo.setDep(cAuthor);
                                            }
                                            if (tagName != null && tagName.equals("CTime")) {
                                                String cTime = parser.nextText();
                                                baseInfo.setDate(cTime);
                                            }
                                            break;
                                        }
                                        case XmlPullParser.END_TAG: {
                                            if (parser.getName().equals("MsgItem"))
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
                            Log.i("news Mobile", "success");
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
                    break;
                }
                case ConstValue.FUNCTION_JWCX: {

                    List<NewsBaseInfo> newsList = null;
                    AsyncHttpClient client = new AsyncHttpClient();
                    mListinfoGetListener = listinfoGetListener;

                    client.get(ConstValue.JWC_BASIC_INFO + "&page=" + pageId, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            List<NewsBaseInfo> newsList = new ArrayList<>();
                            String parseString = "";
                            try {
                                parseString = new String(bytes, "gbk");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Document doc = Jsoup.parse(parseString);
                            Element element = doc.getElementById("content");
                            Elements elements = element.getElementsByTag("li");
                            for (Element ele : elements) {
                                NewsBaseInfo baseInfo = new NewsBaseInfo();
                                Elements eles1 = ele.getElementsByTag("a");
                                Element ele0 = eles1.get(0);
                                String title = ele0.text();
                                String href = ele0.attr("href");
                                String date = ele.getElementsByClass("right").text();
                                String dep = eles1.get(1).text();
                                Log.i("title:", title);
                                Log.i("href:", href);
                                Log.i("date:", date);
                                Log.i("dep:", dep);
                                baseInfo.setTitle(title);
                                baseInfo.setDate(date);
                                baseInfo.setDep(dep);
                                baseInfo.setHref(href);
                                newsList.add(baseInfo);
                            }
                            Message message = new Message();
                            message.what = MSG_GET_NEWSLIST_SUCCESS;
                            message.obj = newsList;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            mHandler.sendEmptyMessage(MSG_GET_NEWSLIST_FAIL);
                        }
                    });
                    break;
                }
            }
        }



    }
}
