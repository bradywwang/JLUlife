package com.brady.jlulife;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;

import com.brady.jlulife.Fragments.SemSelectFragment;
import com.brady.jlulife.Fragments.UIMSAuthFragment;
import com.brady.jlulife.Models.UIMSModel;
import com.umeng.analytics.MobclickAgent;

public class UimsOauthActivity extends ActionBarActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mContext = getApplicationContext();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        UIMSModel model = UIMSModel.getInstance(mContext);
        if(model.isLoginIn()){
            transaction.replace(R.id.auth_container,new SemSelectFragment());
        }else {
            transaction.replace(R.id.auth_container, new UIMSAuthFragment());
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uims_oauth, menu);
        return true;
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
