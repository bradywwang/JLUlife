package com.brady.jlulife.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.brady.jlulife.R;

/**
 * Created by brady on 15-11-16.
 */
public class BaseFragment extends Fragment{
    public void repleceFragment(Fragment fragment){
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
    }
}
