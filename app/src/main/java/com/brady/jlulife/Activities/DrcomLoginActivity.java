package com.brady.jlulife.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.brady.jlulife.Fragments.DrcomLoginFragment;
import com.brady.jlulife.Fragments.LoginSuccessFragment;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.Utils;

/**
 * Created by brady on 15-11-30.
 */
public class DrcomLoginActivity extends BaseActivity {
    ProgressDialog dialog;

    protected void showDialog(){
        dialog.show();
    }

    protected void hideDialog(){
        if(dialog!=null&&dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void initProgressDialog(){
        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragment() {
        initProgressDialog();
        showDialog();
        Utils.checkislogin(new Utils.LoginStateListener() {
            @Override
            public void isLogin() {
                hideDialog();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_container, LoginSuccessFragment.getInstance());
                transaction.commitAllowingStateLoss();
            }

            @Override
            public void needLogin() {
                hideDialog();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_container, DrcomLoginFragment.getInstance());
                transaction.commitAllowingStateLoss();
            }
        });


    }
}
