package com.project.ync.blooddonation.ui.wellcome;

import android.os.Handler;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.ui.home.HomeActivity_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_splash)
@Fullscreen
public class SplashActivity extends BaseActivity {
    private static final int TIME_DELAYED = 2000; // 2s

    @Pref
    SharedPreferences_ mPref;

    private Handler mHandler;
    private final Runnable mActivityStarter = new Runnable() {
        @Override
        public void run() {
            if (mPref.accessToken().get() == null || mPref.accessToken().get().equals("")) {
                InitialActivity_.intent(SplashActivity.this).start();
            } else {
                HomeActivity_.intent(SplashActivity.this).start();
            }
            finish();
        }
    };

    @Override
    protected void init() {
        mHandler = new Handler();
        mHandler.postDelayed(mActivityStarter, TIME_DELAYED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mActivityStarter);
        mHandler = null;
    }
}
