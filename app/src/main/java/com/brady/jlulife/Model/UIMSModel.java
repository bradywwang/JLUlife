package com.brady.jlulife.Model;

import android.content.Context;
import android.util.Log;

import com.brady.jlulife.Utils.ConstValue;
import com.brady.jlulife.Utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

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
        client.post(ConstValue.SECURITY_CHECK, params, new ResponseHandlerInterface() {
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

            }

            @Override
            public void sendFailureMessage(int i, Header[] headers, byte[] bytes, Throwable throwable) {

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
