package com.brady.jlulife.Fragments;

import com.brady.jlulife.FragmentControler;
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
        FragmentControler.addFragment(this, R.id.main_container, FragmentControler.TAG_SEM_SELECT);
    }
}
