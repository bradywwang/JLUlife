package com.brady.jlulife.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.Toast;

import com.brady.jlulife.Activities.AboutActivity;
import com.brady.jlulife.Activities.ContactActivity;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.Utils;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import java.util.Date;

public class SettingFragment extends PreferenceFragment {
    private Context mContext;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        Preference myPreference = getPreferenceManager().findPreference("check_update");
        ListPreference listPreference = (ListPreference) getPreferenceManager().findPreference("currentweek");
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong("savedtime", Utils.getFirstDayOfWeek(new Date()).getTime());
                editor.commit();
                return true;
            }
        });
        try {
            myPreference.setSummary(getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        switch (preference.getKey()){
            case "about":{
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            }case "check_update":{
                initUmengUpdate();
                break;
            }case "contact_us":{
                Intent intent = new Intent(getActivity(), ContactActivity.class);
                startActivity(intent);
                break;
            }case "currentweek":{

            }
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private String getVersionName() throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = getActivity().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getActivity().getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }

    private void initUmengUpdate() {
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
                        break;
                    case UpdateStatus.No: // has no update
                        Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.Timeout: // time out
                        Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}