package com.brady.jlulife.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.brady.jlulife.Fragments.ScoreListFragment;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.R;

/**
 * Created by brady on 15-11-30.
 */
public class ScoreListActivity extends BaseActivity {

    @Override
    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, ScoreListFragment.getInstance());
        transaction.commit();

    }


}
