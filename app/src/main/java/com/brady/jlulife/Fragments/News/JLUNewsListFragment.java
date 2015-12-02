package com.brady.jlulife.Fragments.News;

import android.os.Bundle;

import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Models.JLUNewsModel;
import com.brady.jlulife.Models.Listener.OnListinfoGetListener;

import java.util.List;

/**
 * Created by brady on 15-11-21.
 */
public class JLUNewsListFragment extends NewsListFragment{
    private static JLUNewsListFragment fragment;

    public static JLUNewsListFragment getInstance(){
        if (fragment==null)
            fragment = new JLUNewsListFragment();
        return fragment;
    }

    @Override
    protected void LoadInfo() {
        JLUNewsModel.getInstance().getNewsList(mPageNum, new OnListinfoGetListener() {

            @Override
            public void onGetInfoSuccess(final List list) {
                showNewsList(list);
                hideDialog();
            }

            @Override
            public void onGetInfoFail() {
                hideDialog();
            }
        });
        refreshListView.onRefreshComplete();
    }

    @Override
    public void processItemClick(int position) {
        NewsBaseInfo baseInfo = (NewsBaseInfo) mList.get(position - 1);
        Bundle bundle = new Bundle();
        bundle.putString("href", baseInfo.getHref());
        JLUNewsDetailFragment fragment = new JLUNewsDetailFragment();
        replaceFrag(fragment,bundle);
    }
}
