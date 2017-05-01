package com.project.ync.blooddonation.ui.wellcome;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.body.RegisterBody;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.api.response.RegisterResponse;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.ui.home.HomeActivity_;
import com.project.ync.blooddonation.util.KeyboardUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    @ViewById(R.id.edtEmail)
    EditText mEdtEmail;
    @ViewById(R.id.edtPassword)
    EditText mEdtPassword;
    @ViewById(R.id.edtFullName)
    EditText mEdtFullName;
    @ViewById(R.id.edtCardId)
    EditText mEdtCardId;
    @ViewById(R.id.rdMan)
    RadioButton mRdMan;
    @ViewById(R.id.llRegister)
    LinearLayout mLlRegister;

    @Pref
    SharedPreferences_ mPref;

    @Override
    protected void init() {
        KeyboardUtil.touchHideKeyboard(RegisterActivity.this, mLlRegister);
    }

    @Click(R.id.btnRegister)
    void onClickRegister() {
        Call<RegisterResponse> call = ApiClient
                .call()
                .register(
                        RegisterBody.builder()
                                .email(mEdtEmail.getText().toString())
                                .password(mEdtPassword.getText().toString())
                                .fullName(mEdtFullName.getText().toString())
                                .cardId(mEdtCardId.getText().toString())
                                .gender(mRdMan.isChecked())
                                .build());
        call.enqueue(new ApiCallback<RegisterResponse>() {
            @Override
            public void success(RegisterResponse registerResponse) {
                if (registerResponse != null && registerResponse.getToken() != null) {
                    mPref.accessToken().put(registerResponse.getToken());
                    HomeActivity_.intent(RegisterActivity.this).start();
                }
            }

            @Override
            public void failure(ApiError apiError) {

            }
        });
    }
}
