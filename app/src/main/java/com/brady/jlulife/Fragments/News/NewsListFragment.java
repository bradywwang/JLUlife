package com.brady.jlulife.Fragments.News;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.brady.jlulife.Adapters.NewsAdapter;
import com.brady.jlulife.Fragments.BaseFragment;
import com.brady.jlulife.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class NewsListFragment extends BaseFragment {
    protected PullToRefreshListView refreshListView;
    Fragment fragment = null;
    NewsAdapter mAdapter = null;
    List mList = null;
    int mPageNum = 1;
    TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPageNum = 1;
        fragment = this;
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefeshView(view);
        if(mList.size()==0) {
            showDialog();
            LoadInfo();
        }
        mTextView = (TextView) view.findViewById(R.id.label_loading);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void initRefeshView(View view){
        refreshListView = (PullToRefreshListView) view.findViewById(R.id.news_listview);
        mList = new ArrayList();
        mAdapter = new NewsAdapter(getActivity(), R.layout.item_news, mList);
        refreshListView.setAdapter(mAdapter);
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.getLoadingLayoutProxy(false,true).setPullLabel(getString(R.string.pull_to_load));
        refreshListView.getLoadingLayoutProxy(false,true).setRefreshingLabel(getString(R.string.loading));
        refreshListView.getLoadingLayoutProxy(false,true).setReleaseLabel(getString(R.string.release_to_load));
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getView().getContext().getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                if (refreshListView.isHeaderShown()) {
                    mPageNum = 1;
                    LoadInfo();
                } else {
                    mPageNum++;
                    LoadInfo();
                }
            }
        });
    }
    protected abstract void LoadInfo();

    protected void showNewsList(List list){
        mTextView.setVisibility(View.GONE);
        if (mPageNum == 1) {
            mList = list;
            mAdapter.setNewsList(list);
        } else {
            mList.addAll(list);
        }

        mAdapter.notifyDataSetChanged();
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                processItemClick(position);
            }
        });


        refreshListView.onRefreshComplete();

    }
    public abstract void processItemClick(int position);

    public void replaceFrag(Fragment fragment,Bundle bundle){
        fragment.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(this);
        transaction.add(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
