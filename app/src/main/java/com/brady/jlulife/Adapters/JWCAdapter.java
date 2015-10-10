package com.brady.jlulife.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.R;
import com.brady.jlulife.ViewHolders.JWCViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2015/9/28.
 */
public class JWCAdapter extends ArrayAdapter<NewsBaseInfo> {
    private List<NewsBaseInfo> newsList;
    private Context mcontext;
    private int resourceId;


    public JWCAdapter(Context context, int resource, int textViewResourceId, List<NewsBaseInfo> objects) {
        super(context, resource, textViewResourceId, objects);
        newsList = objects;
        mcontext = context;
        resourceId = resource;
    }

    public void setNewsList(List list){
        newsList = list;
    }
    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsBaseInfo baseInfo = newsList.get(position);
        View view = convertView;
        JWCViewHolder viewHolder = null;
        if(view ==null){
            view = LayoutInflater.from(mcontext).inflate(resourceId,null);
            viewHolder = new JWCViewHolder();
            viewHolder.tvDate = (TextView) view.findViewById(R.id.jwc_date);
            viewHolder.tvDep = (TextView) view.findViewById(R.id.jwc_dep);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.jwc_title);
            view.setTag(viewHolder);
        }else{
            viewHolder = (JWCViewHolder) view.getTag();
        }
        viewHolder.tvTitle.setText(baseInfo.getTitle());
        viewHolder.tvDate.setText(baseInfo.getDate());
        viewHolder.tvDep.setText(baseInfo.getDep());
        return view;
    }

}
