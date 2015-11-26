package com.brady.jlulife.Fragments;

import android.os.Bundle;
import android.view.View;

import com.brady.jlulife.R;

/**
 * Created by brady on 15-11-21.
 */
public class CjcxAuthFragment extends UIMSAuthFragment {
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
        repleceFragment(R.id.main_container,ScoreListFragment.getInstance());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginOutside.setVisibility(View.VISIBLE);
    }
}
