package com.brady.jlulife.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.brady.jlulife.R;

/**
 * Created by brady on 15-11-30.
 */
public abstract class BaseActivity extends AppCompatActivity{
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }*/

    public void initActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar == null){
            Log.e("ttt","toolbar is null");
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initActionBar();
    }

    public abstract void initFragment();
}
