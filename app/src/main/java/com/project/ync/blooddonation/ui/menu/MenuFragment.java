package com.project.ync.blooddonation.ui.menu;

import android.view.View;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseFragment;
import com.project.ync.blooddonation.ui.find.FindBloodActivity_;
import com.project.ync.blooddonation.ui.wellcome.LoginActivity_;
import com.project.ync.blooddonation.widget.CustomMenuItem;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_menu)
public class MenuFragment extends BaseFragment {
    @ViewById(R.id.btnLogin)
    CustomMenuItem mBtnLogin;
    @ViewById(R.id.btnLogout)
    CustomMenuItem mBtnLogout;
    @ViewById(R.id.btnChangePassword)
    CustomMenuItem mBtnChangePassword;
    @ViewById(R.id.btnFindBlood)
    CustomMenuItem mBtnFindBlood;
    @ViewById(R.id.btnInfo)
    CustomMenuItem mBtnInfo;

    @Pref
    SharedPreferences_ mPref;

    @Override
    protected void init() {
        if (mPref.accessToken().get() == null || mPref.accessToken().get().equals("")) {
            mBtnFindBlood.setVisibility(View.GONE);
            mBtnChangePassword.setVisibility(View.GONE);
            mBtnInfo.setVisibility(View.GONE);
            mBtnLogout.setVisibility(View.GONE);
            mBtnLogin.setVisibility(View.VISIBLE);
        } else {
            mBtnFindBlood.setVisibility(View.VISIBLE);
            mBtnChangePassword.setVisibility(View.VISIBLE);
            mBtnInfo.setVisibility(View.VISIBLE);
            mBtnLogout.setVisibility(View.VISIBLE);
            mBtnLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadData() {
    }

    @Click(R.id.btnFindBlood)
    void onFindBloodClick() {
        FindBloodActivity_.intent(getActivity()).start();
    }

    @Click(R.id.btnLogout)
    void onLogoutClick() {
        mPref.accessToken().put("");
        LoginActivity_.intent(getActivity()).start();
        getActivity().finish();
    }

    @Click(R.id.btnInfo)
    void onInfoClick() {
        InfoActivity_.intent(getActivity()).start();
    }

    @Click(R.id.btnChangePassword)
    void onChangePasswordClick() {
        ChangePasswordActivity_.intent(getActivity()).start();
    }

}
