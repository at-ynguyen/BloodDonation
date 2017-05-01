package com.project.ync.blooddonation.ui.menu;

import android.widget.EditText;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.body.ChangePasswordBody;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.api.response.ObjectResponse;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.util.DialogUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_change_password)
public class ChangePasswordActivity extends BaseActivity {
    @ViewById(R.id.edtPassword)
    EditText mEdtPassword;
    @ViewById(R.id.edtNewPassword)
    EditText mEdtNewPassword;
    @ViewById(R.id.edtConfirmPassword)
    EditText mEdtConfirmPassword;

    @Pref
    SharedPreferences_ mPref;

    @Override
    protected void init() {

    }

    @Click(R.id.imgBack)
    void onBackClick() {
        onBackPressed();
    }

    @Click(R.id.btnChange)
    void onChangePasswordClick() {
        ChangePasswordBody changePasswordBody = ChangePasswordBody.builder()
                .currentPassword(mEdtPassword.getText().toString())
                .newPassword(mEdtNewPassword.getText().toString())
                .confirmPassword(mEdtConfirmPassword.getText().toString()).build();
        Call<ObjectResponse> call = ApiClient.call().changePassword(mPref.accessToken().get(), changePasswordBody);
        call.enqueue(new ApiCallback<ObjectResponse>() {
            @Override
            public void success(ObjectResponse objectResponse) {
                DialogUtil.createSuccessSecret(ChangePasswordActivity.this, "Kết quả", objectResponse.getMessage()).show();
            }

            @Override
            public void failure(ApiError apiError) {

            }
        });
    }
}
