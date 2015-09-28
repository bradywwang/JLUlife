package com.brady.jlulife.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        Bundle bundle = getArguments();
        String href = bundle.getString("title");
        Log.i("hrefaaaa",href);
        Log.i("hrefaaaa","test");
        initComponents(view);
        JWCModel.getInstance().getNewsContent(href, new OnNewsDetailinfoGetListener() {
            @Override
            public void onGetInfoSuccess(News news) {
                tvTitle.setText(news.getTitle());
                tvContent.setText(news.getContent());
                tvSubmitDate.setText(news.getSubmitTime());
                tvSubmitDep.setText(news.getSubmitDepartment());
            }

            @Override
            public void onGetInfoFail() {

            }
        });

    }
    private void initComponents(View view){
        tvTitle = (TextView) view.findViewById(R.id.news_title);
        tvSubmitDep = (TextView) view.findViewById(R.id.news_submit_dep);
        tvSubmitDate = (TextView) view.findViewById(R.id.news_edit_date);
        tvContent = (TextView) view.findViewById(R.id.news_content);
    }
}
