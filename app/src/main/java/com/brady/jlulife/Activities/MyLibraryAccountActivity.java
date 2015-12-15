package com.brady.jlulife.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.brady.jlulife.Fragments.LibraryAccountFragment;
import com.brady.jlulife.Fragments.LibraryAuthFragment;
import com.brady.jlulife.R;

/**
 * Created by brady on 15-12-15.
 */
public class MyLibraryAccountActivity extends BaseActivity {
    @Override
    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, LibraryAuthFragment.getInstance());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        LibraryAccountFragment fragment = LibraryAccountFragment.getInstance();
        if(fragment.isAdded()&&fragment.canGoBack()){
            LibraryAccountFragment.getInstance().preformBack();
        }else {
            super.onBackPressed();
        }
    }
}
