package com.brady.jlulife.Fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.brady.jlulife.Activities.ScoreListActivity;
import com.brady.jlulife.R;

/**
 * Created by brady on 15-11-21.
 */
public class CjcxAuthFragment extends UIMSAuthFragment {
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private static CjcxAuthFragment instance;

    public CjcxAuthFragment() {
        instance = this;
    }

    public static CjcxAuthFragment getInstance(){
        if(instance == null)
            instance = new CjcxAuthFragment();
        return instance;
    }

    @Override
    public void showNextPage() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container,ScoreListFragment.getInstance());
        transaction.commit();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginOutside.setVisibility(View.VISIBLE);
    }
}
