package com.brady.jlulife.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.brady.jlulife.SlidingMenuMainActivity;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * A placeholder fragment containing a simple view.
 */
public class MenuFragment extends BaseFragment {
    private ListView listView;
    private Context mContext;

    public MenuFragment() {
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
                "我的课表","校园网登陆","校内通知","教务通知","成绩查询","图书馆","吉大就业"
        };
        listView = (ListView) view.findViewById(R.id.lvfunction);
        listView.setAdapter(new ArrayAdapter<String>(mContext,R.layout.function_item,strings));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
                        repleceFragment(R.id.main_container,CourseListFragment.getInstance());
                        closeMenu();
                        break;
                    }
                    case 1:{
                        repleceFragment(R.id.main_container, DrcomLoginFragment.getInstance());
                        closeMenu();
                        break;
                    }
                    case 2:{
                        NewsListFragment fragment = NewsListFragment.getInstance();
                        Bundle argument = new Bundle();
                        argument.putString("action", ConstValue.FUNCTION_JLUNEWS);
                        fragment.setArguments(argument);
                        repleceFragment(R.id.main_container,fragment);
                        closeMenu();
                        break;
                    }
                    case 3:{
                        NewsListFragment fragment = NewsListFragment.getInstance();
                        Bundle argument = new Bundle();
                        argument.putString("action", ConstValue.FUNCTION_JWCX);
                        fragment.setArguments(argument);
                        repleceFragment(R.id.main_container,fragment);
                        closeMenu();
                        break;
                    }
                    default:{
                        Toast.makeText(mContext,"The function is commit soon",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




        /*final FragmentManager manager = getFragmentManager();
                ((Button) view.findViewById(R.id.main_drcom_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repleceFragment(R.id.main_container,new DrcomLoginFragment());
                closeMenu();
            }
        });
        ((Button) view.findViewById(R.id.main_jwQuery)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsListFragment fragment = new NewsListFragment();
                Bundle argument = new Bundle();
                argument.putString("action", ConstValue.FUNCTION_JWCX);
                fragment.setArguments(argument);
                repleceFragment(R.id.main_container,fragment);
                closeMenu();

            }
        });
        ((Button) view.findViewById(R.id.main_xntz)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsListFragment fragment = new NewsListFragment();
                Bundle argument = new Bundle();
                argument.putString("action", ConstValue.FUNCTION_JLUNEWS);
                fragment.setArguments(argument);
                repleceFragment(R.id.main_container,fragment);
                closeMenu();
            }
        });*/
    }

    private void closeMenu(){
        SlidingMenu menu = ((SlidingMenuMainActivity) getActivity()).getMenu();
        if(menu.isMenuShowing()){
            menu.showContent();
        }
    }
}
