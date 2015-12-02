package com.brady.jlulife.Activities;


import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.brady.jlulife.Fragments.SettingFragment;
import com.brady.jlulife.R;

/**
 * Created by brady on 15-11-30.
 */
public class SettingActivity extends BaseActivity{

    @Override
    public void initFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, new SettingFragment());
        transaction.commit();
    }
}
