package com.brady.jlulife.Models;

import android.os.Message;
import android.util.Log;

import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Models.Listener.OnListinfoGetListener;
import com.brady.jlulife.Models.Listener.OnObjectGetListener;
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
 * Created by brady on 15-11-21.
 */
public class JWNewsModel extends NewsModel{

    private static JWNewsModel instance;
    public static JWNewsModel getInstance(){
        if(instance == null)
            instance = new JWNewsModel();
        return instance;
    }



    @Override
    public void getNewsDetail(String newsId, OnObjectGetListener newsDetailinfoGetListener) {
        mNewsDetailinfoGetListener = newsDetailinfoGetListener;

        initClient();

        mClient.get(ConstValue.JWC_HOST + newsId, new AsyncHttpResponseHandler() {
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

    @Override
    public void getNewsList(int pageId, OnListinfoGetListener listinfoGetListener) {
        initClient();
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
    }

}
