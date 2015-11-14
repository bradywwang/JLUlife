package com.brady.jlulife.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.brady.jlulife.Entities.CourseSpec;
import com.brady.jlulife.Models.db.DBHelper;
import com.brady.jlulife.Models.db.DBManager;
import com.brady.jlulife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends Fragment {

    private RelativeLayout mCourseContent;
    private DBManager dbManager;


    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBaseComponents(view);
        dbManager = new DBManager(getActivity());
//        initTestData();
//        showCourses(courseSpecs);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<CourseSpec> courseSpecs = dbManager.queryAllCourses();
        showCourses(courseSpecs);
    }

    private void initBaseComponents(View view){
        view.scrollTo(0, 0);
        GridView head = (GridView) view.findViewById(R.id.gridWeek);
        String[] weeks = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
        ArrayAdapter<String> headAdap = new ArrayAdapter<String>(getActivity(),
                R.layout.weekstyle, weeks);
        head.setAdapter(headAdap);

        // 第几节课
        GridView courseNum = (GridView) view.findViewById(R.id.gridCourseNum);
        String[] courseNums = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11" };
        ArrayAdapter<String> leftAdap = new ArrayAdapter<String>(getActivity(),
                R.layout.coursenumstyle, courseNums);
        courseNum.setAdapter(leftAdap);

        mCourseContent = (RelativeLayout) view.findViewById(R.id.relativeCourseContent);
//        addCourse(new CourseSpec("毛概","f9","张三",3,3,4,1,8,false));
    }
    private void addCourse(CourseSpec spec){
        int leftMargin = 0;
        int topMargin = 0;
        int courseLength = 0;
        int courseWidth = (int) (getResources()
                .getDimension(R.dimen.course_week_item_width));
        // 一节课的高度
        int courseHeight = (int) (getResources()
                .getDimension(R.dimen.course_num_item_height));
        // week的高度
        int week_item_height = (int) (getResources()
                .getDimension(R.dimen.course_week_height));
        // courseNum的宽度
        int courseNum_width = (int) (getResources()
                .getDimension(R.dimen.course_num_width));
        // 行(列)间距
        int spacing = (int) (getResources().getDimension(R.dimen.spacing));
        // 算出左边距=左边有几节课*（课程宽度+1px的间隔）+左侧编号宽度
        leftMargin = (spec.getWeek() - 1) * (courseWidth + spacing) + courseNum_width;
        // 上边距=上边有几节课*（课程高度+1px的间隔）+顶部星期的宽度
        topMargin = (spec.getStartTime() - 1) * (courseHeight + spacing)
                + week_item_height;
        // 课程长度=课程节数*(课程高度+1px的间隔)-1个像素
        courseLength = (spec.getEndTime() - spec.getStartTime() + 1)
                * (courseHeight + spacing) - spacing;
        Button course = new Button(getActivity());
        RelativeLayout.LayoutParams marginParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        marginParams.setMargins(leftMargin,topMargin,0,0);
        course.setLayoutParams(marginParams);
        course.setGravity(Gravity.CENTER);
        course.setWidth(courseWidth);
        course.setHeight(courseLength);
        course.setBackgroundColor(getResources().getColor(R.color.courseColor));
        course.setPadding(8,8,8,8);
        course.setText(spec.toString());
        course.setTextSize(15);
        course.setTextColor(Color.WHITE);
        mCourseContent.addView(course);
    }

//    public CourseSpec(String courseName, String classRoom, String teacherName, int week, int startTime, int endTime, int beginWeek, int endWeek, int isSingleWeek, int isDoubleWeek) {
    private void initTestData(){
        List<CourseSpec> list = new ArrayList<CourseSpec>();
        list.add(new CourseSpec("毛概","f9","张三",3,3,4,1,8,0,0));
        list.add(new CourseSpec("毛111概","f9","张三",4,3,4,1,8,0,0));
        dbManager.addAllCourses(list);
    }

    public void showCourses(List<CourseSpec>courseSpecList){
        for (CourseSpec spec :courseSpecList){
            addCourse(new CourseSpec(spec.getCourseName(),spec.getClassRoom(),spec.getTeacherName(),spec.getWeek(),spec.getStartTime(),spec.getEndTime(),spec.getBeginWeek(),spec.getEndWeek(),spec.getIsSingleWeek(),spec.getIsDoubleWeek()));
        }
    }
}
