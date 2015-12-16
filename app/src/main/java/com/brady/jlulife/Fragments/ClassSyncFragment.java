package com.brady.jlulife.Fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.brady.jlulife.R;

/**
 * Created by brady on 15-11-21.
 */
public class ClassSyncFragment extends UIMSAuthFragment {
    private static ClassSyncFragment instance;

    public ClassSyncFragment() {
        instance = this;
    }

    public static ClassSyncFragment getInstance(){
        if(instance == null)
            instance = new ClassSyncFragment();
        return instance;
    }

    @Override
    public void showNextPage() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container,SemSelectFragment.getInstance());
        transaction.commit();
    }
}
