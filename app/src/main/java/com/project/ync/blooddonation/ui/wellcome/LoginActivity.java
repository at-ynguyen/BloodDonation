package com.project.ync.blooddonation.ui.wellcome;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.body.LoginBody;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.api.response.LoginResponse;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.ui.home.HomeActivity_;
import com.project.ync.blooddonation.util.DialogUtil;
import com.project.ync.blooddonation.util.KeyboardUtil;
import com.project.ync.blooddonation.widget.LoadingBar;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewById(R.id.edtEmail)
    EditText mEdtEmail;
    @ViewById(R.id.edtPassword)
    EditText mEdtPassword;
    @ViewById(R.id.progressBar)
    LoadingBar mProgressBar;
    @ViewById(R.id.rlLogin)
    RelativeLayout mRlLogin;

    @Pref
    SharedPreferences_ mPref;

    @Override
    protected void init() {
        KeyboardUtil.touchHideKeyboard(LoginActivity.this, mRlLogin);
    }

    @Click(R.id.btnLogin)
    void clickLogin() {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<LoginResponse> call = ApiClient.call().login(new LoginBody(mEdtEmail.getText().toString().trim(), mEdtPassword.getText().toString().trim()));
        call.enqueue(new ApiCallback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse) {
                mProgressBar.setVisibility(View.GONE);
                if (loginResponse != null) {
                    mPref.email().put(mEdtEmail.getText().toString());
                    mPref.accessToken().put(loginResponse.getToken());
                    HomeActivity_.intent(LoginActivity.this).start();
                }
            }

            @Override
            public void failure(ApiError apiError) {
                mProgressBar.setVisibility(View.GONE);
                DialogUtil.createErrorDialog(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mPref.accessToken().put("");
                        sweetAlertDialog.dismiss();
                    }
                }, LoginActivity.this, "Lỗi", "Đăng nhập thất bại");
            }
        });

    }

    @Click(R.id.btnRegister)
    void onRegisterClick() {
        RegisterActivity_.intent(LoginActivity.this).start();
    }
}
