package com.project.ync.blooddonation.ui.award;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.model.Award;
import com.project.ync.blooddonation.ui.BaseFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_award)
public class AwardFragment extends BaseFragment {
    @ViewById(R.id.recyclerView)
    RecyclerView mRyclerView;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;

    private List<Award> mAwards = new ArrayList<>();

    @Override
    protected void init() {
    }

    @Override
    public void loadData() {
        final AwardAdapter adapter = new AwardAdapter(getActivity(), mAwards);
        mRyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRyclerView.setAdapter(adapter);
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        Call<List<Award>> call = ApiClient.call().getListAward();
        call.enqueue(new ApiCallback<List<Award>>() {
            @Override
            public void success(List<Award> awards) {
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }
                mAwards.clear();
                mAwards.addAll(awards);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(ApiError apiError) {
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}
