package com.project.ync.blooddonation.ui.home;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.ui.award.AwardFragment;
import com.project.ync.blooddonation.ui.history.HistoryFragment;
import com.project.ync.blooddonation.widget.NoSwipeViewPager;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.lang.reflect.Field;

import retrofit2.Call;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @ViewById(R.id.bottomBar)
    BottomNavigationView mBottomBar;
    @ViewById(R.id.viewPager)
    NoSwipeViewPager mViewPager;
    private HomePagerAdapter mHomePagerAdapter;

    @Pref
    SharedPreferences_ mPref;

    @Override
    protected void init() {
        disableShiftMode(mBottomBar);
        mBottomBar.setOnNavigationItemSelectedListener(this);
        mHomePagerAdapter = new HomePagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mHomePagerAdapter);
        SharedPreferences mSharedPreferences = getSharedPreferences("PUSHNOTIFICATION", 0);
        String token = mSharedPreferences.getString("Token", null);
        Log.i("TAG11111", token + "");
        Call<Object> call = ApiClient.call().putTokenNotification( token,mPref.email().get());
        call.enqueue(new ApiCallback() {
            @Override
            public void success(Object o) {
                Log.i("TAG111", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBB");
            }

            @Override
            public void failure(ApiError apiError) {

            }
        });
    }

    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("TAG", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("TAG", "Unable to change value of shift mode");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                mViewPager.setCurrentItem(0);
                HomeFragment homeFragment = (HomeFragment) mHomePagerAdapter.getFragment(mViewPager.getCurrentItem());
                if (homeFragment != null) {
                    homeFragment.loadData();
                }
                break;
            case R.id.action_history:
                mViewPager.setCurrentItem(1);
                HistoryFragment historyFragment = (HistoryFragment) mHomePagerAdapter.getFragment(mViewPager.getCurrentItem());
                if (historyFragment != null) {
                    historyFragment.loadData();
                }
                break;
            case R.id.action_award:
                mViewPager.setCurrentItem(2);
                AwardFragment awardFragment = (AwardFragment) mHomePagerAdapter.getFragment(mViewPager.getCurrentItem());
                if (awardFragment != null) {
                    awardFragment.loadData();
                }
                break;
            case R.id.action_menu:
                mViewPager.setCurrentItem(3);
                break;
        }
        return true;
    }

}
