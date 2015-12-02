package com.brady.jlulife.Fragments.News;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Fragments.BaseFragment;
import com.brady.jlulife.Models.Listener.OnObjectGetListener;
import com.brady.jlulife.Models.NewsModel;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;


public abstract class NewsDetailFragment extends BaseFragment {
    private TextView tvTitle;
    private TextView tvSubmitDep;
    private TextView tvSubmitDate;
    private TextView tvContent;
    LinearLayout newsDetailLayout;
    protected PullToRefreshScrollView scrollView;
    private String action;
    protected String href;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        Bundle bundle = getArguments();
        action = bundle.getString("action");
        href = bundle.getString("href");
        initScrollView(view);
        showDialog();
        loadInfo();
    }
    private void initComponents(View view){
        tvTitle = (TextView) view.findViewById(R.id.news_title);
        tvSubmitDep = (TextView) view.findViewById(R.id.news_submit_dep);
        tvSubmitDate = (TextView) view.findViewById(R.id.news_edit_date);
        tvContent = (TextView) view.findViewById(R.id.news_content);
        scrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_to_refresh_scroll);
        newsDetailLayout = (LinearLayout) view.findViewById(R.id.news_detail_layout);
    }
    public void initScrollView(View view){
        if(scrollView==null)
            return;
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadInfo();
            }
        });
    }
    public abstract void loadInfo();


    public void showNewsDetail(Object object){
        News news = (News) object;
        tvTitle.setText(news.getTitle());
        tvSubmitDate.setText(news.getSubmitTime());
        tvSubmitDep.setText(news.getSubmitDepartment());
        String cAttach = news.getcAttach();
        String cAttachName = news.getcAttachName();
        String content = news.getContent();
        if (cAttach != null && !cAttach.equals("")) {
            StringBuilder builder = new StringBuilder();
            SpannedString string = new SpannedString(content);
            builder.append(Html.toHtml(string));
            builder.append("<p>附件</p>");
            String test = cAttachName.replace("|", "ttttt");
            String attechList[] = test.split("ttttt");
            for (int i = 0; i < attechList.length; i++) {
                builder.append("<br><a href='" + ConstValue.NEWS_DOWNLOAD + "?id=" + ((News) object).getId() + "&fid=" + i + "'>" + attechList[i] + "</a></br>");
            }

            CharSequence con = Html.fromHtml(builder.toString());
            tvContent.setText(con);
        } else {
            tvContent.setText(content);
        }
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
