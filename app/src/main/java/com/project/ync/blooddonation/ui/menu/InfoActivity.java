package com.project.ync.blooddonation.ui.menu;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.body.UserBody;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.model.Town;
import com.project.ync.blooddonation.model.User;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.ui.dialog.StandardPickerDialog;
import com.project.ync.blooddonation.ui.dialog.StandardPickerDialog_;
import com.project.ync.blooddonation.util.DialogUtil;
import com.project.ync.blooddonation.util.TimeUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_info)
public class InfoActivity extends BaseActivity {
    @ViewsById({R.id.edtEmail, R.id.edtFullName, R.id.edtCardId, R.id.edtAddress, R.id.edtWeight, R.id.edtPhoneNumber})
    List<EditText> mEdtInfo;
    @ViewsById({R.id.tvSelectBlood, R.id.tvTown, R.id.tvBirthDay})
    List<TextView> mTvInfo;

    @ViewById(R.id.rdgGender)
    RadioGroup mRdgGender;
    @ViewById(R.id.rdMan)
    RadioButton mRdMan;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;

    private int mIdTown;

    private DatePickerDialog mDatePickerDialog;

    @Pref
    SharedPreferences_ mPref;

    @Override
    protected void init() {
        mProgressBar.setVisibility(View.VISIBLE);
        Log.i("TAG111", mPref.accessToken().get());
        Call<User> call = ApiClient.call().getInfoUser(mPref.accessToken().get());
        call.enqueue(new ApiCallback<User>() {
            @Override
            public void success(User user) {
                mProgressBar.setVisibility(View.GONE);
                mIdTown = user.getTown().getId();
                setText(0, user.getEmail(), true);
                setText(1, user.getFullName(), true);
                setText(2, user.getCardId(), true);
                setText(3, user.getAddress(), true);
                setText(4, String.valueOf(user.getWeight()), true);
                setText(5, user.getPhoneNumber(), true);
                setText(0, user.getBloodType(), false);
                setText(1, user.getTown().getName(), false);
                setText(2, TimeUtil.convertTime(user.getBirthDay()), false);
                mRdgGender.check(user.isGender() ? R.id.rdMan : R.id.rdWomen);
            }

            @Override
            public void failure(ApiError apiError) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setDateTime() {
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setText(2, dayOfMonth + "-" + (month + 1) + "-" + year, false);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.show();
    }

    @Click(R.id.imgBack)
    void onBackClick() {
        onBackPressed();
    }

    @Click(R.id.tvSelectBlood)
    void onSelectBloodClick() {
        StandardPickerDialog_.builder().mResourceArray(R.array.blood_type).build().setListenerStandardPicker(new StandardPickerDialog.IListenerStandardPicker() {
            @Override
            public void onClickOk(String item, int index) {
                setText(0, item, false);
            }

            @Override
            public void onDismiss() {

            }
        }).show(getFragmentManager(), "");
    }

    @Click(R.id.tvTown)
    void onSelectTown() {
        StandardPickerDialog_.builder().mResourceArray(R.array.town).build().setListenerStandardPicker(new StandardPickerDialog.IListenerStandardPicker() {
            @Override
            public void onClickOk(String item, int index) {
                setText(1, item, false);
                mIdTown = index;
            }

            @Override
            public void onDismiss() {

            }
        }).show(getFragmentManager(), "");
    }

    @Click(R.id.btnChange)
    void onChangeClick() {
        mProgressBar.setVisibility(View.VISIBLE);
        UserBody userBody = UserBody.builder()
                .email(getText(mEdtInfo.get(0)))
                .fullName(getText(mEdtInfo.get(1)))
                .cardId(getText(mEdtInfo.get(2)))
                .birthDay(mTvInfo.get(2).getText().toString())
                .phoneNumber(getText(mEdtInfo.get(5)))
                .address(getText(mEdtInfo.get(3)))
                .town(new Town(mIdTown, ""))
                .bloodType(mTvInfo.get(0).getText().toString())
                .gender(mRdMan.isChecked())
                .weight(Float.parseFloat(getText(mEdtInfo.get(4))))
                .build();

        Call<User> call = ApiClient.call().updateUser(mPref.accessToken().get(), userBody);
        call.enqueue(new ApiCallback<User>() {
            @Override
            public void success(User user) {
                mProgressBar.setVisibility(View.GONE);
                DialogUtil.createSuccessSecret(InfoActivity.this, "Thành công", "Chỉnh sửa thành công").show();
            }

            @Override
            public void failure(ApiError apiError) {
                mProgressBar.setVisibility(View.GONE);
                DialogUtil.createErrorDialog(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }, InfoActivity.this, "Thất bại", "Chỉnh sửa thất bại");
            }
        });
    }

    @Click(R.id.tvBirthDay)
    void onBirthDayClick() {
        setDateTime();
    }

    private String getText(EditText edt) {
        return edt.getText().toString();
    }

    private void setText(int position, String text, boolean isEdt) {
        if (isEdt) {
            mEdtInfo.get(position).setText(text);
        } else {
            mTvInfo.get(position).setText(text);
        }
    }

}
