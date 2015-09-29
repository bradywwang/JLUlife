package com.brady.jlulife.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.brady.jlulife.Adapters.JWCAdapter;
import com.brady.jlulife.CallbackListeners.OnListinfoGetListener;
import com.brady.jlulife.CallbackListeners.OnNewsDetailinfoGetListener;
import com.brady.jlulife.Entities.News;
import com.brady.jlulife.Entities.NewsBaseInfo;
import com.brady.jlulife.Models.JWCModel;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class JWQueryFragment extends Fragment {
    private int pageNum;
    private Button btnNext;
    private Button btnLast;
    private ListView JWCView;
    private ProgressDialog dialog;
    Fragment fragment = null;

    public JWQueryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jwquery, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        LoadInfo();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pageNum++;
                if(pageNum>1)
                btnLast.setEnabled(true);
                LoadInfo();
            }
        });
        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum --;
                if(pageNum<=1)
                    btnLast.setEnabled(false);
                LoadInfo();
            }
        });
    }

    private void initComponents(View view){
        pageNum = 1;
        btnLast = (Button) view.findViewById(R.id.btn_last);
        btnNext = (Button) view.findViewById(R.id.btn_next);
        JWCView = (ListView) view.findViewById(R.id.jwc_listview);
        btnLast.setEnabled(false);
        dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("加载中，请稍后");
    }
    private void LoadInfo(){
        dialog.show();
        JWCModel.getInstance().getNewsBaseInfo(pageNum, new OnListinfoGetListener() {
            @Override
            public void onGetInfoSuccess(final List list) {
                JWCAdapter jwcAdapter = new JWCAdapter(getActivity(), R.layout.item_jwc, R.layout.item_jwc, list);
                JWCView.setAdapter(jwcAdapter);
                JWCView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final NewsBaseInfo baseInfo = (NewsBaseInfo) list.get(position);
                        final Bundle bundle = new Bundle();
                        dialog.show();
                        JWCModel.getInstance().getNewsContent(baseInfo.getHref(), new OnNewsDetailinfoGetListener() {
                            @Override
                            public void onGetInfoSuccess(News news) {
                                bundle.putString("title", news.getTitle());
                                bundle.putString("dep", news.getSubmitDepartment());
                                bundle.putString("date", news.getSubmitTime());
                                bundle.putString("content", news.getContent());
                                fragment = new NewsDetailFragment();
                                fragment.setArguments(bundle);
                                dialog.dismiss();
                                FragmentManager manager = getFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.jwquery_container, fragment);
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
                                transaction.replace(R.id.jwquery_container, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        });

                    }
                });
                dialog.dismiss();
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
                transaction.replace(R.id.jwquery_container,failFragment);
                transaction.commit();
            }
        });
    }
}
