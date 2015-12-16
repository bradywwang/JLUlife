package com.brady.jlulife.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.brady.jlulife.Fragments.LoginSuccessFragment;
import com.brady.jlulife.R;

/**
 * Created by brady on 15-12-16.
 */
public class DrComLoginSuccessActivity extends BaseActivity {
    @Override
    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, LoginSuccessFragment.getInstance());
        transaction.commit();
    }
}
