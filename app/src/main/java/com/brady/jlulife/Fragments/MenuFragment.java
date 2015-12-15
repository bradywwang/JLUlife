package com.brady.jlulife.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.brady.jlulife.Activities.CJCXAuthActivity;
import com.brady.jlulife.Activities.DrcomLoginActivity;
import com.brady.jlulife.Activities.JLUNewsListActivity;
import com.brady.jlulife.Activities.JWNewsListActivity;
import com.brady.jlulife.Activities.LibrarySearchActivity;
import com.brady.jlulife.Activities.ScoreListActivity;
import com.brady.jlulife.Activities.SettingActivity;
import com.brady.jlulife.Entities.Weather.WeatherData;
import com.brady.jlulife.Models.Listener.OnObjectGetListener;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.Activities.SlidingMenuMainActivity;
import com.brady.jlulife.Models.WeatherModel;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * A placeholder fragment containing a simple view.
 */
public class MenuFragment extends BaseFragment {
    private ListView listView;
    private Context mContext;
    private MenuFragment mFragment;
    private LinearLayout mSettingLayout;
    private ImageView ivWeather;
    private TextView tvWeather;
    private TextView tvLowTem;
    private TextView tvHighTem;
    private SharedPreferences sf;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        sf = getActivity().getSharedPreferences(ConstValue.SHARED_WEATHER_INFO,Context.MODE_PRIVATE);
        initConments(view);
        initWeather();
        syncWeather();
    }

    private void closeMenu(boolean isShowAnim) {
        SlidingMenu menu = ((SlidingMenuMainActivity) getActivity()).getMenu();
        if (isShowAnim != false && menu.isMenuShowing()) {
            menu.showContent(isShowAnim);
        }
    }

    private void initWeather(){
        String weather = sf.getString("weather","");
        String lowTemp = sf.getString("lowtemp","");
        String highTemp = sf.getString("hightemp","");
        tvWeather.setText(weather);
        tvLowTem.setText(lowTemp);
        tvHighTem.setText(highTemp);
        ivWeather.setImageDrawable(getResources().getDrawable(getWeatherIcon(weather)));
    }

    private void syncWeather() {
        WeatherModel.getInstance(mContext).getWeatherDetail(new OnObjectGetListener() {
            @Override
            public void onGetInfoSuccess(Object object) {
                WeatherData data = (WeatherData) object;
                tvWeather.setText(data.getWeather());
                tvLowTem.setText(data.getL_tmp());
                tvHighTem.setText(data.getH_tmp());
                ivWeather.setImageDrawable(getResources().getDrawable(getWeatherIcon(data.getWeather())));
                SharedPreferences.Editor editor = sf.edit();
                editor.putString("weather", data.getWeather());
                editor.putString("lowtemp", data.getL_tmp());
                editor.putString("hightemp", data.getH_tmp());
                editor.commit();
            }

            @Override
            public void onGetInfoFail() {

            }
        });
    }

    private void initConments(View view){
        listView = (ListView) view.findViewById(R.id.lvfunction);
        mSettingLayout = (LinearLayout) view.findViewById(R.id.setting_layout);
        ivWeather = (ImageView) view.findViewById(R.id.image_weather);
        tvWeather = (TextView) view.findViewById(R.id.tv_weather);
        tvLowTem = (TextView) view.findViewById(R.id.tv_low_tem);
        tvHighTem = (TextView) view.findViewById(R.id.tv_high_tem);
        mSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(SettingActivity.class);
            }
        });
        String[] strings = new String[]{
                "我的课表", "校园网登陆", "校内通知", "教务通知", "成绩查询", "图书馆"
        };
        listView.setAdapter(new ArrayAdapter<String>(mContext, R.layout.function_item, strings));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        closeMenu(true);
                        break;
                    }
                    case 1: {
                        startNewActivity(DrcomLoginActivity.class);
                        closeMenu(false);
                        break;
                    }
                    case 2: {
                        startNewActivity(JLUNewsListActivity.class);
                        closeMenu(false);
                        break;
                    }
                    case 3: {
                        startNewActivity(JWNewsListActivity.class);
                        closeMenu(false);
                        break;
                    }
                    case 4: {
                        if (UIMSModel.getInstance(mContext).isLoginIn()) {
                            startNewActivity(ScoreListActivity.class);
                        } else {
                            startNewActivity(CJCXAuthActivity.class);
                        }
                        closeMenu(false);
                        break;
                    }
                    case 5: {
                        startNewActivity(LibrarySearchActivity.class);
                        closeMenu(false);
                        break;
                    }
                    default: {
                        Toast.makeText(mContext, "The function is commit soon", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private int getWeatherIcon(String weather){
        if(weather.contains("晴")) {
            return R.mipmap.sunny;
        }else if(weather.contains("雪")) {
            return R.mipmap.snow;
        }else if(weather.contains("雷")) {
            return R.mipmap.thunderstorms;
        }else if(weather.contains("雨")) {
            return R.mipmap.slight_drizzle;
        }else if(weather.contains("多云")) {
            return R.mipmap.mostly_cloudy;
        }else if(weather.contains("阴")) {
            return R.mipmap.cloudy;
        }else if(weather.contains("雾")) {
            return R.mipmap.haze;
        }else {
            return R.mipmap.sunny;
        }
    }

}
