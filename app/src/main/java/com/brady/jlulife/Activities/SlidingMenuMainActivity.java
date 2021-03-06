package com.brady.jlulife.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.brady.jlulife.Fragments.CourseListFragment;
import com.brady.jlulife.Fragments.MenuFragment;
import com.brady.jlulife.Models.UIMSModel;
import com.brady.jlulife.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

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
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            super.onBackPressed();
        }
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
            if (UIMSModel.getInstance(this).isLoginIn() && UIMSModel.getInstance(mContext).getmLoginMethod() == UIMSModel.LOGIN_NORMAL_MODE) {
//                FragmentControler.addFragment(getSupportFragmentManager(), R.id.main_container, FragmentControler.TAG_SEM_SELECT);
                Intent intent = new Intent(mContext, SemSelectActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, UIMSAuthActivity.class);
                startActivity(intent);
            }
            menu.showContent();
            return true;
        }
        if (id == 16908332) {
            if (menu.isMenuShowing()) {
                menu.showContent();
            } else {
                menu.showMenu();
            }
            return true;
        }
        if (id == R.id.action_add_course) {
            Intent intent = new Intent(this, EditCourseActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void initSlidingMenu() {
        menu = new SlidingMenu(mContext);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidth(200);
        menu.setBehindOffset(500);
        menu.setFadeDegree(0.5f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_container);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.menu_container, new MenuFragment());
        transaction.commit();
    }

    public SlidingMenu getMenu() {
        return menu;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }


}
