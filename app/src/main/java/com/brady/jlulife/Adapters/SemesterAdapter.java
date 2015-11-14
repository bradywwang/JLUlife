package com.brady.jlulife.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brady.jlulife.Entities.TermList;
import com.brady.jlulife.R;

import java.util.List;

/**
 * Created by brady on 15-11-13.
 */
public class SemesterAdapter extends ArrayAdapter {
    private Context mContext;
    private List<TermList> mList;
    private int mResourse;


    public SemesterAdapter(Context context, int resource, List<TermList> objects) {
        super(context, resource, objects);
        mContext = context;
        mList = objects;
        mResourse = resource;
    }
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            Log.i("resId",String.valueOf(mResourse));
            view = LayoutInflater.from(mContext).inflate(mResourse,null);
            TextView textView = (TextView) view.findViewById(R.id.tv_spinner_item);
            textView.setText(mList.get(position).getTermName());
        }else {
            view = convertView;
        }
        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
