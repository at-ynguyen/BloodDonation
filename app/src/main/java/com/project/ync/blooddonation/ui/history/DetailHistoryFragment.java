package com.project.ync.blooddonation.ui.history;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.model.History;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseFragment;
import com.project.ync.blooddonation.ui.wellcome.LoginActivity_;
import com.project.ync.blooddonation.util.DialogUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_detail_history)
public class DetailHistoryFragment extends BaseFragment {
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.rlNotify)
    RelativeLayout mRlNotify;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;
    @Pref
    SharedPreferences_ mPref;
    List<History> mHistorys = new ArrayList<>();


    @Override
    protected void init() {
        if (mPref.accessToken().get().equals("") || mPref.accessToken().get() == null) {
            mRlNotify.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            mRlNotify.setVisibility(View.GONE);
            final DetailHistoryAdapter adapter = new DetailHistoryAdapter(getActivity(), mHistorys);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(adapter);
            Call<List<History>> call = ApiClient.call().getListHistory(mPref.accessToken().get());
            call.enqueue(new ApiCallback<List<History>>() {
                @Override
                public void success(List<History> histories) {
                    mProgressBar.setVisibility(View.GONE);
                    mHistorys.clear();
                    mHistorys.addAll(histories);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void failure(ApiError apiError) {
                    mProgressBar.setVisibility(View.GONE);
                    if (apiError.getCode() == 401) {
                        DialogUtil.createErrorDialog(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                mPref.accessToken().put("");
                                LoginActivity_.intent(getActivity()).start();
                            }
                        }, getActivity(), "Lỗi", apiError.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void loadData() {
    }

    @Click(R.id.btnLogin)
    void login() {
        LoginActivity_.intent(getActivity()).start();
    }
}
