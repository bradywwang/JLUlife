package com.brady.jlulife.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.brady.jlulife.Adapters.JWCAdapter;
import com.brady.jlulife.Models.Listener.OnListinfoGetListener;
import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Models.NewsModel;
import com.brady.jlulife.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsListFragment extends Fragment {
    private PullToRefreshListView refreshListView;
    Fragment fragment = null;
    JWCAdapter mAdapter = null;
    List mList = null;
    int mPageNum = 1;
    private String mAction;
    private boolean isLoaded;
    private Fragment mFragment;

    public NewsListFragment() {
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPageNum = 1;
        return inflater.inflate(R.layout.fragment_jwquery, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        Bundle bundle = getArguments();
        String action = bundle.getString("action");
        mAction = action;
        if(mList.size()==0) {
            LoadInfo();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void initComponents(View view){
        refreshListView = (PullToRefreshListView) view.findViewById(R.id.jwc_listview);
        mList = new ArrayList();
        mAdapter = new JWCAdapter(getActivity(), R.layout.item_jwc, R.layout.item_jwc, mList);
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
    private void LoadInfo(){
        NewsModel.getInstance().getNewsList(mAction,mPageNum, new OnListinfoGetListener() {
            @Override
            public void onGetInfoSuccess(final List list) {
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
                        NewsBaseInfo baseInfo = (NewsBaseInfo) mList.get(position - 1);
                        Bundle bundle = new Bundle();
                        bundle.putString("action",mAction);
                        bundle.putString("href", baseInfo.getHref());
                        NewsDetailFragment fragment = new NewsDetailFragment();
                        fragment.setArguments(bundle);
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.hide(mFragment);
                        transaction.add(R.id.main_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                refreshListView.onRefreshComplete();

            }

            @Override
            public void onGetInfoFail() {

            }
        });
    }
}
