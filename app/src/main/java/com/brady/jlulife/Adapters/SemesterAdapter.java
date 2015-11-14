package com.brady.jlulife.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.brady.jlulife.Entities.TermList;

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
        return mList.get(position).getTermName();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            Log.i("resId",String.valueOf(mResourse));
            view = View.inflate(mContext, mResourse, null);
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
