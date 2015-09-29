package com.brady.jlulife.Models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.brady.jlulife.CallbackListeners.OnListinfoGetListener;
import com.brady.jlulife.CallbackListeners.OnNewsDetailinfoGetListener;
import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Utils.ConstValue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2015/9/27.
 */
public class JWCModel {
    private News news;
    private OnListinfoGetListener mOnListinfoGetListener;
    private OnNewsDetailinfoGetListener mOnNewsDetailinfoGetListener;
    private static JWCModel model;
    private static final int MSG_GET_NEWSLIST_SUCCESS = 100001;
    private static final int MSG_GET_NEWSDETAIL_SUECCESS = 100002;
    private static final int MSG_GET_NEWSLIST_FAIL = 100003;
    private static final int MSG_GET_NEWSDETAIL_FAIL = 100004;



    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_GET_NEWSLIST_SUCCESS:{
                    mOnListinfoGetListener.onGetInfoSuccess((List) msg.obj);
                    break;
                }
                case MSG_GET_NEWSLIST_FAIL:{
                    mOnListinfoGetListener.onGetInfoFail();
                    break;
                }
                case MSG_GET_NEWSDETAIL_SUECCESS:{
                    mOnNewsDetailinfoGetListener.onGetInfoSuccess((News) msg.obj);
                    break;
                }

                case MSG_GET_NEWSDETAIL_FAIL:{
                    mOnNewsDetailinfoGetListener.onGetInfoFail();
                    break;
                }
            }
        }
    };


    private JWCModel(){

    }

    public static JWCModel getInstance() {
        if(model ==null)
            model = new JWCModel();
        return model;
    }
    public void getNewsBaseInfo(final int pageNum,OnListinfoGetListener listinfoGetListener){
        List<NewsBaseInfo> newsList = null;
        AsyncHttpClient client = new AsyncHttpClient();
        mOnListinfoGetListener = listinfoGetListener;

        client.get(ConstValue.JWC_BASIC_INFO + "&page=" + pageNum, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                List<NewsBaseInfo> newsList = new ArrayList<>();
                String parseString = "";
                try {
                    parseString = new String(bytes,"gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Document doc = Jsoup.parse(parseString);
                Element element = doc.getElementById("content");
                Elements elements = element.getElementsByTag("li");
                for(Element ele:elements){
                    NewsBaseInfo baseInfo = new NewsBaseInfo();
                    Elements eles1 = ele.getElementsByTag("a");
                    Element ele0 = eles1.get(0);
                    String title = ele0.text();
                    String href = ele0.attr("href");
                    String date = ele.getElementsByClass("right").text();
                    String dep = eles1.get(1).text();
                    Log.i("title:",title);
                    Log.i("href:",href);
                    Log.i("date:",date);
                    Log.i("dep:",dep);
                    baseInfo.setTitle(title);
                    baseInfo.setDate(date);
                    baseInfo.setDep(dep);
                    baseInfo.setHref(href);
                    newsList.add(baseInfo);
                }
                Message message = new Message();
                message.what = MSG_GET_NEWSLIST_SUCCESS;
                message.obj = newsList;
                mhandler.sendMessage(message);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mhandler.sendEmptyMessage(MSG_GET_NEWSLIST_FAIL);
            }
        });
    }
    public void getNewsContent(String href,OnNewsDetailinfoGetListener listener){
        mOnNewsDetailinfoGetListener = listener;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstValue.JWC_HOST + href, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String parseString = "";
                news = new News();

                try {
                    parseString = new String(bytes,"gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("parsestring",parseString);
                Document doc = Jsoup.parse(parseString);
                Elements contents = doc.getElementsByClass("content");
                Element content = contents.get(0);
                String title = content.getElementsByTag("h4").text();
                String info = content.getElementsByClass("info").text();
                StringBuilder builder = new StringBuilder();
//                Element ele = content.getElementsByTag("div").get(1);
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
                news.setTitle(title);
                news.setContent(contentText);
                news.setSubmitDepartment(dep);
                news.setSubmitTime(date);
                Message msg = new Message();
                msg.what = MSG_GET_NEWSDETAIL_SUECCESS;
                msg.obj = news;
                mhandler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mhandler.sendEmptyMessage(MSG_GET_NEWSDETAIL_FAIL);
            }
        });
    }
}
