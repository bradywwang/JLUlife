package com.brady.jlulife.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.brady.jlulife.Activities.BaseActivity;
import com.brady.jlulife.Activities.DrcomLoginActivity;

import org.jsoup.select.Evaluator;


/**
 * Created by brady on 15-11-16.
 */
public class BaseFragment extends Fragment{
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    /*public void repleceFragment(Fragment fragment){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(((ViewGroup)(getView().getParent())).getId(),fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void repleceFragment(int viewId,Fragment fragment){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace((getActivity().findViewById(viewId)).getId(),fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void repleceFragment(Fragment originFragment,Fragment desFragment){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(originFragment);
        transaction.add(((ViewGroup) (getView().getParent())).getId(), desFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

    public void setTitle (String title){
        getActivity().setTitle(title);
    }

    public void startNewActivity(Class<?> cls){
        Intent intent = new Intent(getActivity(),cls);
        startActivity(intent);
    }

}
