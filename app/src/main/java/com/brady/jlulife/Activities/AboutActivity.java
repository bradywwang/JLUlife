package com.brady.jlulife.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.brady.jlulife.Fragments.AboutFragment;
import com.brady.jlulife.Fragments.CjcxAuthFragment;
import com.brady.jlulife.R;

public class AboutActivity extends BaseActivity {

    @Override
    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, AboutFragment.getInstance());
        transaction.commit();
    }


}
