package com.project.ync.blooddonation.ui.event;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.api.response.EventResponse;
import com.project.ync.blooddonation.api.response.ObjectResponse;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.ui.wellcome.LoginActivity_;
import com.project.ync.blooddonation.util.DialogUtil;
import com.project.ync.blooddonation.util.TimeUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;

/**
 * @author YNC
 */
@EActivity(R.layout.fragment_detail_event)
public class DetailEventActivity extends BaseActivity {

    @ViewById(R.id.tvTime)
    TextView mTvTime;
    @ViewById(R.id.tvTitle)
    TextView mTvTitle;
    @ViewById(R.id.tvMembers)
    TextView mTvMember;
    @ViewById(R.id.tvContent)
    TextView mTvContent;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;
    @ViewById(R.id.btnJoin)
    Button mBtnJoin;

    @Extra
    int mId;
    @Pref
    SharedPreferences_ mPref;

    private boolean isCheck = true;

    @Override
    protected void init() {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<EventResponse> call = ApiClient.call().getListEventResponse(mId);
        call.enqueue(new ApiCallback<EventResponse>() {
            @Override
            public void success(EventResponse eventResponse) {
                mProgressBar.setVisibility(View.GONE);
                if (eventResponse != null) {
                    mTvTime.setText(TimeUtil.parseDate(eventResponse.getEvent().getCreateAt()));
                    mTvContent.setText(eventResponse.getEvent().getContent());
                    mTvTitle.setText(eventResponse.getEvent().getEventName());
                    mTvMember.setText(eventResponse.getNumbers() + " người tham gia");
                }
            }

            @Override
            public void failure(ApiError apiError) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
        if (mPref.accessToken().get().equals("") || mPref.accessToken().get() != null) {
            Call<ObjectResponse> objectResponseCall = ApiClient.call().checkEvent(mPref.accessToken().get(), mId);
            objectResponseCall.enqueue(new ApiCallback<ObjectResponse>() {
                @Override
                public void success(ObjectResponse objectResponse) {
                    if (objectResponse.getCode() == 1) {
                        isCheck = true;
                    } else if (objectResponse.getCode() == 2) {
                        isCheck = false;
                    }
                    mBtnJoin.setText(objectResponse.getMessage());
                }

                @Override
                public void failure(ApiError apiError) {

                }
            });
        }
    }

    @Click(R.id.imgBack)
    void onBackClick() {
        onBackPressed();
    }

    @Click(R.id.btnJoin)
    void onJoinClick() {
        if (isCheck) {
            if (mPref.accessToken().get().equals("") || mPref.accessToken().get() != null) {
                Call<Object> call = ApiClient.call().joinEvent(mPref.accessToken().get(), mId);
                call.enqueue(new ApiCallback<Object>() {
                    @Override
                    public void success(Object obj) {
                        DialogUtil.createSuccessSecret(DetailEventActivity.this, "Kết quả", "Đăng ký thành công").show();
                    }

                    @Override
                    public void failure(ApiError apiError) {
                        DialogUtil.createErrorDialog(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }, DetailEventActivity.this, "Lỗi", "Đăng ký thất bại");
                    }
                });
            } else {
                DialogUtil.createErrorDialog(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mPref.accessToken().put("");
                        LoginActivity_.intent(DetailEventActivity.this).start();
                    }
                }, DetailEventActivity.this, "Thông báo", "Bạn phải đăng nhập để đăng ký tham gia đợt hiến máu này");
            }
        }
    }

}
