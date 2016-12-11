package com.cs.group.videoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cs.group.Utils.SharePreferences;
import com.cs.group.fragments.CustomizeFragment;
import com.cs.group.fragments.ListBuddiesFragment;
import com.cs.group.provider.FragmentTags;
import com.cs.group.tool.Parameter;
import com.cs.group.tool.SharedPreferenceTool;
import com.cs.group.tool.VideoTool;


public class MainActivity extends ActionBarActivity implements CustomizeFragment.OnCustomizeListener {

    private boolean isOpenActivitiesActivated = true;
    private String TAG = "MainActivity == ";
    private Intent mIntent;
    private String mUserName;
    private VideoTool mVideoTool = new VideoTool();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = SharedPreferenceTool.read(Parameter.K_USERNAMEM, this);
        if (savedInstanceState == null) {
            manageFragment(ListBuddiesFragment.newInstance(isOpenActivitiesActivated), FragmentTags.LIST_BUDDIES, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_customize:
                manageFragment(CustomizeFragment.newInstance(), FragmentTags.CUSTOMIZE, true);
                break;
            case R.id.action_about:
                startActivityWith(AboutActivity.class);
                break;
//            case R.id.action_delete:
//                startActivityWith(DeleteActivity.class);
//                setResult(RESULT_OK, null);
//                finish();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivityWith(Class clazz) {
        Intent intent = new Intent(this, clazz);

        startActivity(intent);
    }

    private void manageFragment(Fragment newInstanceFragment, FragmentTags tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment currentIntanceFragment = findFragmentByTag(tag);
        if (currentIntanceFragment == null || (currentIntanceFragment != null && currentIntanceFragment.isHidden())) {
            if (currentIntanceFragment != null) {
                ft.show(currentIntanceFragment);
                Log.d(TAG, "add fragment");
            } else {
                currentIntanceFragment = newInstanceFragment;
                ft.add(R.id.container, currentIntanceFragment, tag.toString());
                if (addToBackStack) {
                    ft.addToBackStack(null);
                }
            }
        } else {
            ft.hide(currentIntanceFragment);
            fm.popBackStack();
        }
        ft.commit();
    }

    private Fragment findFragmentByTag(FragmentTags tag) {
        return getSupportFragmentManager().findFragmentByTag(tag.toString());
    }

    @Override
    public void setSpeed(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setSpeed(value);
        }
    }

    @Override
    public void setGap(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGap(value);
        }
    }


    @Override
    public void setDividerHeight(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDividerHeight(value);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
    }

    private void resetLayout() {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.resetLayout();
            reset();
            CustomizeFragment customizeFragment = (CustomizeFragment) findFragmentByTag(FragmentTags.CUSTOMIZE);
            if (customizeFragment != null) {
                customizeFragment.reset();
            }
        }
    }

    private void reset() {
        SharePreferences.reset();
    }

    public boolean onOpenActivitiesClick(MenuItem menuItem) {
        isOpenActivitiesActivated = !menuItem.isChecked();
        menuItem.setChecked(isOpenActivitiesActivated);
        ListBuddiesFragment fragment = getListBuddiesFragment();


        return false;
    }

    private ListBuddiesFragment getListBuddiesFragment() {
        return (ListBuddiesFragment) findFragmentByTag(FragmentTags.LIST_BUDDIES);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
