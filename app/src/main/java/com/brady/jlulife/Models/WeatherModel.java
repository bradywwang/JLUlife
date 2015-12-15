package com.brady.jlulife.Models;

import android.content.Context;
import android.util.Log;

import com.brady.jlulife.Utils.ConstValue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.commons.codec.Decoder;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by brady on 15-12-15.
 */
public class WeatherModel {
    private static WeatherModel instance;
    AsyncHttpClient client;
    private static Context sContext;

    private WeatherModel() {
        client = new AsyncHttpClient();
    }

    public static WeatherModel getInstance(Context context){
        if(instance == null){
            instance = new WeatherModel();
            sContext = context;
        }
        return instance;
    }

    public void getWeatherList(){
        Header header = new BasicHeader("apikey","46700072a5005c1812bb88c6fd98a6aa");
        String quaryParm = URLEncoder.encode(ConstValue.BAIDU_API_WEATHER_LIST+"?cityname=长春");
        client.get(sContext, quaryParm, new Header[]{header}, new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    Log.e("query string",new String(bytes,"GBK"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }


        });
    }
    public void getWeatherDetail(){
        String cityId =ConstValue.BAIDU_API_WEATHER_CITY_ID;
        String querystring = URLEncoder.encode("http://apis.baidu.com/apistore/weatherservice/cityid?cityid=101060101");
        Header header = new BasicHeader("apikey","46700072a5005c1812bb88c6fd98a6aa");
        client.get(sContext, querystring, new Header[]{header}, new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    Log.e("query string",new String(bytes,"GBK"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }


        });
    }
}
