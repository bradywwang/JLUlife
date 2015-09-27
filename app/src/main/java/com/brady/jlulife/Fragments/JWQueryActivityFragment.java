package com.brady.jlulife.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.brady.jlulife.Model.JWCModel;
import com.brady.jlulife.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class JWQueryActivityFragment extends Fragment {
    Button btnSubmit;
    TextView tvContent;
    TextView tvTitle;

    public JWQueryActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jwquery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSubmit = (Button) view.findViewById(R.id.btnsubmit);
        tvContent = (TextView) view.findViewById(R.id.content);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvContent.setText("clicked");
                JWCModel.getNewsBaseInfo(1,getActivity().getApplicationContext());
            }
        });
    }
}
