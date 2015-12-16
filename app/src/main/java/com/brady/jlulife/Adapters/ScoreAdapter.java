package com.brady.jlulife.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brady.jlulife.Entities.Score.OutSchoolScore.OutsideItem;
import com.brady.jlulife.R;

import java.util.List;

/**
 * Created by brady on 15-11-26.
 */
public class ScoreAdapter extends ArrayAdapter<OutsideItem> {
    private Context mContext;
    private List<OutsideItem> mList;
    private int mResource;
    public ScoreAdapter(Context context, int resource, List<OutsideItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mList = objects;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, null);
            holder = new ViewHolder();
            holder.tvCourseName = (TextView) convertView.findViewById(R.id.tv_cour_name);
            holder.tvCredit = (TextView) convertView.findViewById(R.id.tv_scorepoint);
            holder.tvGpoint = (TextView) convertView.findViewById(R.id.tv_gpoint);
            holder.tvIsReselect = (TextView) convertView.findViewById(R.id.is_reselect);
            holder.tvScore = (TextView) convertView.findViewById(R.id.tv_score);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        OutsideItem value = mList.get(position);
        holder.tvIsReselect.setText(value.getIsReselect());
        holder.tvScore.setText(value.getCj());
        holder.tvGpoint.setText(value.getGpoint());
        holder.tvCredit.setText(value.getCredit());
        holder.tvCourseName.setText(value.getKcmc());
        return convertView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    private class ViewHolder{
        TextView tvCourseName;
        TextView tvScore;
        TextView tvGpoint;
        TextView tvCredit;
        TextView tvIsReselect;
    }
}
