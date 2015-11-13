package com.brady.jlulife.Models;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.brady.jlulife.Entities.CourseSpec;
import com.brady.jlulife.Entities.LessonSchedule.LessonSchedules;
import com.brady.jlulife.Entities.LessonSchedule.LessonTeachers;
import com.brady.jlulife.Entities.LessonSchedule.LessonValue;
import com.brady.jlulife.Entities.LessonSchedule.ScheduleRequestSpec;
import com.brady.jlulife.Entities.LessonSchedule.TeachClassMaster;
import com.brady.jlulife.Entities.LessonSchedule.Teacher;
import com.brady.jlulife.Entities.RequestBody;
import com.brady.jlulife.Entities.ResponseBody;
import com.brady.jlulife.Entities.TermList;
import com.brady.jlulife.Models.Listener.LoginListener;
import com.brady.jlulife.Models.db.DBManager;
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

    private UIMSModel() {
        client = new AsyncHttpClient();
    }

    public static UIMSModel getInstance(Context context) {
        if (model == null) {
            model = new UIMSModel();
        }
        dbManager = new DBManager(context);
        return model;
    }

    public void login(String uname, String pwd, final LoginListener listener) {
        mLoginListener = listener;
        String convertPwd = Utils.getMD5Str("UIMS" + uname + pwd);
        Log.i(getClass().getSimpleName(), "uname" + uname + "pwd:" + convertPwd);
        RequestParams params = new RequestParams();
        params.put("j_username", uname);
        params.put("j_password", convertPwd);
        client.post(ConstValue.SECURITY_CHECK, params, new TextHttpResponseHandler() {
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
                    Log.i(getClass().getSimpleName(), "Log success");
                    mLoginListener.onLoginSuccess();
                }
            }
        });
        client.removeAllHeaders();
    }

    public void logout() {

    }

    public void getSemesters(final Context context) {
        StringEntity entity = null;
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
        client.post(context, ConstValue.RESOURCES_URI, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(getClass().getSimpleName(), response.toString());
                ResponseBody body = JSON.parseObject(response.toString(),ResponseBody.class);
                ArrayList<TermList> list = JSON.parseObject(body.getValue(),new TypeReference<ArrayList<TermList>>(){});
                for(TermList term:list){
                    Log.i("TermList",term.getTermId()+term.getTermName());
                }
//                syncCourses(129,context);

                getLessonSchedule(128, context);
            }
        });
    }
    public void getLessonSchedule(int semesterId, final Context context){
        StringEntity entity = null;
        RequestBody body = new RequestBody();
        ScheduleRequestSpec spec = new ScheduleRequestSpec();
        spec.setTermId(semesterId);
        if(mStudId==0){
            getCurrentInfo(context);
        }else {
            spec.setStudId(mStudId);
        }
        body.setParams(spec);
        body.setBranch("default");
        body.setTag("teachClassStud@schedule");
        try {
            entity = new StringEntity(JSON.toJSONString(body));
            Log.i("body",JSON.toJSONString(body));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(context, ConstValue.RESOURCES_URI, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if(response.getInt("status")==0){
                        ResponseBody body = JSON.parseObject(response.toString(), ResponseBody.class);
                        lessonList = JSON.parseObject(body.getValue(), new TypeReference<ArrayList<LessonValue>>() {
                        });
                        syncCourses();
                        for (LessonValue value:lessonList) {
                            Log.i("LessonValue", value.getTeachClassMaster().getLessonSegment().getFullName());
                        }
                    }else{
                        handleErrMsg(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(getClass().getSimpleName(), response.toString());


            }
        });
    }

    public void syncCourses(){
        dbManager.deleteAllItems();
        List<CourseSpec> specs= new ArrayList<CourseSpec>();
        for(LessonValue value:lessonList){
            Log.i("result",value.toString());
            TeachClassMaster master = value.getTeachClassMaster();
            for(LessonSchedules schedule:master.getLessonSchedules()){
                CourseSpec spec = new CourseSpec();
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
                String[] times = blockName.split("节");
                if(times.length==2){
                    String[] times2 = times[0].split("第");
                    if(times2.length ==2){
                        String[] timeFinal = times2[1].split(",");
                        spec.setStartTime(Integer.parseInt(timeFinal[0]));
                        spec.setEndTime(Integer.parseInt(timeFinal[timeFinal.length-1]));
                    }
                }
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
                spec.setBeginWeek(schedule.getTimeBlock().getBeginWeek());
                spec.setEndWeek(schedule.getTimeBlock().getEndWeek());
                spec.setClassRoom(schedule.getClassroom().getFullName());
                spec.setCourseName(master.getLessonSegment().getLesson().getCourseInfo().getCourName());
                specs.add(spec);
            }
        }
        for(CourseSpec spec:specs){
            Log.i("result",spec.toString());
        }
        dbManager.addAllCourses(specs);
    }
    public void getCurrentInfo(Context context) {
        client.get("http://uims.jlu.edu.cn/ntms/action/getCurrentUserInfo.do", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    mStudId = response.getInt("userId");
                    mStudName = response.getString("nickName");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
}
