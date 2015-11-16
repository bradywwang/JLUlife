package com.brady.jlulife.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoadFailFragment extends BaseFragment {
    Button btnRefresh;

    public LoadFailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_load_fail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        final String argument = getArguments().getString("source");
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = null;
                switch (argument){
                    case ConstValue.JWC_SOURCE:{
                        fragment = new NewsListFragment();
                        transaction.replace(R.id.main_container,fragment);
                        break;
                    }
                    /*case ConstValue.JWC_DETAIL_SOURCE:{
                        fragment = new NewsDetailFragment();
                        transaction.replace(R.id.main_container,fragment);
                        break;
                    }
                    case ConstValue.NEWS_SOURCE:{
                        fragment = new NewsActivityFragment();
                        transaction.replace(R.id.main_container,fragment);
                        break;
                    }
                    case ConstValue.NEWS_SOURCE_DETAIL:{
                        fragment = new NewsDetailFragment();
                        transaction.replace(R.id.main_container,fragment);
                    }*/
                }
                    transaction.commit();
            }
        });
    }

    void initComponents(View view) {
        btnRefresh = (Button) view.findViewById(R.id.btn_fail_refresh);
    }

}
