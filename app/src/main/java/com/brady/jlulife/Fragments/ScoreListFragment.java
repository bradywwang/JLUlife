package com.brady.jlulife.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.brady.jlulife.Activities.CJCXAuthActivity;
import com.brady.jlulife.Adapters.ScoreAdapter;
import com.brady.jlulife.Adapters.SemesterAdapter;
import com.brady.jlulife.Entities.TermList;
import com.brady.jlulife.Models.Listener.OnListinfoGetListener;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreListFragment extends BaseFragment {
    private Spinner mSpinner;
    private Button mBtnQuery;
    private ListView mLvScore;
    private UIMSModel uimsModel;
    private Context mContext;
    private List mSemList;
    private List mScoreList;
    private ScoreAdapter mAdapter;
    private SemesterAdapter semesterAdapter;
    private static ScoreListFragment instance;

    public ScoreListFragment() {
        // Required empty public constructor
    }
    public static ScoreListFragment getInstance(){
        if(instance==null)
            instance = new ScoreListFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setTitle("我的成绩");
        return inflater.inflate(R.layout.fragment_score_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        uimsModel = UIMSModel.getInstance(mContext);
        initComponents(view);
        mScoreList = new ArrayList();
        mAdapter = new ScoreAdapter(mContext,R.layout.item_cjcx,mScoreList);
        if(mSemList==null||mSemList.size()==0) {
            getSemesters();
        }else{
            mSpinner.setAdapter(semesterAdapter);
        }
        mLvScore.setAdapter(mAdapter);
        mBtnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryScore();
            }
        });
    }

    private void initComponents(View view){
        mSpinner = (Spinner) view.findViewById(R.id.sem_sel_spinner);
        mBtnQuery = (Button) view.findViewById(R.id.btn_query);
        mLvScore = (ListView) view.findViewById(R.id.lv_scoreList);
    }
    public void getSemesters(){
        Log.i("loginMethod", String.valueOf(uimsModel.getmLoginMethod()));
        uimsModel.getSemesters(new OnListinfoGetListener() {
            @Override
            public void onGetInfoSuccess(List list) {
                mSemList = list;
                semesterAdapter = new SemesterAdapter(mContext, R.layout.spinner_item, list);
                mSpinner.setAdapter(semesterAdapter);
            }

            @Override
            public void onGetInfoFail() {

            }
        });
    }
    private void queryScore(){
        TermList term = (TermList) mSpinner.getSelectedItem();
        if (term == null)
            return;
        String terId = term.getTermId();
        showDialog();
        uimsModel.queryScore(Integer.parseInt(terId), new OnListinfoGetListener() {
            @Override
            public void onGetInfoSuccess(List list) {
                hideDialog();
                mScoreList.removeAll(mScoreList);
                mScoreList.addAll(list);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "共查询到" + list.size() + "条记录", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetInfoFail() {
                hideDialog();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_courselist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_logout){
            Log.i("gets","logout");
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
    public void logout(){
        UIMSModel.getInstance(mContext).logout();
        Intent intent = new Intent(mContext,CJCXAuthActivity.class);
        intent.setAction(ConstValue.UIMS_LOGOUT);
        startActivity(intent);
        getActivity().finish();
    }
}
