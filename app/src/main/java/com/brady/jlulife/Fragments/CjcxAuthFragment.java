package com.brady.jlulife.Fragments;

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
        repleceFragment(R.id.main_container,new SemSelectFragment());
    }
}
