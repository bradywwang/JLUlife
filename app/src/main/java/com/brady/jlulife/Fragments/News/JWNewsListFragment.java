package com.brady.jlulife.Fragments.News;

import android.os.Bundle;

import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Models.JWNewsModel;
import com.brady.jlulife.Models.Listener.OnListinfoGetListener;

import java.util.List;

/**
 * Created by brady on 15-11-21.
 */
public class JWNewsListFragment extends NewsListFragment {
    private static JWNewsListFragment fragment;

    public static JWNewsListFragment getInstance(){
        if (fragment==null)
            fragment = new JWNewsListFragment();
        return fragment;
    }


    protected void LoadInfo(){
        JWNewsModel.getInstance().getNewsList(mPageNum, new OnListinfoGetListener() {
            @Override
            public void onGetInfoSuccess(final List list) {
                hideDialog();
                showNewsList(list);
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
//                        bundle.putString("action", mAction);
        bundle.putString("href", baseInfo.getHref());
        JWNewsDetailFragment fragment = new JWNewsDetailFragment();
        replaceFrag(fragment,bundle);
    }
}
