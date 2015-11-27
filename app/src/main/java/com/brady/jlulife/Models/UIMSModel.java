package com.brady.jlulife.Models;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.brady.jlulife.Entities.Score.OutSchoolScore.OutSideResult;
import com.brady.jlulife.Entities.Score.OutSchoolScore.OutsideItem;
import com.brady.jlulife.Entities.Score.ScoreValue;
import com.brady.jlulife.Models.Listener.OnAsyncLoadListener;
import com.brady.jlulife.Models.Listener.OnListinfoGetListener;
import com.brady.jlulife.Entities.CourseSpec;
import com.brady.jlulife.Entities.LessonSchedule.LessonSchedules;
import com.brady.jlulife.Entities.LessonSchedule.LessonTeachers;
import com.brady.jlulife.Entities.LessonSchedule.LessonValue;
import com.brady.jlulife.Entities.LessonSchedule.ScheduleRequestSpec;
import com.brady.jlulife.Entities.LessonSchedule.TeachClassMaster;
import com.brady.jlulife.Entities.RequestBody;
import com.brady.jlulife.Entities.ResponseBody;
import com.brady.jlulife.Entities.TermList;
import com.brady.jlulife.Models.Listener.LoginListener;
import com.brady.jlulife.Models.db.DBManager;
import com.brady.jlulife.Utils.ConstValue;
import com.brady.jlulife.Utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpClientConnection;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brady on 15-9-20.
 */
public class UIMSModel {
    private static UIMSModel model;
    private AsyncHttpClient client;
    private LoginListener mLoginListener;
    private int mStudId = 0;
    private String mStudName;
    List<LessonValue> lessonList =null;
    private static DBManager dbManager;
    private static Context sContext;
    private OnAsyncLoadListener mSyncListener;
    public boolean isLogin = false;
    private String mUId;
    private int mLoginMethod;

    public static final int LOGIN_NORMAL_MODE = 0;
    public static final int LOGIN_CJCX_MODE = 1;

    private UIMSModel() {
        client = new AsyncHttpClient();
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
    }

    public static UIMSModel getInstance(Context context) {
        if (model == null) {
            model = new UIMSModel();
        }
        sContext = context;
        dbManager = new DBManager(context);
        return model;
    }

