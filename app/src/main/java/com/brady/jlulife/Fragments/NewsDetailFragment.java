package com.brady.jlulife.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brady.jlulife.CallbackListeners.OnNewsDetailinfoGetListener;
import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Models.JWCModel;
import com.brady.jlulife.R;


public class NewsDetailFragment extends Fragment {
    private TextView tvTitle;
    private TextView tvSubmitDep;
    private TextView tvSubmitDate;
    private TextView tvContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String dep = bundle.getString("dep");
        String date = bundle.getString("date");
        String content = bundle.getString("content");
        tvTitle.setText(title);
        tvSubmitDate.setText(date);
        tvContent.setText(content);
        tvSubmitDep.setText(dep);

    }
    private void initComponents(View view){
        tvTitle = (TextView) view.findViewById(R.id.news_title);
        tvSubmitDep = (TextView) view.findViewById(R.id.news_submit_dep);
        tvSubmitDate = (TextView) view.findViewById(R.id.news_edit_date);
        tvContent = (TextView) view.findViewById(R.id.news_content);
    }
}
