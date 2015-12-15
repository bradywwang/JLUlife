package com.brady.jlulife.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.brady.jlulife.Activities.EditCourseActivity;
import com.brady.jlulife.Entities.CourseSpec;
import com.brady.jlulife.Models.db.DBManager;
import com.brady.jlulife.R;

/**
 * Created by brady on 15-11-30.
 */
public class EditCourseFragment extends BaseFragment {
    private static EditCourseFragment instance;
    private EditText etCourseName;
    private EditText etTeacherName;
    private EditText etCoursePlace;
    private Button btnAddCourse;
    private Button btnUpdateCourse;
    private Button btnDelCourse;
    private Spinner spinnerWeek;
    private Spinner spinnerStartTime;
    private Spinner spinnerEndTime;
    private Spinner spinnerStartWeek;
    private Spinner spinnerEndWeek;
    private Context mContext;
    private DBManager dbManager;
    private CourseSpec mCourseSpec;

    public static EditCourseFragment getInstance() {
        if (instance == null) {
            instance = new EditCourseFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_course, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        initComents(view);
        initSpinners();
        dbManager = new DBManager(mContext);
        initData();
    }

    private void initComents(View view) {
        etCourseName = (EditText) view.findViewById(R.id.et_course_name);
        etCoursePlace = (EditText) view.findViewById(R.id.et_course_place);
        etTeacherName = (EditText) view.findViewById(R.id.et_teacher_name);
        spinnerWeek = (Spinner) view.findViewById(R.id.spinner_week_picker);
        spinnerStartWeek = (Spinner) view.findViewById(R.id.spinner_startweek_picker);
        spinnerEndWeek = (Spinner) view.findViewById(R.id.spinner_endweek_picker);
        spinnerStartTime = (Spinner) view.findViewById(R.id.spinner_startclass_picker);
        spinnerEndTime = (Spinner) view.findViewById(R.id.spinner_endclass_picker);
        btnAddCourse = (Button) view.findViewById(R.id.btn_add_course);
        btnDelCourse = (Button) view.findViewById(R.id.btn_del_course);
        btnUpdateCourse = (Button) view.findViewById(R.id.btn_update_course);
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
            }
        });
        btnDelCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delCourse();
            }
        });
        btnUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourse();
            }
        });
    }

    private void initSpinners() {
        spinnerWeek.setAdapter(new ArrayAdapter(mContext, R.layout.spinner_item, getResources().getStringArray(R.array.days)));
        spinnerStartWeek.setAdapter(new ArrayAdapter(mContext, R.layout.spinner_item, getResources().getStringArray(R.array.weeks)));
        spinnerEndWeek.setAdapter(new ArrayAdapter(mContext, R.layout.spinner_item, getResources().getStringArray(R.array.weeks)));
        spinnerStartTime.setAdapter(new ArrayAdapter(mContext, R.layout.spinner_item, getResources().getStringArray(R.array.course)));
        spinnerEndTime.setAdapter(new ArrayAdapter(mContext, R.layout.spinner_item, getResources().getStringArray(R.array.course)));
    }

    public void initData() {
        int courseId = getCourseIdFromArgments();
        if (courseId != -1) {
            btnAddCourse.setVisibility(View.GONE);
            CourseSpec spec = dbManager.queryCourse(courseId);
            mCourseSpec = spec;
            etCourseName.setText(spec.getCourseName());
            etTeacherName.setText(spec.getTeacherName());
            etCoursePlace.setText(spec.getClassRoom());
            spinnerWeek.setSelection(spec.getWeek() - 1);
            spinnerStartWeek.setSelection(spec.getBeginWeek() - 1);
            spinnerEndWeek.setSelection(spec.getEndWeek() - 1);
            spinnerStartTime.setSelection(spec.getStartTime() - 1);
            spinnerEndTime.setSelection(spec.getEndTime() - 1);
        } else {
            btnUpdateCourse.setVisibility(View.GONE);
            btnDelCourse.setVisibility(View.GONE);
            setTitle("添加课程");
        }
    }

    private int getCourseIdFromArgments() {
        Bundle argument;
        int result = -1;
        if ((argument = getArguments()) != null) {
            result = argument.getInt(EditCourseActivity.EXTRA_COURSEID, -1);
        }
        return result;
    }

    private void addCourse() {
        CourseSpec spec = obtainData();
        if (validateCourse(spec)) {
            dbManager.addCourse(spec);
            Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    private void delCourse() {
        int id = mCourseSpec.getId();
        dbManager.deleteCourse(id);
        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    private void updateCourse() {
        CourseSpec spec = obtainData();
        if (validateCourse(spec)) {
            dbManager.editCourse(mCourseSpec.getId(), spec);
            Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    private CourseSpec obtainData() {
        CourseSpec spec = new CourseSpec();
        spec.setCourseName(etCourseName.getText().toString());
        spec.setClassRoom(etCoursePlace.getText().toString());
        spec.setEndWeek(spinnerEndWeek.getSelectedItemPosition() + 1);
        spec.setBeginWeek(spinnerStartWeek.getSelectedItemPosition() + 1);
        spec.setEndTime(spinnerEndTime.getSelectedItemPosition() + 1);
        spec.setCourseId(0);
        spec.setIsDoubleWeek(0);
        spec.setIsSingleWeek(0);
        spec.setStartTime(spinnerStartTime.getSelectedItemPosition() + 1);
        spec.setTeacherName(etTeacherName.getText().toString());
        spec.setWeek(spinnerWeek.getSelectedItemPosition() + 1);
        return spec;
    }

    private boolean validateCourse(CourseSpec spec) {
        if (spec.getStartTime() > spec.getEndTime()) {
            Toast.makeText(mContext, "开始节数必须小于结束节数", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spec.getBeginWeek() > spec.getEndWeek()) {
            Toast.makeText(mContext, "开始周数必须小于结束周数", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(spec.getCourseName()) || TextUtils.isEmpty(spec.getClassRoom())) {
            Toast.makeText(mContext, "课程名或上课地点不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
