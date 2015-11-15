package com.brady.jlulife.Fragments;

import android.app.ProgressDialog;
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
import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Models.JWCModel;
import com.brady.jlulife.Models.Listener.OnObjectGetListener;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class JWQueryFragment extends Fragment {
    private PullToRefreshListView refreshListView;
    private ProgressDialog dialog;
    Fragment fragment = null;
    JWCAdapter mAdapter = null;
    List mList = null;
    int mPageNum = 1;

    public JWQueryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPageNum = 1;
        LoadInfo();
        return inflater.inflate(R.layout.fragment_jwquery, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void initComponents(View view){
        refreshListView = (PullToRefreshListView) view.findViewById(R.id.jwc_listview);
        dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("加载中，请稍后");
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
                if(refreshListView.isHeaderShown()){
                    mPageNum = 1;
                    LoadInfo();
                }else{
                    mPageNum++;
                    LoadInfo();
                }
            }
        });
        dialog.show();

    }
    private void LoadInfo(){
        JWCModel.getInstance().getNewsBaseInfo(mPageNum, new OnListinfoGetListener() {
            @Override
            public void onGetInfoSuccess(final List list) {
                if(mPageNum ==1){
                    mList = list;
                    mAdapter.setNewsList(list);
                }else{
                    mList.addAll(list);
                }

                mAdapter.notifyDataSetChanged();
                refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.show();
                        final NewsBaseInfo baseInfo = (NewsBaseInfo) mList.get(position-1);
                        final Bundle bundle = new Bundle();
                        JWCModel.getInstance().getNewsContent(baseInfo.getHref(), new OnObjectGetListener() {
                            @Override
                            public void onGetInfoSuccess(Object object) {
                                News news = (News) object;
                                bundle.putString("title", news.getTitle());
                                bundle.putString("dep", news.getSubmitDepartment());
                                bundle.putString("date", news.getSubmitTime());
                                bundle.putString("content", news.getContent());
                                fragment = new NewsDetailFragment();
                                fragment.setArguments(bundle);
                                dialog.dismiss();
                                FragmentManager manager = getFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.main_container, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }

                            @Override
                            public void onGetInfoFail() {
                                dialog.dismiss();
                                bundle.putString("source", ConstValue.JWC_DETAIL_SOURCE);
                                bundle.putString("href", baseInfo.getHref());
                                fragment = new LoadFailFragment();
                                fragment.setArguments(bundle);
                                FragmentManager manager = getFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.main_container, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        });

                    }
                });
                dialog.dismiss();
                refreshListView.onRefreshComplete();

            }

            @Override
            public void onGetInfoFail() {
                dialog.dismiss();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("source", ConstValue.JWC_SOURCE);
                LoadFailFragment failFragment = new LoadFailFragment();
                failFragment.setArguments(bundle);
                transaction.replace(R.id.main_container,failFragment);
                transaction.commit();
            }
        });
    }
}
