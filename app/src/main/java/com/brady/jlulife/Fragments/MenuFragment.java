package com.brady.jlulife.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.brady.jlulife.Activities.CJCXAuthActivity;
import com.brady.jlulife.Activities.DrcomLoginActivity;
import com.brady.jlulife.Activities.JLUNewsListActivity;
import com.brady.jlulife.Activities.JWNewsListActivity;
import com.brady.jlulife.Activities.LibrarySearchActivity;
import com.brady.jlulife.Activities.ScoreListActivity;
import com.brady.jlulife.Activities.SettingActivity;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.Activities.SlidingMenuMainActivity;
import com.brady.jlulife.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * A placeholder fragment containing a simple view.
 */
public class MenuFragment extends BaseFragment {
    private ListView listView;
    private Context mContext;
    private MenuFragment mFragment;
    private LinearLayout mSettingLayout;

    public MenuFragment() {
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        String[] strings = new String[]{
                "我的课表","校园网登陆","校内通知","教务通知","成绩查询","图书馆"
        };
        listView = (ListView) view.findViewById(R.id.lvfunction);
        mSettingLayout = (LinearLayout) view.findViewById(R.id.setting_layout);
        mSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(SettingActivity.class);
            }
        });
        listView.setAdapter(new ArrayAdapter<String>(mContext,R.layout.function_item,strings));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
//                        FragmentControler.addFragment(mFragment, R.id.main_container, FragmentControler.TAG_COURSE_LIST);
                        closeMenu(true);
                        break;
                    }
                    case 1:{
                        startNewActivity(DrcomLoginActivity.class);
//                        FragmentControler.addFragment(mFragment, R.id.main_container, FragmentControler.TAG_DRCOM_LOGIN);
                        closeMenu(false);
                        break;
                    }
                    case 2:{
                        startNewActivity(JLUNewsListActivity.class);
//                        FragmentControler.addFragment(mFragment, R.id.main_container, FragmentControler.TAG_JLU_NEWS_LIST);
                        closeMenu(false);
                        break;
                    }
                    case 3:{
                        startNewActivity(JWNewsListActivity.class);
//                        FragmentControler.addFragment(mFragment, R.id.main_container, FragmentControler.TAG_JW_NEWS_LIST);
                        closeMenu(false);
                        break;
                    }
                    case 4:{
                        if(UIMSModel.getInstance(mContext).isLoginIn()){
                            startNewActivity(ScoreListActivity.class);
//                            FragmentControler.addFragment(mFragment, R.id.main_container, FragmentControler.TAG_SCORE_LIST);
                        }else {
                            startNewActivity(CJCXAuthActivity.class);
//                            FragmentControler.addFragment(mFragment, R.id.main_container, FragmentControler.TAG_CJCX_AUTH);
                        }
                        closeMenu(false);
                        break;
                    }case 5:{
                        startNewActivity(LibrarySearchActivity.class);
//                        FragmentControler.addFragment(mFragment, R.id.main_container, FragmentControler.TAG_LIBRARY_SEARCH);
                        closeMenu(false);
                        break;
                    }
                    default:{
                        Toast.makeText(mContext,"The function is commit soon",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void closeMenu(boolean isShowAnim){
        SlidingMenu menu = ((SlidingMenuMainActivity) getActivity()).getMenu();
        if(isShowAnim!=false&&menu.isMenuShowing()){
            menu.showContent(isShowAnim);
        }
    }
}
