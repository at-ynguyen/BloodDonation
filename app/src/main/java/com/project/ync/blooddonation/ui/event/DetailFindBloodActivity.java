package com.project.ync.blooddonation.ui.event;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.model.FindBlood;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.util.ShareUtils;
import com.project.ync.blooddonation.util.TimeUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_detail_find_blood)
public class DetailFindBloodActivity extends BaseActivity {
    @ViewById(R.id.tvTime)
    TextView mTvTime;
    @ViewById(R.id.tvTitle)
    TextView mTvTitle;
    @ViewById(R.id.tvName)
    TextView mTvName;
    @ViewById(R.id.tvContent)
    TextView mTvContent;
    @ViewById(R.id.imgView)
    ImageView mImgView;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;

    @Extra
    int mId;

    private String mSdt;

    @Override
    protected void init() {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<FindBlood> call = ApiClient.call().getListFindBloodById(mId);
        call.enqueue(new ApiCallback<FindBlood>() {
            @Override
            public void success(FindBlood findBlood) {
                mProgressBar.setVisibility(View.GONE);
                if (findBlood != null) {
                    mTvContent.setText(findBlood.getPostContent());
                    mTvName.setText(findBlood.getUser().getFullName());
                    mTvTime.setText(TimeUtil.parseDate(findBlood.getCreateAt()));
                    if (!findBlood.getImage().equals("") || findBlood.getImage() != null) {
                        mImgView.setVisibility(View.VISIBLE);
                        Glide.with(DetailFindBloodActivity.this).load(getString(R.string.url_host) + findBlood.getImage()).centerCrop().into(mImgView);
                    } else {
                        mImgView.setVisibility(View.GONE);
                    }
                    mTvTitle.setText(findBlood.getPostName());
                    mSdt = findBlood.getUser().getPhoneNumber();
                    Log.i("TAG1", mSdt);
                }
            }

            @Override
            public void failure(ApiError apiError) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Click(R.id.imgBack)
    void onBackClick() {
        onBackPressed();
    }

    @Click(R.id.btnHelp)
    void onHelpClick() {
        if (mSdt != null) {
            Uri uri = Uri.parse("tel:" + mSdt);
            Intent i = new Intent(Intent.ACTION_CALL, uri);
            startActivity(i);
        }
    }

    @Click(R.id.tvShare)
    void onClickShare() {
        ShareUtils.SharingToSocialMedia("index/view/findblood" + mId, this);
    }
}
