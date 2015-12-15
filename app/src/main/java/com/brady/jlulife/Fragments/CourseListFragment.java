package com.brady.jlulife.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;

import com.brady.jlulife.Activities.EditCourseActivity;
import com.brady.jlulife.MyHorizontalScrollView;
import com.brady.jlulife.MyVerticalScrollView;
import com.brady.jlulife.OnScrollListener;
import com.brady.jlulife.Activities.SlidingMenuMainActivity;
import com.brady.jlulife.Entities.CourseSpec;
import com.brady.jlulife.Models.db.DBManager;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;
import com.brady.jlulife.Utils.Utils;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends BaseFragment {

    private RelativeLayout mCourseContent;
    private DBManager dbManager;
    private Context mContext;
    private MyHorizontalScrollView horizontalScrollView;
    private MyVerticalScrollView scrollView;
    private float x_tmp1;
    private float x_tmp2;
    private static CourseListFragment mfragment;
    GridView head;
    GridView courseNum;

    public CourseListFragment() {
        mfragment = this;
    }

    public static CourseListFragment getInstance() {
        if (mfragment == null)
            mfragment = new CourseListFragment();
        return mfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        initBaseComponents(view);
        refreshClassView(view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
//        initActionBar();
    }

    private void refreshClassView(View view){
        dbManager = new DBManager(getActivity());
        long currentWeek = getCurrentWeek();
        showCourses(currentWeek);
        initScrollMethod(view);
        getActivity().invalidateOptionsMenu();
        getActivity().getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
        setTitle("我的课表");
    }
    private void initScrollMethod(final View view) {
        horizontalScrollView = (MyHorizontalScrollView) view.findViewById(R.id.horizon_scrollview);
        scrollView = (MyVerticalScrollView) view.findViewById(R.id.scrollview);
        horizontalScrollView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void OnScrollChanged(int x, int y, int oldx, int oldy) {
                head.scrollTo(x, 0);
            }
        });
        scrollView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void OnScrollChanged(int x, int y, int oldx, int oldy) {
                courseNum.scrollTo(0, y);
            }
        });
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
        courseNum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        refreshClassView(getView());
        super.onResume();
    }



    private long getCurrentWeek() {
        SharedPreferences sf = getActivity().getSharedPreferences("com.brady.jlulife_preferences",Context.MODE_PRIVATE);
        String week = sf.getString("currentweek", "");
        if(week.equals("")) {
            SharedPreferences.Editor editor = sf.edit();
            editor.putString("currentweek","第1周");
            editor.putLong("savedtime", Utils.getFirstDayOfWeek(new Date()).getTime());
            editor.commit();
            return 1;
        }
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(week);
        int savedWeek = Integer.parseInt(m.replaceAll("").trim());
        long savedDate  = sf.getLong("savedtime", 0);
        long numOfDay = (new Date().getTime()-savedDate)/86400000;
        long weekPassed =  (numOfDay/7);
        if(savedDate==0||savedWeek==0)
            return 1;
        else
            return savedWeek+weekPassed;
    }

    private void initBaseComponents(View view) {
        head = (GridView) view.findViewById(R.id.gridWeek);
        String[] weeks = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        ArrayAdapter<String> headAdap = new ArrayAdapter<String>(getActivity(),
                R.layout.weekstyle, weeks);
        head.setAdapter(headAdap);

        // 第几节课
        courseNum = (GridView) view.findViewById(R.id.gridCourseNum);
        String[] courseNums = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11"};
        ArrayAdapter<String> leftAdap = new ArrayAdapter<String>(getActivity(),
                R.layout.coursenumstyle, courseNums);
        courseNum.setAdapter(leftAdap);
        mCourseContent = (RelativeLayout) view.findViewById(R.id.relativeCourseContent);
//        addCourse(new CourseSpec("毛概","f9","张三",3,3,4,1,8,false));
    }

    private void addCourse(final CourseSpec spec) {
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
        final int spacing = (int) (getResources().getDimension(R.dimen.spacing));
        // 算出左边距=左边有几节课*（课程宽度+1px的间隔）+左侧编号宽度
        leftMargin = (spec.getWeek() - 1) * (courseWidth + spacing) + courseNum_width;
        // 上边距=上边有几节课*（课程高度+1px的间隔）+顶部星期的宽度
        topMargin = (spec.getStartTime() - 1) * (courseHeight + spacing)
                + week_item_height;
        // 课程长度=课程节数*(课程高度+1px的间隔)-1个像素
        courseLength = (spec.getEndTime() - spec.getStartTime() + 1)
                * (courseHeight + spacing) - spacing;
        RelativeLayout.LayoutParams marginParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        marginParams.setMargins(leftMargin, topMargin, 0, 0);
        marginParams.width = courseWidth;
        marginParams.height = courseLength;
        Button course = new Button(getActivity());
        course.setLayoutParams(marginParams);
        course.setGravity(Gravity.CENTER);
        int courseId = spec.getCourseId();
        if(courseId == 0) {
            course.setBackgroundColor(getCourseBackground(spec.getId()));
        }
        else {
            course.setBackgroundColor(getCourseBackground(courseId));
        }
        course.setPadding(8, 5, 8, 5);
        course.setText(spec.toString());
        course.setTextSize(getResources().getDimension(R.dimen.coursespec_font_size));
        course.setTextColor(Color.WHITE);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditCourseActivity.class);
                intent.putExtra(EditCourseActivity.EXTRA_COURSEID,spec.getId());
                startActivity(intent);
            }
        });
        mCourseContent.addView(course);
    }

    public void showCourses(long currentWeek) {
        mCourseContent.removeAllViews();
        List<CourseSpec> courseSpecs = dbManager.queryAllCourses();
        for (CourseSpec spec : courseSpecs) {
            if ((currentWeek >= spec.getBeginWeek() && currentWeek <= spec.getEndWeek()) || (spec.getBeginWeek() == 0 && spec.getEndWeek() == 0)) {
                if ((spec.getIsDoubleWeek() == 1 && currentWeek % 2 == 1) || (spec.getIsSingleWeek() == 1 && currentWeek % 2 == 0))
                    continue;
                addCourse(spec);
            }
        }
    }


    private int getCourseBackground(int courseId) {
        int backId = courseId % 7;
        Log.e("test","backId:"+backId+"courseId"+courseId);
        int result = getResources().getColor(R.color.class_value_1);
        switch (backId) {
            case 0: {
                result = getResources().getColor(R.color.class_value_1);
                break;
            }
            case 1: {
                result = getResources().getColor(R.color.class_value_2);
                break;
            }
            case 2: {
                result = getResources().getColor(R.color.class_value_3);
                break;
            }
            case 3: {
                result = getResources().getColor(R.color.class_value_4);
                break;
            }
            case 4: {
                result = getResources().getColor(R.color.class_value_5);
                break;
            }
            case 5: {
                result = getResources().getColor(R.color.class_value_6);
                break;
            }
            case 6: {
                result = getResources().getColor(R.color.class_value_7);
                break;
            }
            case 7: {
                result = getResources().getColor(R.color.class_value_8);
                break;
            }
            case 8: {
                result = getResources().getColor(R.color.class_value_9);
                break;
            }

        }
        return result;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item= menu.findItem(R.id.action_week);
        item.setTitle("第"+getCurrentWeek()+"周");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_course, menu);
    }
}
