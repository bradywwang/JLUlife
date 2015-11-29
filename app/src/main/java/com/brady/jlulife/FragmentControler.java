package com.brady.jlulife;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.brady.jlulife.Fragments.BaseFragment;
import com.brady.jlulife.Fragments.CjcxAuthFragment;
import com.brady.jlulife.Fragments.ClassSyncFragment;
import com.brady.jlulife.Fragments.CourseListFragment;
import com.brady.jlulife.Fragments.DrcomLoginFragment;
import com.brady.jlulife.Fragments.LibrarySearchFragment;
import com.brady.jlulife.Fragments.LoginSuccessFragment;
import com.brady.jlulife.Fragments.MenuFragment;
import com.brady.jlulife.Fragments.News.JLUNewsDetailFragment;
import com.brady.jlulife.Fragments.News.JLUNewsListFragment;
import com.brady.jlulife.Fragments.News.JWNewsDetailFragment;
import com.brady.jlulife.Fragments.News.JWNewsListFragment;
import com.brady.jlulife.Fragments.ScoreListFragment;
import com.brady.jlulife.Fragments.SemSelectFragment;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by brady on 15-11-29.
 */
public class FragmentControler {
    public static final int TAG_CJCX_AUTH = 1000001;
    public static final int TAG_CLASS_SYNC = 1000002;
    public static final int TAG_COURSE_LIST = 1000003;
    public static final int TAG_DRCOM_LOGIN = 1000004;
    public static final int TAG_LIBRARY_SEARCH = 1000005;
    public static final int TAG_LOGIN_SUCCESS = 1000006;
    public static final int TAG_MENU_FRAGMENT = 1000007;
    public static final int TAG_SCORE_LIST = 1000008;
    public static final int TAG_UIMS_AUTH = 1000009;
    public static final int TAG_SEM_SELECT = 1000010;
    public static final int TAG_JLU_NEWS_DETAIL = 1000011;
    public static final int TAG_JLU_NEWS_LIST = 1000012;
    public static final int TAG_JW_NEWS_DETAIL = 1000013;
    public static final int TAG_JW_NEWS_LIST = 1000014;



    private static HashMap<Integer, BaseFragment> fragmentMap = new HashMap<>();
//    private static HashMap<Integer, LinkedList<BaseFragment>> containerMap = new HashMap<>();

    /*public static void addFragment(Fragment currentFragment, int resId, int containerTAG, int fragmentTag) {
        BaseFragment fragment;
        LinkedList<BaseFragment> list;
        if (fragmentMap.containsKey(fragmentTag)) {
            fragment = fragmentMap.get(fragmentTag);
        } else {
            if (!containerMap.containsKey(containerTAG)) {
                LinkedList<BaseFragment> baselist = new LinkedList<>();
                containerMap.put(containerTAG, baselist);
            }
            fragment = getFragmentByTag(fragmentTag);
            fragmentMap.put(fragmentTag, fragment);
        }
        list = containerMap.get(containerTAG);
        while (list.size() != 0 && (!(list.pop()).equals(fragment))) ;
        FragmentManager manager = currentFragment.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.replace(resId, fragment);
        transaction.commit();
    }*/

    public static void addFragment(FragmentManager manager, int resId, int fragmentTag) {
        BaseFragment fragment;
        LinkedList<BaseFragment> list;
        if (fragmentMap.containsKey(fragmentTag)) {
            fragment = fragmentMap.get(fragmentTag);
        } else {
            /*if (!containerMap.containsKey(containerTAG)) {
                LinkedList<BaseFragment> baselist = new LinkedList<>();
                containerMap.put(containerTAG, baselist);
            }*/
            fragment = getFragmentByTag(fragmentTag);
            fragmentMap.put(fragmentTag, fragment);
        }
//        list = containerMap.get(containerTAG);
//        while (list.size() != 0 && (!(list.pop()).equals(fragment))) ;
        if(manager==null){
            Log.e("Manger","manager is null");
            return;
        }
        commitTransaction(manager,fragment,resId);
    }

    public static void addFragment(Fragment currentFragment, int resId, int fragmentTag) {
        BaseFragment fragment;
        LinkedList<BaseFragment> list;
        if (fragmentMap.containsKey(fragmentTag)) {
            fragment = fragmentMap.get(fragmentTag);
        } else {
            /*if (!containerMap.containsKey(containerTAG)) {
                LinkedList<BaseFragment> baselist = new LinkedList<>();
                containerMap.put(containerTAG, baselist);
            }*/
            fragment = getFragmentByTag(fragmentTag);
            fragmentMap.put(fragmentTag, fragment);
        }
//        list = containerMap.get(containerTAG);
//        while (list.size() != 0 && (!(list.pop()).equals(fragment))) ;
        FragmentManager manager = currentFragment.getFragmentManager();
        if(manager==null){
            Log.e("Manger","manager is null");
            return;
        }
        commitTransaction(manager,fragment,resId);
    }

    public static void addFragment(Fragment currentFragment,Bundle bundle, int resId, int fragmentTag) {
        BaseFragment fragment;
        LinkedList<BaseFragment> list;
        if (fragmentMap.containsKey(fragmentTag)) {
            fragment = fragmentMap.get(fragmentTag);
        } else {
            /*if (!containerMap.containsKey(containerTAG)) {
                LinkedList<BaseFragment> baselist = new LinkedList<>();
                containerMap.put(containerTAG, baselist);
            }*/
            fragment = getFragmentByTag(fragmentTag);
            fragmentMap.put(fragmentTag, fragment);
        }
        fragment.setArguments(bundle);
//        list = containerMap.get(containerTAG);
//        while (list.size() != 0 && (!(list.pop()).equals(fragment))) ;
        FragmentManager manager = currentFragment.getFragmentManager();
        commitTransaction(manager,fragment,resId);
    }

    public static void commitTransaction(FragmentManager manager,Fragment fragment,int resId){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.replace(resId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static boolean onBackPressed(Fragment fragment){

        return false;
    }

    private static BaseFragment getFragmentByTag(int fragmentTag) {
        BaseFragment baseFragment = null;
        switch (fragmentTag) {
            case TAG_CJCX_AUTH: {
                baseFragment = new CjcxAuthFragment();
                break;
            }
            case TAG_CLASS_SYNC: {
                baseFragment = new ClassSyncFragment();
                break;
            }
            case TAG_COURSE_LIST: {
                baseFragment = new CourseListFragment();
                break;
            }
            case TAG_DRCOM_LOGIN: {
                baseFragment = new DrcomLoginFragment();
                break;
            }
            case TAG_LIBRARY_SEARCH: {
                baseFragment = new LibrarySearchFragment();
                break;
            }
            case TAG_LOGIN_SUCCESS: {
                baseFragment = new LoginSuccessFragment();
                break;
            }
            case TAG_MENU_FRAGMENT: {
                baseFragment = new MenuFragment();
                break;
            }
            case TAG_SCORE_LIST: {
                baseFragment = new ScoreListFragment();
                break;
            }
            case TAG_SEM_SELECT: {
                baseFragment = new SemSelectFragment();
                break;
            }
            case TAG_JLU_NEWS_DETAIL: {
                baseFragment = new JLUNewsDetailFragment();
                break;
            }
            case TAG_JLU_NEWS_LIST: {
                baseFragment = new JLUNewsListFragment();
                break;
            }
            case TAG_JW_NEWS_DETAIL: {
                baseFragment = new JWNewsDetailFragment();
                break;
            }
            case TAG_JW_NEWS_LIST: {
                baseFragment = new JWNewsListFragment();
                break;
            }
        }
        return baseFragment;
    }


}
