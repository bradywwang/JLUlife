package com.brady.jlulife;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.brady.jlulife.Fragments.DrcomLoginFragment;
import com.brady.jlulife.Fragments.LoginSuccessFragment;
import com.drcom.Android.DrCOMWS.Tool.DrCOMWSManagement;

public class DrcomLoginActivity extends ActionBarActivity {
    DrCOMWSManagement management;
    DrcomLoginFragment loginFragment;
    LoginSuccessFragment successFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drcom_login);
        management = new DrCOMWSManagement(this);
        FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        loginFragment = new DrcomLoginFragment();
        successFragment = new LoginSuccessFragment();
        if(management.getLoginStatus()){
            transaction.replace(R.id.drcom_login_status,successFragment);
            Log.i(getClass().getSimpleName(),"is login");
        }else {
            transaction.replace(R.id.drcom_login_status, loginFragment);
            Log.i(getClass().getSimpleName(), "is not login");
        }
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drcom_login, menu);
        return true;
    }


    public Fragment getLoginSuccessFragment(){
        return successFragment;
    }

    public Fragment getLoginFragemnt(){
        return loginFragment;
    }
}
