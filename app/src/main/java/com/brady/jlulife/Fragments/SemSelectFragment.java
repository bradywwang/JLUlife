package com.brady.jlulife.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public SemSelectFragment() {

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

    public void getSemesters(){
        uimsModel.getSemesters(new OnListinfoGetListener() {
            @Override
            public void onGetInfoSuccess(List list) {
                mSemList = list;
                SemesterAdapter adapter = new SemesterAdapter(mContext, R.layout.spinner_item, list);
                mSpinner.setAdapter(adapter);
            }

            @Override
            public void onGetInfoFail() {

            }
        });
    }

    public void initComponents(View view){
        mSpinner = (Spinner) view.findViewById(R.id.sem_sel_spinner);
        btnSync = (Button) view.findViewById(R.id.btn_sem_sync);
    }

    public void syncInfo(){
        TermList term = (TermList) mSpinner.getSelectedItem();
        if(term==null)
            return;
        String terId = term.getTermId();
        uimsModel.syncLessonSchedule(Integer.parseInt(terId), mContext,new OnAsyncLoadListener(){

            @Override
            public void onGetInfoSuccess() {
                Toast.makeText(mContext,"同步成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetInfoFail() {

            }
        });

    }
}
