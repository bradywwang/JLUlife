package com.brady.jlulife;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.drcom.Android.DrCOMWS.Jni;
import com.drcom.Android.DrCOMWS.Tool.DrCOMWSManagement;
import com.drcom.Android.DrCOMWS.listener.OnclientLoginListener;
import com.drcom.Android.DrCOMWS.listener.OnclientLogoutListener;


/**
 * A placeholder fragment containing a simple view.
 */
public class DrcomLoginFragment extends Fragment {
    private static final int LOGIN_SUCCESS = 24;
    private static final int LOGIN_FAILURE = 392;
    private static final String IS_SAVED_PASSWORD = "is_saved_password";
    private static final String IS_AUTO_LOGIN = "is_auto_login";
    private static final String SAVED_NAME  = "saved_name";
    private static final String SAVED_PWD  = "saved_password";

    EditText metUname;
    EditText metUpwd;
    CheckBox cbRememberPwd;
    CheckBox cbautoLogin;
    Button btnLogin;
    ProgressDialog mProgressDialog;
    DrCOMWSManagement management;

    public DrcomLoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drcom_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        management = new DrCOMWSManagement(getActivity());
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ConstValue.SHARED_DRCOM_INFO, Activity.MODE_PRIVATE);
        Bundle arguments = getArguments();
        Boolean islogout = false;
        if(arguments!=null)
            islogout = arguments.getBoolean("logout");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = ProgressDialog.show(getContext(), "", "登陆中，请稍后");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SAVED_NAME, metUname.getText().toString());
                editor.putBoolean(IS_SAVED_PASSWORD,cbRememberPwd.isChecked());
                editor.putBoolean(IS_AUTO_LOGIN, cbautoLogin.isChecked());
                if(cbRememberPwd.isChecked()){
                    editor.putString(SAVED_PWD,metUpwd.getText().toString());
                }else {
                    editor.putString(SAVED_PWD,"");
                }
                editor.commit();
                mProgressDialog.setCanceledOnTouchOutside(true);
                mProgressDialog.show();
                management.clientLogin(metUname.getText().toString(), metUpwd.getText().toString(), new OnclientLoginListener() {
                    @Override
                    public void clientLoginFail(int paramInt) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(management.getErrorStringByCode(paramInt));
                        builder.setPositiveButton(R.string.conform, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void clientLoginSuccess(boolean paramBoolean) {
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        DrcomLoginActivity activity = (DrcomLoginActivity) getActivity();
                        transaction.replace(R.id.drcom_login_status, activity.getLoginSuccessFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        mProgressDialog.dismiss();
                    }
                });
            }
        });
        cbRememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                    cbautoLogin.setChecked(false);
            }
        });
        Button logout = (Button) getView().findViewById(R.id.btn_drcom_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jni authinfo = new Jni();
                Log.i(getClass().getSimpleName(), "time:" + authinfo.getFluxStatus());
                Log.i(getClass().getSimpleName(), "flow:" + authinfo.getTimeStatus());
            }
        });
        boolean isAutoLogin = sharedPreferences.getBoolean(IS_AUTO_LOGIN, false);
        boolean isSavedPwd = sharedPreferences.getBoolean(IS_SAVED_PASSWORD, false);
        String uName = sharedPreferences.getString(SAVED_NAME,"");
        String pwd = sharedPreferences.getString(SAVED_PWD,"");
        cbautoLogin.setChecked(isAutoLogin);
        cbRememberPwd.setChecked(isSavedPwd);
        metUname.setText(uName);
        if(isSavedPwd){
            metUpwd.setText(pwd);
        }
        if(isAutoLogin&&!islogout){
            btnLogin.performClick();
        }
    }

    public void initComponents(View view) {
        metUname = (EditText) view.findViewById(R.id.et_drcom_uname);
        metUpwd = (EditText) view.findViewById(R.id.et_drcom_pwd);
        cbRememberPwd = (CheckBox) view.findViewById(R.id.checkbox_remember_pwd);
        cbautoLogin = (CheckBox) view.findViewById(R.id.checkbox_auto_login);
        btnLogin = (Button) view.findViewById(R.id.btn_drcom_login);

    }


}
