package com.brady.jlulife.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.brady.jlulife.Fragments.CourseListFragment;
import com.brady.jlulife.Fragments.LibrarySearchFragment;
import com.brady.jlulife.Fragments.MenuFragment;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;
import com.brady.jlulife.Utils.Utils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.Date;

public class SlidingMenuMainActivity extends BaseActivity {
    private Context mContext;
    private SlidingMenu menu;

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        UmengUpdateAgent.update(this);

        initSlidingMenu();
    }

    @Override
    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, new CourseListFragment());
        transaction.commit();
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar OutsideItem clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_upgrade) {
            if(UIMSModel.getInstance(this).isLoginIn()){
//                FragmentControler.addFragment(getSupportFragmentManager(), R.id.main_container, FragmentControler.TAG_SEM_SELECT);
                Intent intent = new Intent(mContext,SemSelectActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(mContext,UIMSAuthActivity.class);
                startActivity(intent);
            }
            menu.showContent();
            return true;
        }
        if (id == 16908332) {
            if(menu.isMenuShowing()){
                menu.showContent();
            }else {
                menu.showMenu();
            }
            return true;
        }

        if (id == R.id.action_update_week) {
            final EditText editText = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("设置当前周(请输入数字1-20)")
                    .setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        SharedPreferences sf = getSharedPreferences(ConstValue.SHARED_COURSE_INFO, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sf.edit();
//                        long numOfDay = (date.getTime() - new Date().getTime())/86400000;
                        int currentWeek = Integer.parseInt(editText.getText().toString());
                        editor.putInt("savedweek", currentWeek);
                        editor.putLong("savedtime", Utils.getFirstDayOfWeek(new Date()).getTime());
                        editor.commit();
                        Toast.makeText(mContext,"更新成功,重新启动应用生效",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(mContext,"请输入1-20的数字",Toast.LENGTH_SHORT).show();
                    }
                }
            })
                    .setNegativeButton("取消", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);

    }
    private void initSlidingMenu(){
        menu = new SlidingMenu(mContext);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidth(200);
        menu.setBehindOffset(500);
        menu.setFadeDegree(0.5f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu_container);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.menu_container,new MenuFragment());
        transaction.commit();
    }
    public SlidingMenu getMenu(){
        return menu;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }
}