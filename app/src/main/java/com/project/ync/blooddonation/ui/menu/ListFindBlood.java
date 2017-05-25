package com.project.ync.blooddonation.ui.menu;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.model.FindBlood;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_list_find_blood)
public class ListFindBlood extends BaseActivity {
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Pref
    SharedPreferences_ mPref;

    private List<FindBlood> mListFindBlood = new ArrayList<>();

    @Override
    protected void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ListFindBloodAdapter adapter = new ListFindBloodAdapter(this, mListFindBlood, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        Call<List<FindBlood>> call = ApiClient.call().getFindBloodUser(mPref.accessToken().get());
        call.enqueue(new ApiCallback<List<FindBlood>>() {
            @Override
            public void success(List<FindBlood> findBloods) {
                mListFindBlood.clear();
                mListFindBlood.addAll(findBloods);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(ApiError apiError) {

            }
        });
        adapter.setListener(new ListFindBloodAdapter.OnFindBloodClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onCheck(final int position) {
                Call<FindBlood> call = ApiClient.call().check(mPref.accessToken().get(), mListFindBlood.get(position).getId());
                call.enqueue(new ApiCallback<FindBlood>() {
                    @Override
                    public void success(FindBlood findBlood) {
                        mListFindBlood.get(position).setDone(true);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(ApiError apiError) {

                    }
                });
            }

            @Override
            public void onEdit(int position) {

            }
        });
    }

    @Click(R.id.imgBack)
    void onBackClick() {
        onBackPressed();
    }
}
