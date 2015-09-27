package com.brady.jlulife.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Utils.ConstValue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2015/9/27.
 */
public class JWCModel {
    public static List<News> getNewsBaseInfo(final int pageNum, final Context context){
        List<News> newsList = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        /*client.get(ConstValue.JWC_BASIC_INFO+"&page="+pageNum, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(getClass().getSimpleName(),ConstValue.JWC_BASIC_INFO+"&page="+pageNum);
                Log.i(getClass().getSimpleName(), s);
//                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Document doc = Jsoup.parse(s);
                Element element = doc.getElementById("content");
                for (Header header :headers){
                    Log.i("abc",header.getName()+":"+header.getValue());
                }



                String result = "";
                try {
                    result = new String(element.text().getBytes("gbk"),"gb2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("aaaaa", result);

            }
        });*/
        client.get(ConstValue.RESOURCES_URI, new ResponseHandlerInterface() {
            @Override
            public void sendResponseMessage(HttpResponse httpResponse) throws IOException {

            }

            @Override
            public void sendStartMessage() {

            }

            @Override
            public void sendFinishMessage() {

            }

            @Override
            public void sendProgressMessage(long l, long l1) {

            }

            @Override
            public void sendCancelMessage() {

            }

            @Override
            public void sendSuccessMessage(int i, Header[] headers, byte[] bytes) {
                try {
                    String string = new String(bytes,"gbk");
                    Log.i("success string",string);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void sendFailureMessage(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i("success string","failure");

            }

            @Override
            public void sendRetryMessage(int i) {

            }

            @Override
            public URI getRequestURI() {
                return null;
            }

            @Override
            public Header[] getRequestHeaders() {
                return new Header[0];
            }

            @Override
            public void setRequestURI(URI uri) {

            }

            @Override
            public void setRequestHeaders(Header[] headers) {

            }

            @Override
            public void setUseSynchronousMode(boolean b) {

            }

            @Override
            public boolean getUseSynchronousMode() {
                return false;
            }

            @Override
            public void setUsePoolThread(boolean b) {

            }

            @Override
            public boolean getUsePoolThread() {
                return false;
            }

            @Override
            public void onPreProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse) {

            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse) {

            }

            @Override
            public void setTag(Object o) {

            }

            @Override
            public Object getTag() {
                return null;
            }
        });
        return newsList;
    }
}
