package com.brady.jlulife.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.brady.jlulife.Fragments.EditCourseFragment;
import com.brady.jlulife.R;

/**
 * Created by brady on 15-12-13.
 */
public class EditCourseActivity extends BaseActivity {
     public static final String EXTRA_COURSEID= "extra_courseid";

    @Override
    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, EditCourseFragment.getInstance());
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
//        int courseId = intent.getIntExtra(EXTRA_COURSEID, -1);
        EditCourseFragment editCourseFragment = EditCourseFragment.getInstance();
        if(!editCourseFragment.isAdded()){
            editCourseFragment.setArguments(intent.getExtras());
        }
        super.onCreate(savedInstanceState);
    }

}
