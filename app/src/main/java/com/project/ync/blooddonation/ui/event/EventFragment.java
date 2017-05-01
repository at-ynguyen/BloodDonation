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
import com.project.ync.blooddonation.api.response.EventResponse;
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
public class EventFragment extends BaseFragment {
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<EventResponse> mEventResponses;
    private int currentPages = 1;


    @Override
    protected void init() {
        setVisibilityProgress(true);
        mEventResponses = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final EventAdapter adapter = new EventAdapter(getActivity(), mEventResponses, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        Call<List<EventResponse>> call = ApiClient.call().getListEvent(currentPages);
        call.enqueue(new ApiCallback<List<EventResponse>>() {
            @Override
            public void success(List<EventResponse> eventResponses) {
                setVisibilityProgress(false);
                mEventResponses.clear();
                mEventResponses.addAll(eventResponses);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(ApiError apiError) {
                setVisibilityProgress(false);
            }
        });
        adapter.setListener(new EventAdapter.OnItemEventClickListener() {
            @Override
            public void onItemClick(int position) {
                DetailEventActivity_.intent(getActivity()).mId(mEventResponses.get(position).getEvent().getId()).start();
            }
        });
        adapter.setOnLoadMoreListener(new EventAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mEventResponses.add(null);
                adapter.notifyItemChanged(mEventResponses.size() - 1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEventResponses.remove(mEventResponses.size() - 1);
                        adapter.notifyItemChanged(mEventResponses.size());
                        Call<List<EventResponse>> call = ApiClient.call().getListEvent(currentPages + 1);
                        call.enqueue(new ApiCallback<List<EventResponse>>() {
                            @Override
                            public void success(List<EventResponse> eventResponses) {
                                setVisibilityProgress(false);
                                currentPages++;
                                mEventResponses.addAll(eventResponses);
                                adapter.notifyDataSetChanged();
                                adapter.setLoaded();
                            }

                            @Override
                            public void failure(ApiError apiError) {
                                setVisibilityProgress(false);
                            }
                        });
                    }
                }, 1000);

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<EventResponse>> call = ApiClient.call().getListEvent(currentPages);
                call.enqueue(new ApiCallback<List<EventResponse>>() {
                    @Override
                    public void success(List<EventResponse> eventResponses) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mEventResponses.clear();
                        mEventResponses.addAll(eventResponses);
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
