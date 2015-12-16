package com.brady.jlulife.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brady.jlulife.Models.Listener.LoginListener;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;
import com.loopj.android.http.AsyncHttpClient;



public abstract class UIMSAuthFragment extends BaseFragment {
    private EditText metuname;
    private EditText metpwd;
    private CheckBox mcboxRemember;
    private CheckBox mcboxAutoLogin;
    protected CheckBox mLoginOutside;
    private Button btnLogin;
    private TextView tvHelpInfo;
    AsyncHttpClient client;
    UIMSModel uimsModel;
    Context mContext;
    private boolean isFirstShown;
    private SharedPreferences sf;
    private static final String SAVED_NAME  = "saved_name";
    private static final String SAVED_PWD  = "saved_password";
    private static final String IS_AUTO_LOGIN = "is_auto_login";
    private static final String IS_SAVE_PWD = "is_saved_pwd";
    private static final String IS_LOGIN_OUTSIDE = "is_login_outside";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        initComponents(view);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
                String uname = metuname.getText().toString();
                String pwd = metpwd.getText().toString();
                loginOauth(uname, pwd);
                Log.e(getClass().getSimpleName(), "point 4");
            }
        });
        Loadinfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstShown = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setTitle("教务登陆");
        return inflater.inflate(R.layout.fragment_uims_oauth, container, false);
    }

    void initComponents(View view){
        tvHelpInfo = (TextView) view.findViewById(R.id.tv_login_info);
        tvHelpInfo.setText(getResources().getString(R.string.tv_help_uims_login));
        metuname = (EditText) view.findViewById(R.id.uims_uname);
        metpwd = (EditText) view.findViewById(R.id.uims_pwd);
        mcboxAutoLogin = (CheckBox) view.findViewById(R.id.uims_auto_login);
        mcboxRemember = (CheckBox) view.findViewById(R.id.uims_remember_pwd);
        btnLogin = (Button) view.findViewById(R.id.btn_uims_login);
        client = new AsyncHttpClient();
        uimsModel = UIMSModel.getInstance(mContext);
        mLoginOutside = (CheckBox) view.findViewById(R.id.uims_outside);
        sf = getActivity().getSharedPreferences(ConstValue.SHARED_UIMS_INFO, Activity.MODE_PRIVATE);
    }

    public void loginOauth(String uname,String pwd){
        showDialog();
        if(uimsModel!=null) {
            if(mLoginOutside.isChecked()){
                uimsModel.login(UIMSModel.LOGIN_CJCX_MODE, uname, pwd, new LoginListener() {
                    @Override
                    public void onLoginSuccess() {
                        hideDialog();
                        showNextPage();
                    }

                    @Override
                    public void onLoginFailure(String failReason) {
                        hideDialog();
                        Toast.makeText(mContext, failReason, Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                uimsModel.login(UIMSModel.LOGIN_NORMAL_MODE, uname, pwd, new LoginListener() {
                    @Override
                    public void onLoginSuccess() {
                        hideDialog();
                        showNextPage();
                    }

                    @Override
                    public void onLoginFailure(String failReason) {
                        hideDialog();
                        Toast.makeText(mContext, failReason, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else {
        }
    }

    private void saveInfo() {
        SharedPreferences.Editor editor = sf.edit();
        editor.putString(SAVED_NAME, metuname.getText().toString());
        if (mcboxRemember.isChecked()) {
            editor.putString(SAVED_PWD, metpwd.getText().toString());
        }
        editor.putBoolean(IS_AUTO_LOGIN, mcboxAutoLogin.isChecked());
        editor.putBoolean(IS_SAVE_PWD, mcboxRemember.isChecked());
        if(mLoginOutside.getVisibility()==View.VISIBLE){
            editor.putBoolean(IS_LOGIN_OUTSIDE,mLoginOutside.isChecked());
        }
        editor.commit();
    }

    private void Loadinfo(){
        Boolean isAutoLogin = sf.getBoolean(IS_AUTO_LOGIN,false);
        Boolean isSavedPwd = sf.getBoolean(IS_SAVE_PWD,false);
        Boolean isLoginOutside = sf.getBoolean(IS_LOGIN_OUTSIDE,false);
        String uName = sf.getString(SAVED_NAME,"");
        String pwd = sf.getString(SAVED_PWD,"");
        metuname.setText(uName);
        metpwd.setText(pwd);
        mcboxAutoLogin.setChecked(isAutoLogin);
        mcboxRemember.setChecked(isSavedPwd);
        if(mLoginOutside.getVisibility()==View.VISIBLE){
            mLoginOutside.setChecked(isLoginOutside);
        }
        String action = getActivity().getIntent().getAction();
        if(TextUtils.isEmpty(action)&&isAutoLogin){
            btnLogin.performClick();
        }
    }
    public abstract void showNextPage();

}