    public void login(final int loginMethod,String uname, String pwd, final LoginListener listener) {
        mLoginListener = listener;
        mUId = uname;
        String convertPwd = Utils.getMD5Str("UIMS" + uname + pwd);
        Log.i(getClass().getSimpleName(), "uname" + uname + "pwd:" + convertPwd);
        RequestParams params = new RequestParams();
        params.put("j_username", uname);
        params.put("j_password", convertPwd);
        String loginURI = "";
        switch (loginMethod){
            case LOGIN_NORMAL_MODE:
            {
                loginURI = ConstValue.SECURITY_CHECK;
                break;
            }case LOGIN_CJCX_MODE:{
                loginURI = ConstValue.CJCX_AUTH_URI;
            }
        }
        client.post(loginURI, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.i(getClass().getSimpleName(), "login failure");
                listener.onLoginFailure("网络连接失败,请检查网络配置");
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (s.contains("error_message")) {
                    processErrMsg(s);
                } else {
                    getCurrentInfo(new OnAsyncLoadListener(){
                        @Override
                        public void onGetInfoSuccess() {
                            listener.onLoginSuccess();
                        }

                        @Override
                        public void onGetInfoFail() {
                            listener.onLoginFailure("获取用户信息失败，请重试");
                        }
                    });
                    mLoginMethod = loginMethod;
                    mLoginListener.onLoginSuccess();
                    isLogin = true;
                }
            }
        });
        client.removeAllHeaders();
    }

    public void logout() {

    }

    public int getmLoginMethod() {
        return mLoginMethod;
    }

    public void getSemesters(final OnListinfoGetListener listener) {
        StringEntity entity = null;
        String resourceURI = null;
        RequestBody body = new RequestBody();
        body.setBranch("default");
        body.setOrderBy("termName desc");
        body.setRes("teachingTerm");
        body.setTag("teachingTerm");
        body.setType("search");
        body.setParams(new Object());
        Log.i(getClass().getSimpleName(), JSON.toJSONString(body));
        try {
            entity = new StringEntity(JSON.toJSONString(body));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        switch (mLoginMethod){
            case LOGIN_NORMAL_MODE:
            {
                resourceURI = ConstValue.RESOURCES_URI;
                break;
            }case LOGIN_CJCX_MODE:{
                resourceURI = ConstValue.CJCX_RESOURCES_URI;
            }
        }

        client.post(sContext, resourceURI, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<TermList> list = null;
                Log.i(getClass().getSimpleName(), response.toString());
                switch (mLoginMethod){
                    case LOGIN_NORMAL_MODE:
                    {
                        ResponseBody body = JSON.parseObject(response.toString(),ResponseBody.class);
                        list = JSON.parseObject(body.getValue(),new TypeReference<ArrayList<TermList>>(){});
                        break;
                    }case LOGIN_CJCX_MODE:{
                        OutSideResult body = JSON.parseObject(response.toString(),OutSideResult.class);
                        list = JSON.parseObject(body.getItems(),new TypeReference<ArrayList<TermList>>(){});
                    }
                }
                listener.onGetInfoSuccess(list);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.onGetInfoFail();
            }
        });
    }
    public void syncLessonSchedule(int semesterId, final OnAsyncLoadListener listener){
        mSyncListener = listener;
        StringEntity entity = null;
        RequestBody body = new RequestBody();
        ScheduleRequestSpec spec = new ScheduleRequestSpec();
        spec.setStudId(mStudId);
        spec.setTermId(semesterId);
        body.setParams(spec);
        body.setBranch("default");
        body.setTag("teachClassStud@schedule");
        try {
            entity = new StringEntity(JSON.toJSONString(body));
            Log.i("body",JSON.toJSONString(body));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(sContext, ConstValue.RESOURCES_URI, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("status") == 0) {
                        ResponseBody body = JSON.parseObject(response.toString(), ResponseBody.class);
                        lessonList = JSON.parseObject(body.getValue(), new TypeReference<ArrayList<LessonValue>>() {
                        });
                        saveCoursesToDb();
                    } else {
                        handleErrMsg(response);
                        mSyncListener.onGetInfoFail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mSyncListener.onGetInfoFail();
                }
                Log.i(getClass().getSimpleName(), response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mSyncListener.onGetInfoFail();
            }
        });
    }

    public void saveCoursesToDb(){
        dbManager.deleteAllItems();
        List<CourseSpec> specs= new ArrayList<CourseSpec>();
        for(LessonValue value:lessonList){
            Log.i("result",value.toString());
            TeachClassMaster master = value.getTeachClassMaster();
            for(LessonSchedules schedule:master.getLessonSchedules()){
                CourseSpec spec = new CourseSpec();
                spec.setBeginWeek(schedule.getTimeBlock().getBeginWeek());
                spec.setEndWeek(schedule.getTimeBlock().getEndWeek());
                spec.setClassRoom(schedule.getClassroom().getFullName());
                spec.setCourseName(master.getLessonSegment().getLesson().getCourseInfo().getCourName());
                spec.setWeek(schedule.getTimeBlock().getDayOfWeek());
                StringBuilder builder = new StringBuilder();
                boolean isBegin = true;
                for (LessonTeachers teacher:master.getLessonTeachers()){
                    if(!isBegin){
                        builder.append(",");
                        isBegin = false;
                    }
                    builder.append(teacher.getTeacher().getName());
                }
                String blockName = schedule.getTimeBlock().getName();
                Log.i("blockname",spec.getCourseName()+blockName);
                String[] times = blockName.split("节");
                if(times.length==2||times.length==1){
                    String[] times2 = times[0].split("第");
                    if(times2.length ==2){
                        String[] timeFinal = times2[1].split(",");
                        spec.setStartTime(Integer.parseInt(timeFinal[0]));
                        spec.setEndTime(Integer.parseInt(timeFinal[timeFinal.length - 1]));
                    }
                }
                    Log.e("blockName",spec.getCourseName()+spec.getStartTime()+""+spec.getEndTime());
                spec.setTeacherName(builder.toString());
                if(blockName.contains("单周")){
                    spec.setIsSingleWeek(1);
                }else {
                    spec.setIsSingleWeek(0);
                }
                if(blockName.contains("双周")){
                    spec.setIsDoubleWeek(1);
                }else {
                    spec.setIsDoubleWeek(0);
                }
                specs.add(spec);
            }
        }
        for(CourseSpec spec:specs){
            Log.i("result",spec.toString());
        }
        dbManager.addAllCourses(specs);
        mSyncListener.onGetInfoSuccess();
    }
    public void getCurrentInfo(final OnAsyncLoadListener listener) {
        client.get(ConstValue.CURRENT_INFO, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    mStudId = response.getInt("userId");
                    mStudName = response.getString("nickName");
                    listener.onGetInfoSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onGetInfoFail();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.onGetInfoFail();
            }
        });
    }

    public void queryScore(int termId,final  OnListinfoGetListener listener){
        switch (mLoginMethod){
            case LOGIN_CJCX_MODE:{
                queryScoresOutside(termId,listener);
                break;
            }case LOGIN_NORMAL_MODE:{
                queryScoreInSchool(termId,listener);
            }
        }
    }

    public void queryScoreInSchool(int termId, final OnListinfoGetListener listener){
        StringEntity entity = null;
        RequestBody body = new RequestBody();
        ScheduleRequestSpec spec = new ScheduleRequestSpec();
        spec.setStudId(mStudId);
        spec.setTermId(termId);
        body.setParams(spec);
        body.setBranch("byTerm");
        body.setOrderBy("teachingTerm.termId, course.courName");
        body.setTag("archiveScore@queryCourseScore");
        try {
            entity = new StringEntity(JSON.toJSONString(body));
            Log.i("body",JSON.toJSONString(body));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(sContext, ConstValue.RESOURCES_URI, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("status") == 0) {
                        ResponseBody body = JSON.parseObject(response.toString(), ResponseBody.class);
                        List<ScoreValue> scoreValues = JSON.parseObject(body.getValue(), new TypeReference<ArrayList<ScoreValue>>() {
                        });
                        List<OutsideItem> items = translateItem(scoreValues);
                        listener.onGetInfoSuccess(items);
                    } else {
                        handleErrMsg(response);
                        listener.onGetInfoFail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onGetInfoFail();
                }
                Log.i(getClass().getSimpleName(), response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                listener.onGetInfoFail();
            }
        });

    }

    public void queryScoresOutside(int termId, final OnListinfoGetListener listener){
        StringEntity entity = null;
        RequestBody body = new RequestBody();
        ScheduleRequestSpec spec = new ScheduleRequestSpec();
        spec.setXh(mUId);
        spec.setTermId(termId);
        body.setParams(spec);
        body.setTag("lessonSelectResult@oldStudScore");
        try {
            entity = new StringEntity(JSON.toJSONString(body));
            Log.i("body",JSON.toJSONString(body));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(sContext, ConstValue.CJCX_RESOURCES_URI, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("status") == 0) {
                        OutSideResult body = JSON.parseObject(response.toString(), OutSideResult.class);
                        List<OutsideItem> items = JSON.parseObject(body.getItems(), new TypeReference<ArrayList<OutsideItem>>() {
                        });
                        listener.onGetInfoSuccess(items);
                    } else {
                        handleErrMsg(response);
                        listener.onGetInfoFail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onGetInfoFail();
                }
                Log.i(getClass().getSimpleName(), response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                listener.onGetInfoFail();
            }
        });
    }

    private void processErrMsg(String s) {
        Document doc = Jsoup.parse(s);
        Element element = doc.getElementById("error_message");
        String txt = element.text();
        client.removeAllHeaders();
        mLoginListener.onLoginFailure(txt);
        Log.i("errMsg", txt);
    }


    private void handleErrMsg(JSONObject response){

    }
    public boolean isLoginIn(){
        return isLogin;
    }
    private List<OutsideItem> translateItem(List<ScoreValue> list){
        List<OutsideItem> items = new ArrayList<OutsideItem>();
        for(ScoreValue scoreValue:list){
            OutsideItem item = new OutsideItem();
            item.setCj(scoreValue.getScore());
            item.setCredit(scoreValue.getCredit());
            item.setGpoint(scoreValue.getgPoint());
            item.setIsReselect(scoreValue.getIsReselect());
            item.setKcmc(scoreValue.getCourse().getCourName());
            items.add(item);
        }
        return items;
    }

}
