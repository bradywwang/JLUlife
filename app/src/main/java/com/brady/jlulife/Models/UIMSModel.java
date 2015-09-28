package com.brady.jlulife.Models;

import android.content.Context;
import android.util.Log;

import com.brady.jlulife.Utils.ConstValue;
import com.brady.jlulife.Utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by brady on 15-9-20.
 */
public class UIMSModel {
    private static UIMSModel model;
    private AsyncHttpClient client;

    private UIMSModel(){
        client = new AsyncHttpClient();
    }
    public static UIMSModel getInstance(){
        if(model ==null){
            model = new UIMSModel();
        }
        return model;
    }

    public void login(String uname,String pwd){
        String convertPwd = Utils.getMD5Str("UIMS"+uname+pwd);
        Log.i(getClass().getSimpleName(), "pwd:" + convertPwd);
        RequestParams params = new RequestParams();
        params.put("j_username", uname);
        params.put("j_password", convertPwd);
        client.post(ConstValue.SECURITY_CHECK, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.i(getClass().getSimpleName(),"login failure");
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(getClass().getSimpleName(),"login success");
                Log.i(getClass().getSimpleName(),s);
            }
        });

    }

    public void logout(){

    }

    public JSONObject getSemesters (Context context){
        JSONObject requestJson = new JSONObject();
        StringEntity entity = null;
        try {
            requestJson.put("branch","default");
            requestJson.put("orderBy","termName desc");
            requestJson.put("parms",new JSONObject());
            requestJson.put("res","teachingTerm");
            requestJson.put("taq","teachingTerm");
            requestJson.put("type","search");
            Log.i(getClass().getSimpleName(), requestJson.toString());
            entity = new StringEntity(requestJson.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        client.post(context,ConstValue.RESOURCES_URI,entity,"application/json",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(getClass().getSimpleName(),response.toString());
            }
        });
        return null;
    }

    public JSONObject getCurrentInfo(Context context){
        JSONObject requestJson = new JSONObject();
        StringEntity entity = null;
        try {
            requestJson.put("branch","default");
            requestJson.put("orderBy","termName desc");
            requestJson.put("parms",new JSONObject());
            requestJson.put("res","teachingTerm");
            requestJson.put("taq","teachingTerm");
            requestJson.put("type","search");
            Log.i(getClass().getSimpleName(), requestJson.toString());
            entity = new StringEntity(requestJson.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        client.get("http://uims.jlu.edu.cn/ntms/action/getCurrentUserInfo.do", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(getClass().getSimpleName(), response.toString());
            }
        });
        return null;
    }
}
