package com.brady.jlulife.Fragments;


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
import android.widget.TextView;

import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;


public class NewsDetailFragment extends Fragment {
    private TextView tvTitle;
    private TextView tvSubmitDep;
    private TextView tvSubmitDate;
    private TextView tvContent;

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
        String title = bundle.getString("title");
        String dep = bundle.getString("dep");
        String date = bundle.getString("date");
        String content = bundle.getString("content");
        String cAttach = bundle.getString("cAttach");
        String cAttachName = bundle.getString("cAttachName");
        String cId = bundle.getString("cId");
        tvTitle.setText(title);
        tvSubmitDate.setText(date);
        tvSubmitDep.setText(dep);
        if(cAttach!=null&&!cAttach.equals("")){
            StringBuilder builder = new StringBuilder();
            SpannedString string = new SpannedString(content);
            builder.append(Html.toHtml(string));
            builder.append("<p>附件</p>");
            Log.i(getClass().getSimpleName(), cAttachName);
            String test = cAttachName.replace("|", "ttttt");
            Log.i(getClass().getSimpleName(), "ttt"+test);
            String attechList[] = test.split("ttttt");
            Log.i(getClass().getSimpleName(), attechList.length+"");

            for(int i=0;i<attechList.length;i++){
                builder.append("<br><a href='"+ ConstValue.NEWS_DOWNLOAD+"?id="+cId+"&fid="+i+"'>"+attechList[i]+"</a></br>");
            }

            CharSequence con = Html.fromHtml(builder.toString());
            tvContent.setText(con);
        }else {
            tvContent.setText(content);
        }
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());


    }
    private void initComponents(View view){
        tvTitle = (TextView) view.findViewById(R.id.news_title);
        tvSubmitDep = (TextView) view.findViewById(R.id.news_submit_dep);
        tvSubmitDate = (TextView) view.findViewById(R.id.news_edit_date);
        tvContent = (TextView) view.findViewById(R.id.news_content);
    }
}
