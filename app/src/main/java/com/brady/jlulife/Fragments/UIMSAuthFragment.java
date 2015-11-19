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
import android.widget.Toast;

import com.brady.jlulife.Models.Listener.LoginListener;
import com.brady.jlulife.Models.Listener.OnListinfoGetListener;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.R;
import com.loopj.android.http.AsyncHttpClient;



public class UIMSAuthFragment extends BaseFragment {
    EditText metuname;
    EditText metpwd;
    CheckBox mcboxRemember;
    CheckBox mcboxAutoLogin;
    Button btnLogin;
    AsyncHttpClient client;
    UIMSModel uimsModel;
    Context mContext;
    private static UIMSAuthFragment instance;

    public UIMSAuthFragment() {
        instance = this;
    }

    public static UIMSAuthFragment getInstance(){
        if(instance == null)
            instance = new UIMSAuthFragment();
        return instance;
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

    }

    public void loginOauth(String uname,String pwd){
        if(uimsModel!=null) {
            uimsModel.login(UIMSModel.LOGIN_NORMAL_MODE,uname, pwd, new LoginListener() {
                @Override
                public void onLoginSuccess() {
                    showSemSelFrag();
                }
                @Override
                public void onLoginFailure(String failReason) {
                    Toast.makeText(mContext,failReason,Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Log.i(getClass().getSimpleName(),"null 1");
        }
    }


    public void showSemSelFrag(){
        repleceFragment(R.id.main_container,new SemSelectFragment());
    }

}

