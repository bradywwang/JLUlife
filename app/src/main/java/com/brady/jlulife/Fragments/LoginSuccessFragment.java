package com.brady.jlulife.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.brady.jlulife.R;
import com.drcom.Android.DrCOMWS.Tool.DrCOMWSManagement;
import com.drcom.Android.DrCOMWS.listener.OnclientLogoutListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginSuccessFragment extends BaseFragment {
    TextView flowused;
    TextView timeUsed;
    Button btnLogout;
//    ProgressDialog mProgressDialog;
    LoginSuccessFragment mFragment;
    private static LoginSuccessFragment instance;
    public static LoginSuccessFragment getInstance(){
        if(instance == null){
            instance = new LoginSuccessFragment();
        }
        return instance;
    }

    public LoginSuccessFragment() {
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setTitle("登陆成功");
        return inflater.inflate(R.layout.fragment_login_success, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        final DrCOMWSManagement management = new DrCOMWSManagement(getActivity());
        flowused.setText(management.getFlowStatus());
        timeUsed.setText(management.getTimeStatus());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                management.clientLogout(new OnclientLogoutListener() {
                    @Override
                    public void clientLogoutFail(int paramInt) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("注销失败");
                        builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btnLogout.performClick();
                            }
                        });
                        builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        hideDialog();
                        builder.show();
                    }

                    @Override
                    public void clientLogoutSuccess(boolean paramBoolean) {
                        hideDialog();
                        getActivity().finish();
                    }
                });
            }
        });
    }

    void initComponents(View view) {
        flowused = (TextView) view.findViewById(R.id.tv_used_flow);
        timeUsed = (TextView) view.findViewById(R.id.tv_login_time);
        btnLogout = (Button) view.findViewById(R.id.btn_drcom_logout);
    }

}
