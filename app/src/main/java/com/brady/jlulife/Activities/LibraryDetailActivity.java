package com.brady.jlulife.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.brady.jlulife.Fragments.LibraryAccountFragment;
import com.brady.jlulife.R;

/**
 * Created by brady on 15-12-15.
 */
public class LibraryDetailActivity extends BaseActivity {

    @Override
    public void initFragment() {
        LibraryAccountFragment accountFragment = new LibraryAccountFragment();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        accountFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, accountFragment);
        transaction.commit();
    }
}
