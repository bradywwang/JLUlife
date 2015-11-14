package com.brady.jlulife.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.brady.jlulife.Models.Listener.LoginListener;
import com.brady.jlulife.Models.Listener.OnListinfoGetListener;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.R;
import com.loopj.android.http.AsyncHttpClient;

import java.util.List;


public class UIMSAuthFragment extends Fragment {
    EditText metuname;
    EditText metpwd;
    CheckBox mcboxRemember;
    CheckBox mcboxAutoLogin;
    Button btnLogin;
    AsyncHttpClient client;
    UIMSModel uimsModel;
    Context mContext;

    public UIMSAuthFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        initComponents(view);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = metuname.getText().toString();
                String pwd = metpwd.getText().toString();
                loginOauth(uname, pwd);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_uims_oauth, container, false);
    }

    void initComponents(View view){
        metuname = (EditText) view.findViewById(R.id.uims_uname);
        metpwd = (EditText) view.findViewById(R.id.uims_pwd);
        mcboxAutoLogin = (CheckBox) view.findViewById(R.id.uims_auto_login);
        mcboxRemember = (CheckBox) view.findViewById(R.id.uims_remember_pwd);
        btnLogin = (Button) view.findViewById(R.id.btn_uims_login);
        client = new AsyncHttpClient();
        uimsModel = UIMSModel.getInstance(mContext);
//        Log.i(getClass().getSimpleName(), "null 2");

    }

    public void loginOauth(String uname,String pwd){
        if(uimsModel!=null) {
            uimsModel.login(uname, pwd, new LoginListener() {
                @Override
                public void onLoginSuccess() {
                    getSemesters();
                }
                @Override
                public void onLoginFailure(String failReason) {
                    Log.i("fail",failReason);
                }
            });
        }else {
            Log.i(getClass().getSimpleName(),"null 1");
        }
    }

    public void getSemesters(){
        uimsModel.getSemesters(new OnListinfoGetListener() {
            @Override
            public void onGetInfoSuccess(List list) {

            }

            @Override
            public void onGetInfoFail() {

            }
        });
//        uimsModel.getCurrentInfo(mContext);

    }
    public void showSemSelFrag(){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

    }

}

