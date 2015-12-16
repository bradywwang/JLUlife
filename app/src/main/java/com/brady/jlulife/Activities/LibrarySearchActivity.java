package com.brady.jlulife.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.brady.jlulife.Fragments.LibrarySearchFragment;
import com.brady.jlulife.R;

/**
 * Created by brady on 15-11-30.
 */
public class LibrarySearchActivity extends BaseActivity {
    @Override
    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, LibrarySearchFragment.getInstance());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        LibrarySearchFragment fragment = LibrarySearchFragment.getInstance();
        if(fragment.isAdded()&&fragment.canGoBack()){
            LibrarySearchFragment.getInstance().preformBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_library_myhome) {
            Intent intent = new Intent(this,MyLibraryAccountActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
