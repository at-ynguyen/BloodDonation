package com.project.ync.blooddonation.ui.event;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.model.FindBlood;
import com.project.ync.blooddonation.ui.BaseFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_event)
public class FindBloodFragment extends BaseFragment {
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    List<FindBlood> mFindBloods = new ArrayList<>();
    private int mCurrentPages = 1;

    @Override
    protected void init() {
        setVisibilityProgress(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final FindBloodAdapter adapter = new FindBloodAdapter(getActivity(), mFindBloods, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        Call<List<FindBlood>> call = ApiClient.call().getListFindBlood(mCurrentPages);
        call.enqueue(new ApiCallback<List<FindBlood>>() {
            @Override
            public void success(List<FindBlood> findBloods) {
                setVisibilityProgress(false);
                mFindBloods.clear();
                mFindBloods.addAll(findBloods);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(ApiError apiError) {

            }
        });
        adapter.setListener(new FindBloodAdapter.OnFindBloodClickListener() {
            @Override
            public void onClick(int position) {
                DetailFindBloodActivity_.intent(getActivity()).mId(mFindBloods.get(position).getId()).start();
            }
        });
        adapter.setOnLoadMoreListener(new FindBloodAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mFindBloods.add(null);
                adapter.notifyItemChanged(mFindBloods.size() - 1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFindBloods.remove(mFindBloods.size() - 1);
                        adapter.notifyItemChanged(mFindBloods.size());
                        Call<List<FindBlood>> call = ApiClient.call().getListFindBlood(mCurrentPages + 1);
                        call.enqueue(new ApiCallback<List<FindBlood>>() {
                            @Override
                            public void success(List<FindBlood> findBloods) {
                                setVisibilityProgress(false);
                                mCurrentPages++;
                                mFindBloods.addAll(findBloods);
                                adapter.notifyDataSetChanged();
                                adapter.setLoaded();
                            }

                            @Override
                            public void failure(ApiError apiError) {

                            }
                        });
                    }
                }, 1000);

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<FindBlood>> call = ApiClient.call().getListFindBlood(1);
                call.enqueue(new ApiCallback<List<FindBlood>>() {
                    @Override
                    public void success(List<FindBlood> findBloods) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mFindBloods.clear();
                        mFindBloods.addAll(findBloods);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(ApiError apiError) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public void loadData() {

    }

    private void setVisibilityProgress(boolean isVisibility) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
    }
}
