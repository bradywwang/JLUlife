package com.brady.jlulife.Fragments.News;

import com.brady.jlulife.Models.JLUNewsModel;
import com.brady.jlulife.Models.Listener.OnObjectGetListener;

/**
 * Created by brady on 15-11-21.
 */
public class JLUNewsDetailFragment extends NewsDetailFragment{
    @Override
    public void loadInfo() {
        JLUNewsModel.getInstance().getNewsDetail(href, new OnObjectGetListener() {
            @Override
            public void onGetInfoSuccess(Object object) {
                showNewsDetail(object);
                hideDialog();
            }
            @Override
            public void onGetInfoFail() {
                hideDialog();
            }
        });
        scrollView.onRefreshComplete();
    }
}
