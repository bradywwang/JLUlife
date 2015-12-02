package com.brady.jlulife.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.brady.jlulife.Activities.BaseActivity;
import com.brady.jlulife.Activities.DrcomLoginActivity;
import com.brady.jlulife.R;

import org.jsoup.select.Evaluator;


/**
 * Created by brady on 15-11-16.
 */
public class BaseFragment extends Fragment{
    private ProgressDialog dialog;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initProgressDialog();
    }

    public void setTitle (String title){
        getActivity().setTitle(title);
    }

    public void startNewActivity(Class<?> cls){
        Intent intent = new Intent(getActivity(),cls);
        startActivity(intent);
    }

    private void initProgressDialog(){
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.setCancelable(false);
    }

    protected void showDialog(){
        dialog.show();
    }

    protected void hideDialog(){
        if(dialog!=null&&dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
