package com.project.ync.blooddonation.ui.wellcome;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.ui.home.HomeActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_initial)
public class InitialActivity extends BaseActivity {
    @Override
    protected void init() {

    }

    @Click(R.id.btnLogin)
    void clickLogin() {
        LoginActivity_.intent(InitialActivity.this).start();
    }

    @Click(R.id.btnRegister)
    void clickRegister() {
        RegisterActivity_.intent(InitialActivity.this).start();
    }

    @Click(R.id.tvSkip)
    void clickKeep() {
        HomeActivity_.intent(InitialActivity.this).start();
    }
}
