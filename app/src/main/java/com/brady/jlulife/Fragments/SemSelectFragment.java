package com.brady.jlulife.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.brady.jlulife.Adapters.SemesterAdapter;
import com.brady.jlulife.Entities.TermList;
import com.brady.jlulife.Models.Listener.OnAsyncLoadListener;
import com.brady.jlulife.Models.Listener.OnListinfoGetListener;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.R;

import java.util.List;

public class SemSelectFragment extends BaseFragment {
    private UIMSModel uimsModel;
    private Context mContext;
    private List mSemList;
    private Spinner mSpinner;
    private Button btnSync;
    SemesterAdapter adapter;
    private static SemSelectFragment instance;

    public SemSelectFragment() {
        instance = this;
    }

    public static SemSelectFragment getInstance() {
        if (instance == null)
            instance = new SemSelectFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sem_select, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        uimsModel = UIMSModel.getInstance(mContext);
        initComponents(view);
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncInfo();
            }
        });
        getSemesters();
    }

    public void getSemesters() {
        if (mSemList == null || mSemList.size() == 0) {
            uimsModel.getSemesters(new OnListinfoGetListener() {
                @Override
                public void onGetInfoSuccess(List list) {
                    mSemList = list;
                    adapter = new SemesterAdapter(mContext, R.layout.spinner_item, list);
                    mSpinner.setAdapter(adapter);
                }

                @Override
                public void onGetInfoFail() {

                }
            });
        }else {
            mSpinner.setAdapter(adapter);
        }
    }

    public void initComponents(View view) {
        mSpinner = (Spinner) view.findViewById(R.id.sem_sel_spinner);
        btnSync = (Button) view.findViewById(R.id.btn_sem_sync);
    }

    public void syncInfo() {
        TermList term = (TermList) mSpinner.getSelectedItem();
        if (term == null)
            return;
        String terId = term.getTermId();
        showDialog();
        uimsModel.syncLessonSchedule(Integer.parseInt(terId), new OnAsyncLoadListener() {
            @Override
            public void onGetInfoSuccess() {
                hideDialog();
                Toast.makeText(mContext, "同步成功", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

            @Override
            public void onGetInfoFail() {
                hideDialog();
            }
        });

    }
}
