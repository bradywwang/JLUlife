package com.brady.jlulife.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Utils.ConstValue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.LogInterface;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2015/9/27.
 */
public class JWCModel {
    News news;


    private static JWCModel model;
    private JWCModel(){

    }

    public static JWCModel getInstance() {
        if(model ==null)
            model = new JWCModel();
        return model;
    }
    public static List<NewsBaseInfo> getNewsBaseInfo(final int pageNum, final Context context){
        List<NewsBaseInfo> newsList = null;
        AsyncHttpClient client = new AsyncHttpClient();
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
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
        return newsList;
    }
    public News getNewsContent(String href){
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
                Elements elements = content.getElementsByTag("span");
                for(Element element:elements){
                    builder.append(element.text());
                    builder.append("\n");
                }
                String contentText = builder.toString();
                Log.i("title", title);
                Log.i("info",info);
                Log.i("content",contentText);
                String[] arr = info.split(" 发表于");
                String dep = arr[0];
                String date = arr[1].substring(0,19);
                Log.i("dep",dep);
                Log.i("date",date);
                news.setTitle(title);
                news.setContent(contentText);
                news.setSubmitDepartment(dep);
                news.setSubmitTime(date);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return news;
    }
}
