package com.brady.jlulife.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SpinnerAdapter;

import com.brady.jlulife.SlidingMenuMainActivity;
import com.brady.jlulife.Entities.CourseSpec;
import com.brady.jlulife.Models.db.DBManager;
import com.brady.jlulife.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends BaseFragment {

    private RelativeLayout mCourseContent;
    private DBManager dbManager;
    private Context mContext;
    private HorizontalScrollView horizontalScrollView;
    private ScrollView scrollView;
    private float x_tmp1;
    private float x_tmp2;
    private static CourseListFragment mfragment;


    public CourseListFragment() {
        mfragment = this;
    }

    public static CourseListFragment getInstance(){
        if(mfragment==null)
            mfragment = new CourseListFragment();
        return mfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        initBaseComponents(view);
        dbManager = new DBManager(getActivity());
        int currentWeek = getCurrentWeek();
        showCourses(currentWeek);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        initActionBar();
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizon_scrollview);
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        scrollView.scrollTo(0, 0);
        horizontalScrollView.scrollTo(0,0);
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获取当前坐标
                float x = event.getRawX();
                float y = event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        x_tmp1 = x;
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        x_tmp2 = x;
                        if (((x_tmp1 - x_tmp2) < 8) && horizontalScrollView.getScrollX() == 0) {
                            ((SlidingMenuMainActivity) getActivity()).getMenu().showMenu();
                        }
                        break;
                    }
                }
                return false;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private int getCurrentWeek(){
        return 0;
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
        marginParams.setMargins(leftMargin, topMargin, 0, 0);
        course.setLayoutParams(marginParams);
        course.setGravity(Gravity.CENTER);
        course.setWidth(courseWidth);
        course.setHeight(courseLength);
        course.setBackgroundColor(getResources().getColor(R.color.courseColor));
        course.setPadding(8, 8, 8, 8);
        course.setText(spec.toString());
        course.setTextSize(15);
        course.setTextColor(Color.WHITE);
        mCourseContent.addView(course);
    }

    public void showCourses(int currentWeek){
        mCourseContent.removeAllViews();
        List<CourseSpec> courseSpecs = dbManager.queryAllCourses();
        Log.i("size",courseSpecs.size()+"");
        for (CourseSpec spec :courseSpecs){
            Log.i("courses","name:"+spec.getCourseName()+"begin:"+spec.getBeginWeek()+"end"+spec.getEndWeek()+"double"+spec.getIsDoubleWeek()+"single"+spec.getIsSingleWeek());
            if((currentWeek>=spec.getBeginWeek()&&currentWeek<=spec.getEndWeek())||(spec.getBeginWeek()==0&&spec.getEndWeek()==0)) {
                Log.i("aaaa","name:"+spec.getCourseName()+"begin:"+spec.getBeginWeek()+"end"+spec.getEndWeek()+"double"+spec.getIsDoubleWeek()+"single"+spec.getIsSingleWeek());
                if((spec.getIsDoubleWeek()==1&&currentWeek%2==1)||(spec.getIsSingleWeek()==1&&currentWeek%2==0))
                    continue;
                addCourse(new CourseSpec(spec.getCourseName(), spec.getClassRoom(), spec.getTeacherName(), spec.getWeek(), spec.getStartTime(), spec.getEndTime(), spec.getBeginWeek(), spec.getEndWeek(), spec.getIsSingleWeek(), spec.getIsDoubleWeek()));
            }
        }
    }

    public void initActionBar(){
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        String[] titles = new String[18];
        for(int i=1;i<=18;i++){
            titles[i-1] = "第"+i+"周";
        }
        SpinnerAdapter adapter = new ArrayAdapter<String>(mContext,R.layout.spinner_item,titles);
        if(actionBar==null){
            Log.e("err","actionbar is null");
        }
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                Log.i("position", String.valueOf(itemPosition));
                showCourses(itemPosition+1);
                return true;
            }
        });
        actionBar.setSelectedNavigationItem(4);
    }
}
