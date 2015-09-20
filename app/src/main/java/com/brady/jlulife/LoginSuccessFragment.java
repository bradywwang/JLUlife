package com.brady.jlulife;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.drcom.Android.DrCOMWS.Tool.DrCOMWSManagement;
import com.drcom.Android.DrCOMWS.listener.OnclientLogoutListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginSuccessFragment extends Fragment {
    TextView flowused;
    TextView timeUsed;
    Button btnLogout;
    ProgressDialog mProgressDialog;

    public LoginSuccessFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_success, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        final DrCOMWSManagement management = new DrCOMWSManagement(getActivity());
        flowused.setText(management.getFlowStatus());
        timeUsed.setText(management.getTimeStatus());
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("注销中,请稍后");
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "is clicked");
                mProgressDialog.show();
                management.clientLogout(new OnclientLogoutListener() {
                    @Override
                    public void clientLogoutFail(int paramInt) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("注销失败");
                        builder.setPositiveButton(R.string.conform, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        mProgressDialog.dismiss();
                        builder.show();
                    }

                    @Override
                    public void clientLogoutSuccess(boolean paramBoolean) {
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        DrcomLoginFragment fragment = new DrcomLoginFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("logout", true);
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.drcom_login_status, fragment);
                        transaction.commit();
                        mProgressDialog.dismiss();
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
